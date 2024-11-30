package waterfun.waterwood.waterenchant.Enchantments;

import org.bukkit.NamespacedKey;
import org.waterwood.consts.RarityLevel;
import waterfun.waterwood.waterenchant.util.Enchants;

import java.util.List;

public class  DamageOffsetEnchant extends Enchants {
    private static double OFFSET_PERCENT_PER_LEVEL;
    private static double MIN_DAMAGE_PERCENT;

    public DamageOffsetEnchant() {
        super(new NamespacedKey(defaultNameSpace,"damage-offset"));
        this.InitData();
    }

    @Override
    public String getDefaultName() {
        return "DamageOffset";
    }

    @Override
    public int getDefaultMinLevel() {
        return 1;
    }

    @Override
    public int getDefaultMaxLevel() {
        return 3;
    }

    @Override
    public List<String> getDefaultDescription() {
        return List.of(
                "Reduce damage by a certain percentage",
                "",
                "Level(min,max): §7{min-level}§r / §7{max-level}§r",
                "§6Offset percent:§r §7{offset-percent-per-level}%§r per level",
                "§cMin damage:§r §7{min-damage-percent}%§r percent of§c damage§r"
        );
    }

    @Override
    public RarityLevel getDefaultRarityLevel() {
        return RarityLevel.EPIC;
    }

    @Override
    public String getDefaultDisplay() {
        return "\"Are you scraping?\"";
    }

    @Override
    public List<String> getAllowEnchantItems() {
        return List.of(
                "leggings",
                "chestplate"
        );
    }
    @Override
    public void InitData(){
        OFFSET_PERCENT_PER_LEVEL = inputData("offset-percent-per-level",5.0);
        MIN_DAMAGE_PERCENT = inputData("min-damage-percent",50.0);
        super.InitData();
    }

    public static double getOffsetPercentPerLevel() {
        return OFFSET_PERCENT_PER_LEVEL;
    }
    public static double getMinDamagePercent(){ return MIN_DAMAGE_PERCENT; }
}
