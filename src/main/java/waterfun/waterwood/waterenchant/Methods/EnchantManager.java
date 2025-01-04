package waterfun.waterwood.waterenchant.Methods;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.waterwood.io.FileConfigProcess;
import org.waterwood.plugin.bukkit.custom.CustomEnchant;
import org.waterwood.plugin.bukkit.util.CustomDataProcessor;
import org.waterwood.plugin.bukkit.util.ItemMetaProcessor;
import waterfun.waterwood.waterenchant.util.Enchants;

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
                .map(meta -> meta.getPersistentDataContainer().get(enchant.getKey(), PersistentDataType.INTEGER) != null)
                .orElse(false);
    }
    public static boolean hasEnchant(ItemStack item,String enchantNameKey){
        return getItemMetaOptional(item)
                .map(meta -> meta.getPersistentDataContainer().get(new NamespacedKey(Enchants.getDefaultNameSpace(),enchantNameKey), PersistentDataType.INTEGER) != null)
                .orElse(false);
    }
    public static int setEnchant(ItemStack item, CustomEnchant enchant, int level) {
        int setLevel =  Math.max(enchant.getMinLevel(),Math.min(enchant.getMaxLevel(),level));
        CustomDataProcessor.setCustomData(item,enchant.getKey(),PersistentDataType.INTEGER,
               setLevel);
        return setLevel;
    }
    public static void removeEnchant(ItemStack item,CustomEnchant enchant){
        if(hasEnchant(item,enchant)){
            ItemMeta meta= item.getItemMeta();
            PersistentDataContainer datum= meta.getPersistentDataContainer();
            datum.remove(enchant.getKey());
            item.setItemMeta(meta);
        }
    }
    public static int getLevel(ItemStack item,CustomEnchant enchant){
        return getItemMetaOptional(item)
                .map(meta ->meta.getPersistentDataContainer()
                        .get(enchant.getKey(), PersistentDataType.INTEGER)
                ).orElse(0);
    }
    public static List<CustomEnchant> getItemEnchanted(ItemStack item){
        List<CustomEnchant> enchants = new ArrayList<>();
        return getItemMetaOptional(item).map(meta -> {
            PersistentDataContainer container = meta.getPersistentDataContainer();
            Set<NamespacedKey> keys = container.getKeys();
            for(NamespacedKey key : keys){
                if(enchantments.containsKey(key.getKey())){
                    enchants.add(enchantments.get(key.getKey()));
                }
            }
            return enchants;
        }).orElse(List.of());
    }
    public static CustomEnchant getRarestEnchant(ItemStack item){
        List<CustomEnchant> enchants = getItemEnchanted(item);
        if (enchants.isEmpty()) return null;
        CustomEnchant[] upperEnchant = {enchants.get(0)};
        enchants.forEach(enchant -> {
            if(enchant.getRarityLevel().getWorth() >= upperEnchant[0].getRarityLevel().getWorth()){
                upperEnchant[0] = enchant;
            }
        });
        return upperEnchant[0];
    }
    public static Map<String,CustomEnchant> getEnchantments(){
        return enchantments;
    }
    public static CustomEnchant getEnchantment(String nameKey){
        return enchantments.get(nameKey);
    }
//    public static Map<String, Object> getData(String key){
//        Object data = EnchantInfo.getMapData().get(key);
//        if (data == null){
//            return new HashMap<>();
//        }else{
//            return (Map<String, Object>) data;
//        }
//    }
    public static FileConfigProcess getConfigData(){
        return EnchantInfo;
    }
}
