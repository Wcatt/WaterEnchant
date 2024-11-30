package waterfun.waterwood.waterenchant.Methods;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.waterwood.io.FileConfigProcess;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.util.CustomEnchant;
import waterfun.waterwood.waterenchant.Enchantments.*;
import waterfun.waterwood.waterenchant.WaterEnchant;

import java.time.Duration;
import java.util.Optional;

public class Methods {
    protected static FileConfigProcess EnchantInfo;

    private static BukkitPlugin plugin;
    private static FileConfigProcess config;
    public static void init(BukkitPlugin plugin){
            Methods.plugin = plugin;
            config = BukkitPlugin.getConfigs();
            EnchantInfo = plugin.loadFile("enchant.yml");
    }
    protected static Optional<ItemMeta> getItemMetaOptional(ItemStack item) {
        return Optional.ofNullable(item.getItemMeta());
    }

    public static void configReload(){
        plugin.loadConfig();
        config = BukkitPlugin.getConfigs();
    }
    public static void enchantInfoReload(){
        EnchantInfo = plugin.loadFile("enchant.yml");
        registerEnchants();
    }
    public static void registerEnchants(){
        EnchantManager.registerEnchants(
                new LightningEnchant(),
                new VampireEnchant(),
                new DamageAbsorbWeaponEnchant(),
                new DamageAdditionEnchant(),
                new DamageReductionEnchant(),
                new DamageOffsetEnchant(),
                new DamageAmplificationEnchant(),
                new AttackRecoveryEnchant()
        );
    }
    /**
     * Add a PotionEffect to a player
     * intensity start form 1 instead of amplifier > 0
     * @param player the player
     * @param effect potion
     * @param duration duration of effect
     * @param intensity intensity of effect (amplifier -1)
     */
    public static void addPlayerPotionEffect(Player player, PotionEffectType effect, int duration, int intensity){
        PotionEffect PotionEffect = new PotionEffect(effect,duration,intensity - 1);
        player.addPotionEffect(PotionEffect);
    }
}
