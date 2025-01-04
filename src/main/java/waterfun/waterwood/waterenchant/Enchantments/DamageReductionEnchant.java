package waterfun.waterwood.waterenchant.Enchantments;

import org.waterwood.enums.RarityLevel;
import waterfun.waterwood.waterenchant.util.Enchants;

import java.util.List;

public class DamageReductionEnchant extends Enchants {
    private static double REDUCTION_PER_LEVEL = 0.5;
    private static double MIN_DAMAGE = 0.5;

    public DamageReductionEnchant() {
        super("damage-reduction");
        this.InitData();
    }

    @Override
    public String getDefaultName() {
        return "DamageReduction";
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
                "Reduce damage by fixed value",
                "but with a min damage",
                "",
                "Level(min,max): §7{min-level}§r / §7{max-level}§r",
                "§6Reduction:§r §8{reduction-per-level}§c damage§r per level",
                "§cMin damage:§r §7{min-damage}§c damage§r"
        );
    }

    @Override
    public RarityLevel getDefaultRarityLevel() {
        return RarityLevel.EPIC;
    }

    @Override
    public String getDefaultDisplay() {
        return "No one can shake my hair";
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
        REDUCTION_PER_LEVEL = inputData("reduction-per-level",0.5);
        MIN_DAMAGE = inputData("min-damage",0.5);
        super.InitData();
    }

    public static double getReductionPerLevel() {
        return REDUCTION_PER_LEVEL;
    }

    public static double getMinDamage() {
        return MIN_DAMAGE;
    }
}
