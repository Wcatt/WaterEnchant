package waterfun.waterwood.waterenchant.util;

import org.bukkit.NamespacedKey;
import org.waterwood.utils.Colors;
import org.waterwood.utils.ConfigReader;
import org.waterwood.enums.RarityLevel;
import org.waterwood.plugin.bukkit.custom.CustomEnchant;
import org.waterwood.adapter.DataAdapter;
import waterfun.waterwood.waterenchant.Methods.EnchantManager;
import waterfun.waterwood.waterenchant.Methods.Methods;

import java.util.Map;

public abstract class Enchants extends CustomEnchant{
    protected static final String defaultNameSpace = "water-enchant";
    public Enchants(String nameSpace) {
        super(new NamespacedKey(defaultNameSpace, nameSpace));
    }
    /**
     * Get the description which not contain data,
     * will place placeholder string
     * @return List of String of placed string
     */
    @SuppressWarnings("unchecked")
    protected <T> T getFileInfo(String key,T defaultVal){
        Object value = EnchantManager.getConfigData().getMapData(this.getKey().getKey()).getOrDefault(key,defaultVal);
        return DataAdapter.toValue(value,defaultVal);
    }
    @Override
    public void InitData(){
        Map<String,Object> basicData = EnchantManager.getConfigData().getMapData(this.getKey().getKey());
        this.name = (String) basicData.getOrDefault("name",getDefaultName());
        this.minLevel = (int) basicData.getOrDefault("min-level",getDefaultMinLevel());
        this.maxLevel = (int) basicData.getOrDefault("max-level",getDefaultMaxLevel());
        this.rarity = RarityLevel.getRarityLevel(
                (String) basicData.getOrDefault("rarity",getDefaultRarityLevel().getKey())
        );
        this.nameColor = Colors.getColorTitle(
                (String) basicData.getOrDefault("name-color",Colors.getColorCode(getDefaultNameColor()))
        );
        this.display = (String) basicData.getOrDefault("display",getDefaultDisplay());
        this.rarityDisplay = Methods.getRarityDisplay(rarity);
        this.putData(Map.of(
                "name",getName(),
                "min-level",getMinLevel(),
                "max-level",getMaxLevel(),
                "name-color",getNameColor(),
                "rarity",getRarityLevel(),
                "rarity-display", getRarityDisplay(),
                "display",getDisplay()));
        InitDescription();
    }
    @Override
    public <T> T inputData(String key, T defaultVal){
        return putData(key,getFileInfo(key,defaultVal));
    }

    public void InitDescription(){
        Description = ConfigReader.getAndPlaceDescription(
                EnchantManager.getConfigData(),
                this.getKey().getKey() + ".description",
                this.getData(),
                getDefaultDescription()
        );
    }
    public static String getDefaultNameSpace() {
        return defaultNameSpace;
    }
}
