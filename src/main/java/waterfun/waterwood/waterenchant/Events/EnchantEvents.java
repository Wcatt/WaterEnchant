package waterfun.waterwood.waterenchant.Events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.waterwood.enums.COLOR;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.custom.CustomEnchant;
import org.waterwood.processor.MessageProcessor;
import org.waterwood.utils.Colors;
import waterfun.waterwood.waterenchant.Enchantments.*;
import waterfun.waterwood.waterenchant.Methods.EnchantManager;
import waterfun.waterwood.waterenchant.Methods.Methods;
import waterfun.waterwood.waterenchant.items.EnchantBook;

import java.util.Objects;
import java.util.Random;
import java.util.function.Consumer;

public class EnchantEvents {
    Random random = new Random();
    void lightningEvent(ItemStack item, Entity target){
        handleSignalEvent(item,new LightningEnchant(), level ->{
            if(random.nextInt(100) < LightningEnchant.getChancePerLevel() * 0.01 * level){
                target.getWorld().strikeLightning(target.getLocation());
            }
        });
    }
    void vampireEvent(ItemStack weapon, Player player, EntityDamageByEntityEvent evt){
        handleSignalEvent(weapon,new VampireEnchant(), level -> player.setHealth(Math.min(player.getHealth() + evt.getDamage()
                * level * VampireEnchant.getHealingPercent() * 0.01,player.getMaxHealth())));
    }
    void damageAbsorbEvent(ItemStack item, Player player){
       handleSignalEvent(item,new DamageAbsorbWeaponEnchant(),level ->{
           int duration =(int) (DamageAbsorbWeaponEnchant.getBasicDuration()
                   + DamageAbsorbWeaponEnchant.getDurationPerLevel() * level) * 20;
           int effectLevel = (int) DamageAbsorbWeaponEnchant.getEffectPerLevel() * level;
           Methods.addPlayerPotionEffect(player,PotionEffectType.ABSORPTION,duration,effectLevel);
       });
    }

    void damageReduction(Player player,EntityDamageByEntityEvent evt){
        handleArmorEnchants(player,new DamageReductionEnchant(), totalLevel -> evt.setDamage(
                Math.max(DamageReductionEnchant.getMinDamage(),
                        evt.getDamage() - totalLevel  * DamageReductionEnchant.getReductionPerLevel())
        ));
    }
    void damageOffset(Player player,EntityDamageByEntityEvent evt){
        handleArmorEnchants(player, new DamageOffsetEnchant(),totalLevel ->{
            double totalOffset = 1;
            double damage = evt.getDamage();
            double offsetPerLevel = 1 - DamageOffsetEnchant.getOffsetPercentPerLevel() * 0.01f;
            for(int i = 1 ; i <= totalLevel  ; i++){
                totalOffset *= offsetPerLevel;
            }
            evt.setDamage(
                    Math.max(damage * DamageOffsetEnchant.getMinDamagePercent() * 0.01,damage * totalOffset));
        });
    }

    void damageAddition(ItemStack item,EntityDamageByEntityEvent evt){
        handleSignalEvent(item,new DamageAdditionEnchant(), level -> evt.setDamage(evt.getDamage() + level * DamageAdditionEnchant.getDamageAdditionPerLevel()));
    }
    void damageAmplification(ItemStack item,EntityDamageByEntityEvent evt){
        handleSignalEvent(item,new DamageAmplificationEnchant(), level -> evt.setDamage(evt.getDamage() + level * (1 + DamageAmplificationEnchant.getGrowthPercentPerLevel() * 0.01)));
    }
    void attackRecovery(Player player){
        ItemStack item = player.getEquipment().getArmorContents()[3]; //get helmet
        if(item == null) return;
        handleSignalEvent(item,new AttackRecoveryEnchant(),level ->{
            int duration =(int) (AttackRecoveryEnchant.getBasicDuration()
                    + AttackRecoveryEnchant.getDurationPerLevel() * level) * 20;
            int effectLevel = (int) (AttackRecoveryEnchant.getRecoveryEffectPerLevel() * level + 1);
            Methods.addPlayerPotionEffect(player,PotionEffectType.REGENERATION,duration,effectLevel);
        });
    }

    void handleSignalEvent(ItemStack item, CustomEnchant enchant, Consumer<Integer> action) {
        int level = EnchantManager.getLevel(item, enchant);
        if (level > 0) {
            action.accept(level);
        }
    }
    void handleArmorEnchants(Player player, CustomEnchant enchant ,Consumer<Integer> action) {
        ItemStack[] items = player.getEquipment().getArmorContents();
        int totalLevel = 0;
        if (items.length == 0) return;
        for (ItemStack item : items) {
            if(item != null) {
                totalLevel += EnchantManager.getLevel(item, enchant);
            }
        }
        if (totalLevel > 0) {
            action.accept(totalLevel);
        }
    }

    boolean tryEnchantment(PersistentDataContainer container, Player player, CustomEnchant enchant, EnchantBook book,ItemStack targetItem){
        double SUCCESS_RATE = Objects.requireNonNullElse(container.get(Methods.getNamespacedKey(book.getKey(), "success-rate"), PersistentDataType.DOUBLE), 0.0);
        double BREAK_RATE = Objects.requireNonNullElse(container.get(Methods.getNamespacedKey(book.getKey(), "break-rate"), PersistentDataType.DOUBLE), 0.0);
        double DAMAGE_ITEM_RATE = Objects.requireNonNullElse(container.get(Methods.getNamespacedKey(book.getKey(), "damage-item-rate"), PersistentDataType.DOUBLE), 0.);
        double DOWN_GRADE_RATE = Objects.requireNonNullElse(container.get(Methods.getNamespacedKey(book.getKey(), "down-grade-rate"), PersistentDataType.DOUBLE), 0.0);
        double DAMAGE_ITEM_PERCENTAGE_RATE = Objects.requireNonNullElse(container.get(Methods.getNamespacedKey(book.getKey(), "damage-item-percent-rate"), PersistentDataType.DOUBLE), 0.0);
        int currentLevel = EnchantManager.getLevel(targetItem, enchant);
        if(random.nextInt(100) < SUCCESS_RATE) {
            if(currentLevel > 0) {
                EnchantManager.setEnchant(targetItem,enchant,1);
                player.sendMessage(MessageProcessor.successMessage("enchant-success-message",enchant.getDisplayName()));
                return true;
            }else{
                if(currentLevel + 1 > enchant.getMaxLevel()) {
                    player.sendMessage(MessageProcessor.warnMessage(
                            "enchant-up-max-rollback-message",
                            enchant.getNameColor(), " ", enchant.getName()));
                    return false;
                }else{
                    EnchantManager.setEnchant(targetItem,enchant,currentLevel + 1);
                    player.sendMessage(MessageProcessor.successMessage(EnchantBook.SUCCESS_MESSAGE()));
                }
                return true;
            }
        }else{
            int roll = random.nextInt(100);
            if(roll < BREAK_RATE){
                player.getInventory().remove(targetItem);
                player.sendMessage(MessageProcessor.errorMessage(EnchantBook.BREAK_MESSAGE()));
            } else if(roll < DOWN_GRADE_RATE){
                EnchantManager.setEnchant(targetItem,enchant,currentLevel -1);
                player.sendMessage(MessageProcessor.errorMessage(EnchantBook.DOWN_GRADE_MESSAGE()));
            } else if(roll < DAMAGE_ITEM_RATE){
                targetItem.setDurability( // getType get 1.12.2 below
                        (short) Math.max(( targetItem.getDurability() - targetItem.getType().getMaxDurability()
                                * DAMAGE_ITEM_PERCENTAGE_RATE),0));
                player.sendMessage(MessageProcessor.errorMessage(EnchantBook.DAMAGE_MESSAGE()));
            }else{
                player.sendMessage(MessageProcessor.errorMessage(EnchantBook.FAIL_MESSAGE()));
            }
            return true;
        }
    }
}
