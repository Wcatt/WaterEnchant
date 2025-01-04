package waterfun.waterwood.waterenchant.Enchantments;

import org.waterwood.enums.RarityLevel;
import waterfun.waterwood.waterenchant.util.Enchants;

import java.util.List;

public class DamageAdditionEnchant extends Enchants {
    private static double DAMAGE_ADDITION_PER_LEVEL;

    public DamageAdditionEnchant() {
        super("damage-addition");
        this.InitData();
    }

    @Override
    public String getDefaultName() {
        return "DamageAddition";
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
                "Normal attacks deal additional damage",
                "including light strikes that are not fully charged",
                "",
                "Level(min,max): §7{min-level}§r / §7{max-level}§r",
                "§cDamage addition:§r §7{damage-addition-per-level}§r per level"
        );
    }

    @Override
    public RarityLevel getDefaultRarityLevel() {
        return RarityLevel.EPIC;
    }

    @Override
    public String getDefaultDisplay() {
        return "Sharper and better";
    }

    @Override
    public List<String> getAllowEnchantItems() {
        return List.of(
                "all"
        );
    }
    @Override
    public void InitData(){
        DAMAGE_ADDITION_PER_LEVEL = inputData("damage-addition-per-level",0.5);
        super.InitData();
    }

    public static double getDamageAdditionPerLevel(){
        return DAMAGE_ADDITION_PER_LEVEL;
    }
}
