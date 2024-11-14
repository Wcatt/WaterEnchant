package waterfun.waterwood.waterenchant.commands;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        if(args.length > 3) {
            if (!ckeckArgNumeric(sender, args[3])) return false;
            level = Integer.parseInt(args[3]);
        }
        Player player = (Player)sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        if(EnchantManager.getEnchantments().containsKey(enchantNameKey)){ // check enchant is exist.
            CustomEnchant enchantment = EnchantManager.getEnchantment(enchantNameKey);

            if(action_name.equals("info")){ //give the highest priority
                sender.sendMessage( "§l§70§r " +
                        BukkitPlugin.getPluginMessage("enchant-info-shown-message")
                                .formatted(enchantment.getDisplayName())
                );
                for(String str: enchantment.getDescription()){
                    sender.sendMessage("§l§7>§r " + str);
                }
                return true;
            }

            if(item.getType() == Material.AIR) { // item in player's hand
                sender.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("hand-empty-message"), COLOR.RED));
                return false;
            }

            int minLvl = enchantment.getMinLevel();
            int maxLvl = enchantment.getMaxLevel();
            ItemMeta meta = item.getItemMeta();
            if(meta != null) {

                if(checkArgNumIn(sender,level,minLvl,maxLvl)){ // add lores
                        List<String> lores = meta.getLore();
                        if(lores == null) lores = new ArrayList<>();
                        lores.add(
                                enchantment.getDisplayName() + " " + StringProcess.toRoman(level)
                        );
                        meta.setLore(lores);
                        item.setItemMeta(meta);
                }

                String successMessage = null;
                switch (action_name){
                    case "set":
                        if(args.length > 3 && EnchantManager.hasEnchant(item,enchantment)){
                            EnchantManager.addEnchant(item,enchantment,level);
                            successMessage =  Colors.coloredText(
                                    BukkitPlugin.getPluginMessage("enchant-set-success-message",enchantment.getName(),level),
                                    " ", COLOR.GREEN, enchantment.getNameColor(), COLOR.GREEN, COLOR.GOLD, COLOR.GREEN);
                        }
                    case "add":
                        if(! EnchantManager.hasEnchant(item,enchantment)){
                            EnchantManager.addEnchant(item,enchantment,level);
                            successMessage = Colors.coloredText(BukkitPlugin.getPluginMessage("enchant-success-message", enchantment.getName())," "
                                    , COLOR.GREEN
                                    ,enchantment.getNameColor()
                                    , COLOR.GREEN);
                        }
                        break;
                    case "remove":
                    case "clear":
                        if(EnchantManager.hasEnchant(item,enchantment)){
                            successMessage = Colors.coloredText(
                                    BukkitPlugin.getPluginMessage("enchant-remove-success-message",enchantment.getName())
                                    ," ", COLOR.GREEN,
                                    enchantment.getNameColor(),
                                    COLOR.GREEN
                            );
                        }
                        break;
                }
                if(successMessage != null) sender.sendMessage(successMessage);
                return true;
            }
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
                return EnchantManager.getItemEnchanted(item);
            }
            for(CustomEnchant enchantment : EnchantManager.getEnchantments().values()){
                if (enchantment.canEnchantItem(item)){
                    completes.add(enchantment.getKey().getKey());
                }
            }
        }else if(args.length == 4){
            if(List.of("set","add","increase","decrease").contains(args[1])){
                if (EnchantManager.getEnchantments().containsKey(args[2])) {
                    CustomEnchant enchantment = EnchantManager.getEnchantment(args[2]);
                    for (int i = enchantment.getMaxLevel(); i <= enchantment.getMaxLevel(); i++) {
                        completes.add(String.valueOf(i));
                    }
                }
            }
        }

        return completes;
    }
}
