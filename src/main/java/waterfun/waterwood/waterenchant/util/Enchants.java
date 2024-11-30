package waterfun.waterwood.waterenchant.util;

import org.bukkit.NamespacedKey;
import org.waterwood.common.Colors;
import org.waterwood.common.StringProcess;
import org.waterwood.consts.COLOR;
import org.waterwood.consts.RarityLevel;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.util.CustomEnchant;
import waterfun.waterwood.waterenchant.Methods.EnchantManager;
import waterfun.waterwood.waterenchant.WaterEnchant;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Enchants extends CustomEnchant {
    protected static final String defaultNameSpace = "water-enchant";
    private List<String> Description;
    private String name;
    private int maxLevel;
    private int minLevel;
    private COLOR nameColor;
    private RarityLevel rarity;
    private String display;
    public Enchants(NamespacedKey namespacedKey) {
        super(namespacedKey);
    }
    public Enchants(NamespacedKey namespacedKey,Map<String,Object> data){
        super(namespacedKey,data);
    }
    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMinLevel() {
        return minLevel;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public COLOR getNameColor() {
        return nameColor;
    }
    @Override
    public String getDisplay(){
        return display;
    }

    /**
     * Get the description which not contain data,
     * will place placeholder string
     * @return List of String of placed string
     */
    @Override
    public List<String> getDescription(){
        return Description;
    }
    @Override
    public RarityLevel getRarityLevel(){return rarity;}
    @SuppressWarnings("unchecked")
    protected <T> T getFileInfo(String key,T defaultVal){
        Object value = EnchantManager.getData(this.getKey().getKey()).getOrDefault(key,defaultVal);
        if(value == null){
            return defaultVal;
        }
        try{
            if(value instanceof Integer){
                if(defaultVal instanceof Double){
                    return (T) Double.valueOf((Integer)value);
                }
            }
            return (T) value;
        }catch(ClassCastException e){
            BukkitPlugin.logMsg(WaterEnchant.getPluginMessage("invalid-type-message",
                    key,value.getClass().getSimpleName()));
            return defaultVal;
        }
    }
    /**
     * Initial Data
     * Supper <b> Must pe placed </b> at the last row of override
     */
    public void InitData(){
        Map<String,Object> basicData = EnchantManager.getData(this.getKey().getKey());
        this.name = (String) basicData.getOrDefault("name",getDefaultName());
        this.minLevel = (int) basicData.getOrDefault("min-level",getDefaultMinLevel());
        this.maxLevel = (int) basicData.getOrDefault("max-level",getDefaultMaxLevel());
        this.rarity = RarityLevel.convertRarityLevel(
                (String) basicData.getOrDefault("rarity",getDefaultRarityLevel().getKey())
        );
        this.nameColor = Colors.getColorTitle(
                (String) basicData.getOrDefault("name-color",Colors.getColorCode(getDefaultNameColor()))
        );
        this.display = (String) basicData.getOrDefault("display",getDefaultDisplay());
        this.putData(Map.of(
                "name",getName(),
                "min-level",getMinLevel(),
                "max-level",getMaxLevel(),
                "name-color",getNameColor(),
                "rarity",getRarityLevel(),
                "display",getDisplay()));
        InitDescription();
    }
    public <T> T inputData(String key,T defaultVal){
        this.putData(key,getFileInfo(key,defaultVal));
        return getFileInfo(key,defaultVal);
    }
    @SuppressWarnings("unchecked")
    private void InitDescription(){
        Object fileInfo = getFileInfo("description", getDefaultDescription());
        if (fileInfo instanceof List<?> tempList) {
            if (tempList.stream().allMatch(item -> item instanceof String)) {
                Description = (List<String>) tempList;
            } else {
                Description = getDefaultDescription();
            }
        } else {
            Description = getDefaultDescription();
        }

        Description = Description.stream()
                .map(str-> StringProcess.replacePlaceHolder(str,this.getData()))
                .collect(Collectors.toList());
    }
    public static String getDefaultNameSpace(){
        return defaultNameSpace;
    }
    public COLOR getDefaultNameColor() {
        return Colors.getRarityColor(getRarityLevel());
    }
}
