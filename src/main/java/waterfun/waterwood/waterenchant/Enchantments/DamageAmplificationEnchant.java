package waterfun.waterwood.waterenchant.Enchantments;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.waterwood.consts.RarityLevel;
import waterfun.waterwood.waterenchant.util.Enchants;

import java.util.List;

public class DamageAmplificationEnchant extends Enchants {
    private static double GROWTH_PERCENT_PER_LEVEL;
    public DamageAmplificationEnchant() {
        super(new NamespacedKey(defaultNameSpace,"damage-amplification"));
        this.InitData();
    }

    @Override
    public String getDefaultName() {
        return "DamageAmplification";
    }

    @Override
    public int getDefaultMinLevel() {
        return 1;
    }

    @Override
    public int getDefaultMaxLevel() {
        return 10;
    }

    @Override
    public List<String> getDefaultDescription() {
        return List.of(
                "Percentage increase in damage",
                "",
                "Level(min,max): §7{min-level}§r / §7{max-level}§r",
                "§cAmplification growth:§r §7{growth-percent-per-level}%§r percent"
        );
    }

    @Override
    public RarityLevel getDefaultRarityLevel() {
        return RarityLevel.EPIC;
    }

    @Override
    public String getDefaultDisplay() {
        return "\"I feel the power.\"";
    }

    @Override
    public List<String> getAllowEnchantItems() {
        return List.of("all");
    }
    @Override
    public void InitData(){
        GROWTH_PERCENT_PER_LEVEL = inputData("growth-percent-per-level",5);
        super.InitData();
    }
    public static double getGrowthPercentPerLevel(){
        return GROWTH_PERCENT_PER_LEVEL;
    }
}
