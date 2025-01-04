package waterfun.waterwood.waterenchant.Enchantments;

import org.waterwood.utils.Colors;
import org.waterwood.enums.COLOR;
import org.waterwood.enums.RarityLevel;
import waterfun.waterwood.waterenchant.util.Enchants;

import java.util.List;

public class VampireEnchant extends Enchants {
    private static int HEALING_PERCENT_PER_LEVEL = 10;
    public VampireEnchant() {
        super("vampire");
        this.InitData();
    }

    @Override
    public String getDefaultName() {
        return "vampire";
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
        return Colors.getRarityColor(RarityLevel.LEGEND);
    }

    @Override
    public List<String> getDefaultDescription() {
        return List.of(
                "When player damage an entity ",
                "It will convert some of the damage caused to enemies into health points to heal itself",
                "",
                "Level(min,max): §7{min-level}§r / §7{max-level}§r",
                "§aHealing percent:§r §7{healing-percent-per-level}%§r per level"

        );
    }

    @Override
    public RarityLevel getDefaultRarityLevel() {
        return RarityLevel.LEGEND;
    }

    @Override
    public String getDefaultDisplay() {
        return "\"§cI tasted fear in your blood§r\"";
    }

    @Override
    public List<String> getAllowEnchantItems() {
        return List.of("sword");
    }
    public static int getHealingPercent(){
        return HEALING_PERCENT_PER_LEVEL;
    }
    @Override
    public void InitData() {
        HEALING_PERCENT_PER_LEVEL = inputData("healing-percent-per-level",10);
        super.InitData();
    }
}
