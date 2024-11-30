package waterfun.waterwood.waterenchant.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.waterwood.common.Colors;
import org.waterwood.common.StringProcess;
import org.waterwood.consts.COLOR;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.command.BukkitCommand;
import org.waterwood.plugin.bukkit.util.CustomEnchant;
import waterfun.waterwood.waterenchant.Methods.EnchantManager;
import waterfun.waterwood.waterenchant.WaterEnchant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EnchantCommand extends BukkitCommand {
    public static List<String> ACTION = List.of("set","add","increase","decrease","remove","clear","info");
    public EnchantCommand(String path) {
        super(path);
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(! (checkPermission(sender,"waterenchant.enchant") && isPlayerExecute(sender))) return false;
        if(! checkArgs(sender,args,3,4)) return false;
        String action_name = args[1];
        String enchantNameKey = args[2];
        int level = 1;

        if (! ACTION.contains(action_name)){ // illegal action
            sender.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("illegal-action-message")
                    .formatted(action_name), COLOR.RED));
            return false;
        }
        if(! checkPermission(sender,"waterenchant.enchant")) return false;
        if(args.length > 3) {
            if (!checkArgNumeric(sender, args[3])) return false;
            level = Integer.parseInt(args[3]);
        }
        Player player = (Player)sender;

        Optional<ItemStack> optionalItemStack = Optional.ofNullable(player.getInventory().getItem(player.getInventory().getHeldItemSlot()));

        if(EnchantManager.getEnchantments().containsKey(enchantNameKey)){ // check enchant is exist.
            CustomEnchant enchantment = EnchantManager.getEnchantment(enchantNameKey);
            if(action_name.equals("info")){ //give the highest priority
                sender.sendMessage("§l§7*§r " +
                        BukkitPlugin.getPluginMessage("enchant-info-shown-message")
                                .formatted(enchantment.getDisplayName())
                );
                for(String str: enchantment.getDescription()){
                    sender.sendMessage("§l§7>§r " + str);
                }
                return true;
            }
            if (optionalItemStack.map(itemStack -> itemStack.getItemMeta() == null).orElse(true)) { // item in player's hand
                sender.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("hand-empty-message"), COLOR.RED));
                return false;
            }

            ItemStack item = optionalItemStack.get();

            if(! checkArgNumIn(sender,level,enchantment.getMinLevel(),enchantment.getMaxLevel())) return false; // check args nums

            int currentLevel = EnchantManager.getLevel(item,enchantment);
            String successMessage = null;
            switch (action_name) {
                case "set" -> {
                    if (checkArgs(sender, args, 4)) {
                        EnchantManager.setEnchant(item, enchantment, level);
                        successMessage = EnchantSetMessage(enchantment, level);
                    }
                }
                case "add" -> {
                    if (currentLevel == 0) {
                        EnchantManager.setEnchant(item, enchantment, level);
                        successMessage = EnchantSuccessMessage(enchantment);
                    } else {
                        if (!checkArgs(sender, args, 4)) return false;
                        int setLevel = Math.max(enchantment.getMinLevel(), Math.min(currentLevel + level, enchantment.getMaxLevel()));
                        EnchantManager.setEnchant(item, enchantment, setLevel);
                        successMessage = EnchantSetMessage(enchantment,setLevel);
                    }
                }
                case "remove", "clear" -> {
                    if (EnchantManager.hasEnchant(item, enchantment)) {
                        EnchantManager.removeEnchant(item, enchantment);
                        successMessage = EnchantRemoveMessage(enchantment);
                    }else{
                        sender.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("item-enchant-missing-message"),COLOR.RED));
                        return false;
                    }
                }
                case "increase" -> successMessage = EnchantSetMessage(enchantment,
                        increaseEnchantLevel(item, enchantment, level));
                case "decrease" -> successMessage = EnchantSetMessage(enchantment,
                        increaseEnchantLevel(item, enchantment, -1 * level));
            }
            if(successMessage != null) sender.sendMessage(successMessage);

            ItemMeta meta = item.getItemMeta();
            String enchantDisplayName = enchantment.getDisplayName();

            CustomEnchant upperEnchant = EnchantManager.getRarestEnchant(item);

            List<String> lores = meta.getLore();
            if(lores == null) lores = new ArrayList<>();

            lores.removeIf(lore -> lore.startsWith(enchantDisplayName)); //clear the old lore
            lores.removeIf(lore -> lore.startsWith("§8*§r "));
            currentLevel = EnchantManager.getLevel(item,enchantment);
            if (currentLevel != 0) {
                if(currentLevel == enchantment.getMinLevel() && enchantment.getMaxLevel() == 1) {
                    lores.add(enchantDisplayName);
                }else{
                    lores.add(enchantDisplayName + " §7" + StringProcess.toRoman(currentLevel) + "§r"); // add new lore
                }
            }

            if (upperEnchant != null) {
                String display = upperEnchant.getDisplay();
                if(display != null){
                    if(!display.isEmpty()){
                        lores.add("§8*§r " +display );
                    }
                }
            }

            meta.setLore(lores);
            item.setItemMeta(meta);

            return true;
        }
        // can't find custom enchant
        sender.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("enchant-not-found-message")
                , COLOR.RED));
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender,String[] args) {
        if (!(checkPermission(sender, "waterenchant.enchant") && isPlayerExecute(sender))) return List.of();
        Player player = (Player) sender;
        ArrayList<String> completes = new ArrayList<>();
        if(args.length == 2){
            return ACTION;
        }else if(args.length == 3){
            ItemStack item = player.getInventory().getItemInMainHand();
            if(List.of("clear","remove").contains(args[1])){
                return EnchantManager.getItemEnchanted(item)
                        .stream()
                        .map(enchant -> enchant.getKey().getKey()).collect(Collectors.toList());
            }
            for(CustomEnchant enchantment : EnchantManager.getEnchantments().values()){
                if (enchantment.canEnchantItem(item)){
                    completes.add(enchantment.getKey().getKey());
                }
            }
        }else if(args.length == 4){
            if (EnchantManager.getEnchantments().containsKey(args[2])) {
                CustomEnchant enchantment = EnchantManager.getEnchantment(args[2]);
                if(List.of("set","add").contains(args[1])) {
                    for (int i = enchantment.getMinLevel(); i <= enchantment.getMaxLevel(); i++) {
                        completes.add(String.valueOf(i));
                    }
                }
                if(List.of("increase","decrease").contains(args[2])){
                    completes.addAll(List.of("1","-1"));
                }
            }
        }

        return completes;
    }

    private int increaseEnchantLevel(ItemStack item,CustomEnchant enchant,int lvl){
        int current = EnchantManager.getLevel(item,enchant);
        int setLevel = Math.max(enchant.getMinLevel(),
                Math.min(current + lvl,enchant.getMaxLevel())); //bind the value within enchant' max level & min level
        EnchantManager.setEnchant(item,enchant,setLevel);
        return setLevel;
    }

    private static String EnchantSuccessMessage(CustomEnchant enchant){
        return Colors.coloredText(BukkitPlugin.getPluginMessage("enchant-success-message", enchant.getName())," "
                , COLOR.GREEN
                ,enchant.getNameColor()
                , COLOR.GREEN);
    }
    private static String EnchantSetMessage(CustomEnchant enchant, int level){
        return Colors.coloredText(
                BukkitPlugin.getPluginMessage("enchant-set-success-message",enchant.getName(),level),
                " ", COLOR.GREEN, enchant.getNameColor(), COLOR.GREEN, COLOR.GOLD, COLOR.GREEN);

    }
    private static String EnchantRemoveMessage(CustomEnchant enchant){
        return Colors.coloredText(
                BukkitPlugin.getPluginMessage("enchant-remove-success-message",enchant.getName())
                ," ", COLOR.GREEN,
                enchant.getNameColor(),
                COLOR.GREEN
        );
    }
}
