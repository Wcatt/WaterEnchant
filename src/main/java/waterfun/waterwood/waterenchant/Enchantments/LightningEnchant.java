package waterfun.waterwood.waterenchant.Enchantments;

import org.bukkit.NamespacedKey;
import org.waterwood.consts.COLOR;

import java.util.List;
import java.util.Map;

public class LightningEnchant extends Enchants {
    public LightningEnchant() {
        super(new NamespacedKey("waterenchant","lightning"));
        this.InitData();
    }

    @Override
    public String getDefaultName() {
        return "lightning";
    }

    @Override
    public COLOR getDefaultNameColor() {
        return COLOR.BLUE;
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
        this.getData().put("chance-per-level",this.getChancePerLevel());
        super.InitData();
    }

    @Override
    public List<String> getDefaultDescription(){
        return List.of(
                "A enchant that when player attack an entity",
                "will summon a lightning at the entries' position",
                "chance per level: §6%d§r percent".formatted(getChancePerLevel())
        );
    }
    public int getChancePerLevel(){
        return (int) getFileInfo("chance-per-level",15);
    }
}
