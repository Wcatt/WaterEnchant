package waterfun.waterwood.waterenchant.Events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import waterfun.waterwood.waterenchant.Enchantments.DamageAbsorbWeaponEnchant;
import waterfun.waterwood.waterenchant.Enchantments.LightningEnchant;
import waterfun.waterwood.waterenchant.Enchantments.VampireEnchant;
import waterfun.waterwood.waterenchant.Methods.EnchantManager;

import java.util.Random;

public class PlayerEvents extends EnchantEvents implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent evt){
        if(evt.getDamager() instanceof Player player){ // player damage entity
            Entity target = evt.getEntity();
            ItemStack item= player.getInventory().getItemInMainHand();

            player.sendMessage(String.valueOf(evt.getDamage()));

            lightningEvent(item,target);
            vampireEvent(item,player,evt);
            damageAbsorbEvent(item,player);
            damageAddition(item,evt);
            damageAmplification(item,evt);

            player.sendMessage(String.valueOf(evt.getDamage()));
        }else if(evt.getEntity() instanceof Player player){ //entity damage player
            player.sendMessage(String.valueOf(evt.getDamage()));

            damageOffset(player,evt);
            damageReduction(player,evt);
            attackRecovery(player);

            player.sendMessage(String.valueOf(evt.getDamage()));
        }
    }

}
