package waterfun.waterwood.waterenchant;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.waterwood.plugin.bukkit.BukkitPlugin;
import org.waterwood.plugin.bukkit.command.CommandManager;
import waterfun.waterwood.waterenchant.Enchantments.LightningEnchant;
import waterfun.waterwood.waterenchant.Events.EnchantEvents;
import waterfun.waterwood.waterenchant.Methods.EnchantManager;
import waterfun.waterwood.waterenchant.Methods.Methods;
import waterfun.waterwood.waterenchant.commands.EnchantCommand;
import waterfun.waterwood.waterenchant.commands.MainCommand;

public final class WaterEnchant extends BukkitPlugin {
    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        this.initialization();
        this.showPluginTitle("WE");
        this.loadConfig(false);
        this.checkUpdate("Wcatt","WaterEnchant","1.0.0");
        Methods.init(this);
        registerCommands();
        registerEnchants();
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
                new EnchantCommand("enchant")
                );
    }

    public void registerEvents(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new EnchantEvents(),this);
    }
    public void registerEnchants(){
        EnchantManager.registerEnchants(
                new LightningEnchant()
        );
    }
    public static JavaPlugin getInstance(){
        return instance;
    }
}
