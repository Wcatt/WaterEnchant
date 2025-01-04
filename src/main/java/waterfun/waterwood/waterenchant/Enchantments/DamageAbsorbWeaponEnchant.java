package waterfun.waterwood.waterenchant.Enchantments;

import org.bukkit.ChatColor;
import org.waterwood.utils.Colors;
import org.waterwood.enums.COLOR;
import org.waterwood.enums.RarityLevel;
import waterfun.waterwood.waterenchant.util.Enchants;

import java.util.List;

public class DamageAbsorbWeaponEnchant extends Enchants {
    private static double BASIC_DURATION = 0.5;
    private static double DURATION_PER_LEVEL = 1;
    private static double EFFECT_PER_LEVEL = 1;

    public DamageAbsorbWeaponEnchant() {
        super("damage-absorb");
        this.InitData();
    }
    @Override
    public String getDefaultName() {
        return "DamageAbsorb";
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
    public COLOR getDefaultNameColor() {
        return Colors.getRarityColor(RarityLevel.EPIC);
    }

    @Override
    public List<String> getDefaultDescription() {
        return List.of(
                "When player damage an enemy by weapon",
                "will temporary add damage absorb effect",
                "",
                "Level(min,max): §7{min-level}§r / §7{max-level}§r",
                "§bDuration:§r §7{basic-duration}s§r + §7{duration-per-level}s§r ticks per level",
                "§6Effect Level:§r §7{effect-per-level}§r per level"
        );
    }

    @Override
    public RarityLevel getDefaultRarityLevel() {
        return RarityLevel.EPIC;
    }

    @Override
    public String getDefaultDisplay() {
        return ChatColor.GOLD + "With each strike, \nthe sword weaves a shield of unseen grace.";
    }

    @Override
    public List<String> getAllowEnchantItems() {
        return List.of("sword","axe");
    }
    @Override
    public void InitData(){
        BASIC_DURATION = inputData("basic-duration",0.5);
        DURATION_PER_LEVEL = inputData("duration-per-level",1);
        EFFECT_PER_LEVEL = inputData("effect-per-level",1);
        super.InitData();
    }

    public static double getBasicDuration() {
        return BASIC_DURATION;
    }

    public static double getDurationPerLevel() {
        return DURATION_PER_LEVEL;
    }

    public static double getEffectPerLevel() {
        return EFFECT_PER_LEVEL;
    }
}
