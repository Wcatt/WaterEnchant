package waterfun.waterwood.waterenchant.Enchantments;

import org.waterwood.enums.RarityLevel;
import waterfun.waterwood.waterenchant.util.Enchants;

import java.util.List;

public class AttackRecoveryEnchant extends Enchants {
    private static double BASIC_DURATION;
    private static double RECOVERY_EFFECT_PER_LEVEL;
    private static double DURATION_PER_LEVEL;
    public AttackRecoveryEnchant() {
        super("attack-recovery");
        this.InitData();
    }

    @Override
    public String getDefaultName() {
        return "AttackRecovery";
    }

    @Override
    public int getDefaultMinLevel() {
        return 1;
    }

    @Override
    public int getDefaultMaxLevel() {
        return 5;
    }

    @Override
    public List<String> getDefaultDescription() {
        return List.of(
                "When player suffer entity's damage,",
                "temporary add a heart recovery effect to him",
                "",
                "Level(min,max): §7{min-level}§r / §7{max-level}§r",
                "§bDuration:§r §7{basic-duration}s§r + §7{duration-per-level}s§r per level",
                "§aRecovery effect:§r §7{recovery-effect-per-level}§r per level"
        );
    }

    @Override
    public RarityLevel getDefaultRarityLevel() {
        return RarityLevel.LEGEND;
    }

    @Override
    public String getDefaultDisplay() {
        return "protective magic of life recovery";
    }

    @Override
    public List<String> getAllowEnchantItems() {
        return List.of("helmet");
    }
    @Override
    public void InitData(){
        RECOVERY_EFFECT_PER_LEVEL = inputData("recovery-effect-per-level",0.5);
        DURATION_PER_LEVEL = inputData("duration-per-level",0.5);
        BASIC_DURATION = inputData("basic-duration",3);
        super.InitData();
    }
    public static double getBasicDuration(){
        return BASIC_DURATION;
    }
    public static double getDurationPerLevel(){
        return DURATION_PER_LEVEL;
    }
    public static double getRecoveryEffectPerLevel(){
        return RECOVERY_EFFECT_PER_LEVEL;
    }
}
