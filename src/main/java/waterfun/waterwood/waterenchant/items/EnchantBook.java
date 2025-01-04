package waterfun.waterwood.waterenchant.items;

import org.bukkit.Material;
import org.waterwood.enums.RarityLevel;
import org.waterwood.plugin.bukkit.custom.CustomEnchant;
import org.waterwood.utils.ConfigReader;
import org.waterwood.processor.StringProcess;
import waterfun.waterwood.waterenchant.util.EnchantItems;

import java.util.List;

public class EnchantBook extends EnchantItems {
    private double SUCCESS_RATE;
    private double BREAK_RATE;
    private double DOWN_GRADE_RATE;
    private double DAMAGE_ITEM_RATE;
    private double DAMAGE_ITEM_PERCENTAGE;
    private String ENCHANT_DISPLAY_NAME;

    public static String SUCCESS_MESSAGE;
    protected static String BREAK_MESSAGE;
    protected static String DAMAGE_MESSAGE;
    protected static String DOWN_GRADE_MESSAGE;
    protected static String FAIL_MESSAGE;

    private CustomEnchant targetEnchant;
    public EnchantBook() {
        super("enchant-book");
        this.InitData();
    }
    public EnchantBook(CustomEnchant targetEnchant) {
        super("enchant-book");
        this.targetEnchant = targetEnchant;
        this.InitData();
    }

    @Override
    public void InitData(){
        SUCCESS_RATE = putData("success-rate",getRate("enchant.success",60));
        BREAK_RATE = putData("break-rate",getRate("enchant.fail.break",5.0));
        DAMAGE_ITEM_RATE = putData("damage-item-rate",getRate("enchant.fail.damage-item",20.0));
        DOWN_GRADE_RATE = putData("down-grade-rate",getRate("enchant.fail.down-grade",20.0));
        DAMAGE_ITEM_PERCENTAGE = putData("damage-percent",getRate("enchant.fail.damage-item.damage-percent",10.0));

        SUCCESS_MESSAGE = inputData("enchant.success.message","§aSuccess Enchanted");
        FAIL_MESSAGE = config.get("enchant.fail.message","§cEnchant failed,noting happened");
        BREAK_MESSAGE = config.get("enchant.fail.break.message","§cOops, your item break down");
        DOWN_GRADE_MESSAGE = config.get("enchant.fail.down-grade.message","§cEnchant failed , enchant down grade!");
        DAMAGE_MESSAGE = config.get("enchant.fail.damage-item.message","§cEnchant failed , item damaged!");
        ENCHANT_DISPLAY_NAME = putData("enchant-display-name", targetEnchant == null? null:targetEnchant.getDisplayName());

        this.material = putData("material",Material.getMaterial(config.get("enchant-item.material","BOOK")));
        this.name = putData("name", StringProcess.replacePlaceHolder(config.get("enchant-item.name",getDefaultName()),this.getData()));
        InitDescription();
    }

    @Override
    public void InitDescription(){
        Description = ConfigReader.getAndPlaceDescription(
                config,
                "enchant-item.description",
                this.getData(),
                getDefaultDescription()
        );
    }

    @Override
    public RarityLevel getDefaultRarityLevel() {
        return targetEnchant.getRarityLevel();
    }
    @Override
    public String getDefaultName(){
        return "Super Enchant Book";
    }
    @Override
    public List<String> getDefaultDescription(){
        return List.of(
                "§fSuper Enchant: [§r§l{enchant-display-name}§f]7r",
                "§fA book containing mysterious powers",
                "§fIt can grant powerful abilities to certain items",
                "",
                "§fSuccess rate§f: §a{success-rate}%§r",
                "§fBreak rate§f: §c{break-rate}%§r"
        );
    }

    @Override
    public Material getDefaultMaterial() {
        return Material.BOOK;
    }

    private double getRate(String key,double defaultValue){
        return ConfigReader.getPercentRateDouble(
                config,
                key + ".rate",
                defaultValue,
                "min-percent",
                "max-percent");
    }
    public double SUCCESS_RATE() {
        return SUCCESS_RATE;
    }
    public double BREAK_RATE() {
        return BREAK_RATE;
    }
    public double DOWN_GRADE_RATE() {
        return DOWN_GRADE_RATE;
    }
    public double DAMAGE_ITEM_RATE() {
        return DAMAGE_ITEM_RATE;
    }
    public double DAMAGE_ITEM_PERCENTAGE() {
        return DAMAGE_ITEM_PERCENTAGE;
    }
    public CustomEnchant getTargetEnchant() {
        return targetEnchant;
    }
    public String ENCHANT_DISPLAY_NAME() {
        return ENCHANT_DISPLAY_NAME;
    }

    public static String SUCCESS_MESSAGE() {
        return SUCCESS_MESSAGE;
    }

    public static String BREAK_MESSAGE() {
        return BREAK_MESSAGE;
    }

    public static String DAMAGE_MESSAGE() {
        return DAMAGE_MESSAGE;
    }

    public static String DOWN_GRADE_MESSAGE() {
        return DOWN_GRADE_MESSAGE;
    }

    public static String FAIL_MESSAGE() {
        return FAIL_MESSAGE;
    }

    public CustomEnchant targetEnchant() {
        return targetEnchant;
    }
}
