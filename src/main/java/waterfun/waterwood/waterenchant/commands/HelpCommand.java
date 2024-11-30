package waterfun.waterwood.waterenchant.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.waterwood.common.Colors;
import org.waterwood.consts.COLOR;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.command.BukkitCommand;
import waterfun.waterwood.waterenchant.WaterEnchant;

import java.util.List;

public class HelpCommand extends BukkitCommand {

    public HelpCommand(String path) {
        super(path);
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        List<String> commands = sender instanceof Player ? List.of("help","list","info","enchant") : List.of("help","list");
        sender.sendMessage(WaterEnchant.getPluginInfo());
        commands.forEach(command ->
            sender.sendMessage(Colors.coloredText(
                    BukkitPlugin.getPluginMessage(command + "-info-message"),"-"
                    , COLOR.GRAY, COLOR.GOLD))
        );
        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return List.of();
    }
}
