package waterfun.waterwood.waterenchant.Enchantments;

import org.bukkit.NamespacedKey;
import org.waterwood.common.Colors;
import org.waterwood.common.StringProcess;
import org.waterwood.consts.COLOR;
import org.waterwood.plugin.bukkit.util.CustomEnchant;
import waterfun.waterwood.waterenchant.Methods.EnchantManager;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Enchants extends CustomEnchant {
    private List<String> Description;
    private String name;
    private int maxLevel;
    private int minLevel;
    private COLOR nameColor;
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

    /**
     * Get the description which not contain data,
     * will place placeholder string
     * @return List of String of placed string
     */
    @Override
    public List<String> getDescription(){
        return Description;
    }
    protected Object getFileInfo(String key, Object defaultVal){
        return EnchantManager.getData(this.getKey().getKey()).getOrDefault(key,defaultVal);
    }
    protected Object getFileInfo(String key){
        return EnchantManager.getData(this.getKey().getKey()).get(key);
    }

    /**
     * Initial Data\n
     * Supper <b> Must pe placed </b> at the last row of override
     */
    public void InitData(){
        this.name = (String) getFileInfo("name",getDefaultName());
        this.minLevel = (int) getFileInfo("min-level",getDefaultMinLevel());
        this.maxLevel = (int) getFileInfo("max-level",getDefaultMaxLevel());
        this.nameColor = Colors.getColorTitle(
                (String) getFileInfo("name-color",Colors.getColorCode(getDefaultNameColor()))
        );
        this.putData(Map.of(
                "name",getName(),
                "min-level",getMinLevel(),
                "max-level",getMaxLevel(),
                "name-color",getNameColor()));
        InitDescription();
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
}
