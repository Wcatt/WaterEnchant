package waterfun.waterwood.waterenchant.Events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import waterfun.waterwood.waterenchant.Enchantments.LightningEnchant;
import waterfun.waterwood.waterenchant.Methods.EnchantManager;
import waterfun.waterwood.waterenchant.WaterEnchant;

import java.util.Random;

public class EnchantEvents implements Listener {
    Random random = new Random();
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent evt){
        if(evt.getDamager() instanceof Player player){
            Entity target = evt.getEntity();
            ItemStack weapon= player.getInventory().getItemInMainHand();
            int enchantLevel = EnchantManager.getLevel(weapon,new LightningEnchant());
            WaterEnchant.logMsg(String.valueOf(enchantLevel) + " " + EnchantManager.hasEnchant(weapon, "lightning"));
            WaterEnchant.logMsg(weapon.getItemMeta().getAsString());
            if (enchantLevel == 0) return;
            if(random.nextInt(100) < 15 * enchantLevel){
                target.getWorld().strikeLightning(target.getLocation());
            }
        }
    }
}
