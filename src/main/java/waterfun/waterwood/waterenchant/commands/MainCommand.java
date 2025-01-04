package waterfun.waterwood.waterenchant.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.waterwood.utils.Colors;
import org.waterwood.enums.COLOR;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.command.BukkitCommand;

import java.util.ArrayList;
import java.util.List;

public class MainCommand extends BukkitCommand {
    public MainCommand(String path){
        super(path);
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage(BukkitPlugin.getPluginInfo());
        sender.sendMessage(Colors.coloredText(BukkitPlugin.getPluginMessage("help-info-message"), COLOR.GOLD));
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender,String[] args) {
        if(!checkPermission(sender,"waterenchant")) return List.of();
        ArrayList<String> completes = new ArrayList<>();
        if(args.length == 1) {
            if (sender instanceof Player) {
                completes.addAll(List.of("enchant","list","give"));
            }
            completes.addAll(List.of("info","help","reload"));
        }
        return completes;
    }
}
