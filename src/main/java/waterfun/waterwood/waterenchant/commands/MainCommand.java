package waterfun.waterwood.waterenchant.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.command.BukkitCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainCommand extends BukkitCommand {
    public MainCommand(String path){
        super(path);
    }
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        sender.sendMessage(BukkitPlugin.getPluginInfo());
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender,String[] args) {
        if(!checkPermission(sender,"waterenchant")) return List.of();
        ArrayList<String> completes = new ArrayList<>();
        if(args.length == 1) {
            if (sender instanceof Player) {
                completes.add("enchant");
            }
            completes.add("help");
        }
        return completes;
    }
}