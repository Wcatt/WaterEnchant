package waterfun.waterwood.waterenchant.Methods;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.waterwood.plugin.bukkit.util.CustomEnchant;

import java.util.*;

public class EnchantManager extends Methods{ // support 1.14.4 plus
    private static final Map<String,CustomEnchant> enchantments = new HashMap<>();
    public static void registerEnchants(CustomEnchant... enchants){
        for(CustomEnchant enchant : enchants){
            enchantments.put(enchant.getKey().getKey(),enchant);
        }
    }
    public static boolean hasEnchant(ItemStack item, CustomEnchant enchant) {
        return getItemMetaOptional(item)
                .map(meta -> meta.getPersistentDataContainer().get(enchant.getKey(), PersistentDataType.STRING) != null)
                .orElse(false);
    }
    public static boolean hasEnchant(ItemStack item,String enchantNameKey){
        return getItemMetaOptional(item)
                .map(meta -> meta.getPersistentDataContainer().get(new NamespacedKey("waterenchant",enchantNameKey), PersistentDataType.STRING) != null)
                .orElse(false);
    }
    public static void addEnchant(ItemStack item, CustomEnchant enchant, int level) {
        getItemMetaOptional(item).ifPresent(meta -> {
            meta.getPersistentDataContainer()
                    .set(enchant.getKey(), PersistentDataType.STRING, enchant.getKey().getKey());
            meta.getPersistentDataContainer()
                    .set(getLevelKey(enchant), PersistentDataType.INTEGER, level);
            item.setItemMeta(meta);
        });
    }
    public static void removeEnchant(ItemStack item,CustomEnchant enchant){
        if(hasEnchant(item,enchant)){
            ItemMeta meta= item.getItemMeta();
            PersistentDataContainer datum= meta.getPersistentDataContainer();
            datum.remove(enchant.getKey());
            datum.remove(getLevelKey(enchant));
            item.setItemMeta(meta);
        }
    }
    public static int getLevel(ItemStack item,CustomEnchant enchant){
        return getItemMetaOptional(item)
                .map(meta ->meta.getPersistentDataContainer()
                        .get(getLevelKey(enchant), PersistentDataType.INTEGER)
                ).orElse(0);
    }
    public static List<String> getItemEnchanted(ItemStack item){
        List<String> enchants = new ArrayList<>();
        return getItemMetaOptional(item).map(meta -> {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            Set<NamespacedKey> keys = container.getKeys();
            for(NamespacedKey key : keys){
                if(enchantments.containsKey(key.getKey())){
                    enchants.add(key.getKey());
                }
            }
            return enchants;
        }).orElse(List.of());
    }
    private static NamespacedKey getLevelKey(CustomEnchant enchant){
        return new NamespacedKey(enchant.getKey().getKey(),"_level");
    }
    public static Map<String,CustomEnchant> getEnchantments(){
        return enchantments;
    }
    public static CustomEnchant getEnchantment(String nameKey){
        return enchantments.get(nameKey);
    }
    public static Map<String, Object> getData(String key){
        Object data = EnchantInfo.getMapData().get(key);
        if (data == null){
            return new HashMap<>();
        }else{
            return (Map<String, Object>) data;
        }
    }
}
