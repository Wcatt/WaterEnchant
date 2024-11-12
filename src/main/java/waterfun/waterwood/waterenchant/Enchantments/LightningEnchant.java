package waterfun.waterwood.waterenchant.Enchantments;

import org.bukkit.NamespacedKey;
import org.waterwood.consts.ColorCode;
import org.waterwood.plugin.bukkit.util.CustomEnchant;
import waterfun.waterwood.waterenchant.Methods.EnchantManager;

public class LightningEnchant extends CustomEnchant {


    public LightningEnchant() {
        super(new NamespacedKey("waterenchant","lightning"));
    }

    @Override
    public String getName() {
        return EnchantManager.getEnchantName(this);
    }

    @Override
    public ColorCode getNameColor() {
        return ColorCode.DARKBLUE;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
    @Override
    public String getDescription(){
        return EnchantManager.getEnchantDescription(this);
    }
}
