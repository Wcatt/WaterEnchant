package waterfun.waterwood.waterenchant.Events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.waterwood.plugin.bukkit.custom.CustomEnchant;
import waterfun.waterwood.waterenchant.Methods.EnchantManager;
import waterfun.waterwood.waterenchant.Methods.Methods;
import waterfun.waterwood.waterenchant.items.EnchantBook;
import waterfun.waterwood.waterenchant.util.EnchantItems;

import java.util.Random;

public class PlayerEvents extends EnchantEvents implements Listener {
    Random rand = new Random();

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

    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt){
        ItemStack cursorItem = evt.getCursor();
        ItemStack targetItem = evt.getCurrentItem();
        if(cursorItem == null || targetItem == null) return;
        EnchantBook book = new EnchantBook();
        if(cursorItem.getType() != book.getMaterial()) return;
        ItemMeta meta = cursorItem.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if(container.get(Methods.getNamespacedKey(book.getKey(),"target-enchant"), PersistentDataType.STRING) == null) return;

        CustomEnchant enchant = EnchantManager.getEnchantment(
                container.get(Methods.getNamespacedKey(book.getKey(),"target-enchant"), PersistentDataType.STRING));
        evt.setCancelled(! tryEnchantment(container,(Player)evt.getWhoClicked(),enchant,book,targetItem));
    }
}
