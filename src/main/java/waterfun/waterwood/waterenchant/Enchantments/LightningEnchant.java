package waterfun.waterwood.waterenchant.Enchantments;

import org.waterwood.enums.RarityLevel;
import waterfun.waterwood.waterenchant.util.Enchants;

import java.util.List;

public class LightningEnchant extends Enchants {
    private static int CHANCE_PER_LEVEL = 15;
    public LightningEnchant() {
        super("lightning");
        this.InitData();
    }

    @Override
    public String getDefaultName() {
        return "lightning";
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
    public void InitData() {
        CHANCE_PER_LEVEL = inputData("chance-per-level",15);
        super.InitData();
    }

    @Override
    public List<String> getDefaultDescription(){
        return List.of(
                "A enchant that when player attack an entity",
                "will summon a lightning at the entries' position",
                "",
                "Level(min,max): §7{min-level}§r / §7{max-level}§r",
                "§6chance per level: §7{chance-per-level}§r percent"
        );
    }

    @Override
    public RarityLevel getDefaultRarityLevel() {
        return RarityLevel.ADVANCE;
    }

    @Override
    public String getDefaultDisplay() {
        return "§bWith thunderous might, you command all \nharbor tenderness within.§r";
    }

    @Override
    public List<String> getAllowEnchantItems() {
        return List.of("all");
    }

    public static int getChancePerLevel(){
        return CHANCE_PER_LEVEL;
    }
}
