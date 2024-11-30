package waterfun.waterwood.waterenchant.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.waterwood.common.Colors;
import org.waterwood.common.StringProcess;
import org.waterwood.consts.COLOR;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.command.BukkitCommand;
import org.waterwood.plugin.bukkit.util.CustomEnchant;
import org.waterwood.plugin.bukkit.util.ItemMetaProcesser;
import waterfun.waterwood.waterenchant.Methods.EnchantManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InfoCommand extends BukkitCommand {

    public InfoCommand(String path) {
        super(path);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(sender instanceof Player player){
            ItemStack item = player.getInventory().getItemInMainHand();
                if(args.length > 1){
                    String enchantNameKey = args[1];
                    if(EnchantManager.getEnchantments().containsKey(enchantNameKey)) { // check enchant is exist.
                        //shown enchant infos
                        CustomEnchant enchant = EnchantManager.getEnchantment(enchantNameKey);
                        sender.sendMessage(BukkitPlugin.getPluginMessage("enchant-info-shown-message").formatted(enchant.getDisplayName())
                                + " §6ID:§r §l" + enchant.getName() + "§r");
                        for(String str: enchant.getDescription()){
                            sender.sendMessage(ChatColor.BOLD + String.valueOf(ChatColor.GRAY) + "> " + ChatColor.RESET + str);
                        }
                        sender.sendMessage( ChatColor.BOLD + String.valueOf(ChatColor.DARK_GRAY) + "*"   +
                                BukkitPlugin.getPluginMessage("can-enchant-message")
                                        .formatted(enchant.getAllowEnchantItems()
                                                        .stream()
                                                        .collect(Collectors.joining(",", "[", "]"))
                                        )
                        );
                    }else{ // can't find custom enchant
                        sender.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("enchant-not-found-message")
                                , COLOR.RED));
                        return false;
                    }
                }else{ //check enchants in player hand
                    if(item.getType() == Material.AIR) { // item in player's hand
                        sender.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("hand-empty-message"), COLOR.RED));
                        return false;
                    }
                    List<CustomEnchant> ItemEnchant = EnchantManager.getItemEnchanted(item);
                    if(ItemEnchant.isEmpty()) {
                        player.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("no-enchant-message"),COLOR.RED));
                        return false;
                    }
                    player.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("known-enchant-info-message"),COLOR.GOLD));
                    ItemEnchant.forEach(enchant ->{
                        player.sendMessage(
                                ChatColor.GRAY + "> " + enchant.getDisplayName()+ " " + ChatColor.GRAY+
                                        StringProcess.toRoman(EnchantManager.getLevel(item,enchant))
                        );

                    });
                }
            return true;
        }
        sender.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("unknown-command-message"),COLOR.RED));
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        List<String> completes = new ArrayList<>();
        if(args.length == 2){
                for(CustomEnchant enchantment : EnchantManager.getEnchantments().values()){
                    completes.add(enchantment.getKey().getKey());
                }
        }
        return completes;
    }
}
