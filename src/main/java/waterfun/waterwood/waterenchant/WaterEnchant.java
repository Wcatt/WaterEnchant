package waterfun.waterwood.waterenchant;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.waterwood.utils.Colors;
import org.waterwood.enums.COLOR;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.command.CommandManager;
import waterfun.waterwood.waterenchant.Events.PlayerEvents;
import waterfun.waterwood.waterenchant.Methods.EnchantManager;
import waterfun.waterwood.waterenchant.Methods.Methods;
import waterfun.waterwood.waterenchant.commands.*;

public final class WaterEnchant extends BukkitPlugin {
    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        this.initialization();
        this.showPluginTitle("WaterET");
        this.loadConfig(false);
        this.checkUpdate("Wcatt","WaterEnchant","1.0.0");
        Methods.init(this);
        registerCommands();
        registerEnchants();
        registerItems();
        registerEvents();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerCommands(){
        CommandManager commandManager = new CommandManager(this,"waterenchant");
        getCommand("WaterEnchant").setExecutor(commandManager);
        getCommand("WaterEnchant").setTabCompleter(commandManager);
        commandManager.registerCommands(
                new MainCommand("root"),
                new EnchantCommand("enchant"),
                new InfoCommand("info"),
                new HelpCommand("help"),
                new ListCommand("list"),
                new ReloadCommand("reload"),
                new GiveItemCommand("give")
                );
    }

    public void registerEvents(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerEvents(),this);
    }
    public void registerEnchants(){
        Methods.registerEnchants();
        getPluginMessage("load-enchant-count-message",EnchantManager.getEnchantments().values().size());
        logMsg(Colors.coloredText(getPluginMessage("load-enchant-count-message",EnchantManager.getEnchantments().values().size()),
                " ", COLOR.GREEN,COLOR.GOLD,COLOR.GREEN));
    }
    public void registerItems(){
        Methods.registerItems();
    }
    public static JavaPlugin getInstance(){
        return instance;
    }
}
