package waterfun.waterwood.waterenchant.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.waterwood.enums.COLOR;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.command.BukkitCommand;
import org.waterwood.plugin.bukkit.custom.CustomEnchant;
import org.waterwood.plugin.bukkit.custom.CustomItem;
import org.waterwood.processor.MessageProcessor;
import org.waterwood.utils.Colors;
import waterfun.waterwood.waterenchant.Methods.EnchantManager;
import waterfun.waterwood.waterenchant.Methods.ItemManager;
import waterfun.waterwood.waterenchant.Methods.Methods;
import waterfun.waterwood.waterenchant.items.EnchantBook;

import java.util.List;

public class GiveItemCommand extends BukkitCommand {
    public GiveItemCommand(String path) {
        super(path);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(sender.hasPermission("waterenchant.admin") || sender.hasPermission("waterenchant.give")) {
            if(!isPlayerExecute(sender)){ return false;}
            if(!checkArgs(sender,args,2,3)){ return false; }
            if(ItemManager.getRegisteredItems().containsKey(args[1])){
                CustomItem enchantItem = ItemManager.getRegisteredItems().get(args[1]);
                String disPlayerName = args[2];
                Player player = (Player) sender;
                ItemStack playerItem = new ItemStack(enchantItem.getMaterial());
                ItemMeta meta = playerItem.getItemMeta();
                PersistentDataContainer container = meta.getPersistentDataContainer();
                if(enchantItem instanceof EnchantBook){
                    if(!EnchantManager.getEnchantments().containsKey(args[2])){
                        player.sendMessage(Colors.coloredText(
                                BukkitPlugin.getPluginMessage("enchant-not-found-message"),
                                COLOR.RED
                        ));
                        return false;
                    }
                    CustomEnchant enchant = EnchantManager.getEnchantment(args[2]);
                    EnchantBook enchantBook = new EnchantBook(enchant);
                    container.set(Methods.getNamespacedKey(enchantBook.getKey(), "target-enchant"),
                            PersistentDataType.STRING,
                            enchantBook.getTargetEnchant().getName());
                    container.set(
                            Methods.getNamespacedKey(enchantBook.getKey(),"success-rate"),
                            PersistentDataType.DOUBLE,
                            enchantBook.SUCCESS_RATE());
                    container.set(
                            Methods.getNamespacedKey(enchantBook.getKey(),"break-rate"),
                            PersistentDataType.DOUBLE,
                            enchantBook.BREAK_RATE());
                    container.set(
                            Methods.getNamespacedKey(enchantBook.getKey(),"down-grade-rate"),
                            PersistentDataType.DOUBLE,
                            enchantBook.DOWN_GRADE_RATE());
                    container.set(
                            Methods.getNamespacedKey(enchantBook.getKey(),"damage-item-rate"),
                            PersistentDataType.DOUBLE,
                            enchantBook.DAMAGE_ITEM_RATE());
                    container.set(
                            Methods.getNamespacedKey(enchantBook.getKey(),"damage-item-percent-rate"),
                            PersistentDataType.DOUBLE,
                            enchantBook.DAMAGE_ITEM_PERCENTAGE());

                    meta.setLore(enchantBook.getDescription());
                    meta.setDisplayName(enchantBook.getDisplayName());
                    disPlayerName = enchantBook.getDisplayName();
                }
                playerItem.setItemMeta(meta);
                player.getInventory().addItem(playerItem);
                player.sendMessage(MessageProcessor.successMessage("success-give-item-message"));
                return true;
            }else{
                sender.sendMessage(
                        Colors.coloredText(BukkitPlugin.getPluginMessage("item-not-found-message")
                        ," ",COLOR.RED,COLOR.GRAY));
            }
        }else{
            sender.sendMessage(MessageProcessor.errorMessage("no-permission-message"));
            return false;
        }
        sender.sendMessage(MessageProcessor.errorMessage("unknown-command-message"));
        return false;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length == 2){
            return ItemManager.getRegisteredItems().keySet().stream().toList();
        }else if(args.length == 3){
            if(args[1].equals(new EnchantBook().getKey().getKey())){
                return EnchantManager.getEnchantments().keySet().stream().toList();
            }
            return List.of();
        }
        return List.of();
    }
}
