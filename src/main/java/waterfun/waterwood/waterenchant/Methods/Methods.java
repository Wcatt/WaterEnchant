package waterfun.waterwood.waterenchant.Methods;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.waterwood.io.FileConfigProcess;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.util.ComponentProcesser;
import waterfun.waterwood.waterenchant.Enchantments.LightningEnchant;
import waterfun.waterwood.waterenchant.WaterEnchant;

import java.io.FileNotFoundException;
import java.io.IOException;
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
        ItemMeta meta = item.getItemMeta();
        return Optional.ofNullable(meta);
    }
}
