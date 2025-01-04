package waterfun.waterwood.waterenchant.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.waterwood.utils.Colors;
import org.waterwood.enums.COLOR;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.command.BukkitCommand;
import org.waterwood.plugin.bukkit.custom.CustomEnchant;
import waterfun.waterwood.waterenchant.Methods.EnchantManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListCommand extends BukkitCommand {

    public ListCommand(String path) {
        super(path);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (sender instanceof Player player){
            ItemStack item = player.getInventory().getItemInMainHand();

            if(item.getType() == Material.AIR) { // item in player's hand
                sender.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("hand-empty-message"), COLOR.RED));
                return false;
            }

            player.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("enchant-list-player-message"), COLOR.GOLD));
            List<CustomEnchant> enchants = EnchantManager.getEnchantments().values().stream().toList();
            enchants.forEach(enchant -> {
                if (enchant.canEnchantItem(item)) {
                    sender.sendMessage(enchant.getDisplayName() + " §7" + enchant.getKey().getKey() + "§r");
                }
            });
            return true;
        }else{
            sender.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("enchant-list-all-message"), COLOR.GOLD));
            EnchantManager.getEnchantments().values().forEach(enchant -> {
                sender.sendMessage( "§l§7*§r " + enchant.getDisplayName() + " | " +
                        BukkitPlugin.getPluginMessage("can-enchant-message")
                                .formatted(enchant.getAllowEnchantItems()
                                        .stream()
                                        .collect(Collectors.joining(",", "[", "]"))
                                )
                );
            });
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        List<String> completes = new ArrayList<>();
        if(args.length == 2) {
            if (sender instanceof Player player) {
                EnchantManager.getEnchantments().values().forEach(enchant -> {
                    if (enchant.canEnchantItem(player.getInventory().getItemInMainHand())) {
                        completes.add(enchant.getKey().getKey());
                    }
                });
            } else {
                EnchantManager.getEnchantments().values().forEach(enchant ->
                        completes.add(enchant.getKey().getKey()));
            }
        }
        return completes;
    }
}
