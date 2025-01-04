package waterfun.waterwood.waterenchant.util;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.waterwood.io.FileConfigProcess;
import org.waterwood.plugin.bukkit.custom.CustomItem;
import org.waterwood.utils.ConfigReader;
import waterfun.waterwood.waterenchant.Methods.Methods;

public abstract class EnchantItems extends CustomItem {
    protected static final String defaultNameSpace = "water-enchant";
    protected static FileConfigProcess config;

    public EnchantItems(String nameSpace) {
        super(new NamespacedKey(defaultNameSpace, nameSpace));
        config = Methods.getConfig();
    }
    public abstract void InitDescription();
    @Override
    public <T> T inputData(String key, T defaultVal) {
        return putData(key, config.get(key,defaultVal));
    }

    public static String getDefaultNameSpace() {
        return defaultNameSpace;
    }
}
