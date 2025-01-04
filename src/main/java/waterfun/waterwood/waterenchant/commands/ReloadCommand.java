package waterfun.waterwood.waterenchant.commands;

import org.bukkit.command.CommandSender;
import org.waterwood.utils.Colors;
import org.waterwood.enums.COLOR;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.command.BukkitCommand;
import waterfun.waterwood.waterenchant.Methods.EnchantManager;
import waterfun.waterwood.waterenchant.Methods.Methods;

import java.util.List;


public class ReloadCommand extends BukkitCommand {
    private  static final List<String> allConfigs = List.of("config", "enchant","all");
    public ReloadCommand(String path) {
        super(path);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if(!checkArgs(sender,args,1,2)) return false;
        if(!checkPermission(sender,"waterenchant.admin")) return false;
        if(args.length == 1){
            Methods.configReload();
            enchantReload(sender);
            sender.sendMessage(Colors.coloredText(
                    BukkitPlugin.getPluginMessage("config-reload-completed-message"), COLOR.GREEN
            ));
        }else{
            switch (args[1]){
                case "config" ->{
                    Methods.configReload();
                }
                case "enchant" ->{
                    enchantReload(sender);
                }
                case "all" ->{
                    Methods.configReload();
                    enchantReload(sender);
                }
                default -> {
                    sender.sendMessage(Colors.coloredText(
                            BukkitPlugin.getPluginMessage("incorrect-config-file-message",args[1],allConfigs),
                            COLOR.RED
                    ));
                    return false;
                }
            }
            sender.sendMessage(Colors.coloredText(
                    BukkitPlugin.getPluginMessage("config-file-reload-message",args[1]), COLOR.GREEN
            ));
        }
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if(args.length == 2){
            return  allConfigs;
        }
        return List.of();
    }
    private void enchantReload(CommandSender sender){
        Methods.enchantInfoReload();
        sender.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("load-enchant-count-message", EnchantManager.getEnchantments().values().size()),
                " ", COLOR.GREEN,COLOR.GOLD,COLOR.GREEN));
    }
}
