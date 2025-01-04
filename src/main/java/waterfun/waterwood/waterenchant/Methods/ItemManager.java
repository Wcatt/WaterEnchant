package waterfun.waterwood.waterenchant.Methods;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.waterwood.plugin.bukkit.custom.CustomEnchant;
import org.waterwood.plugin.bukkit.custom.CustomItem;
import org.waterwood.plugin.bukkit.util.CustomDataProcessor;
import waterfun.waterwood.waterenchant.items.EnchantBook;
import waterfun.waterwood.waterenchant.util.EnchantItems;

import java.util.HashMap;
import java.util.Map;

public class ItemManager extends Methods{
    private static final Map<String, EnchantItems> Items = new HashMap<String, EnchantItems>();
    public static Map<String, EnchantItems> getRegisteredItems() {
        return Items;
    }

    public static void registerItem(EnchantItems... items){
        for(EnchantItems item : items){
            Items.put(item.getKey().getKey(), item);
        }
    }

    public static ItemStack setUpEnchantBookItem(ItemStack item){
        EnchantBook enchantBook = new EnchantBook();
        ItemMeta meta = item.getItemMeta();
        return item;
    }
}
