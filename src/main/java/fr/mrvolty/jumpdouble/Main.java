package fr.mrvolty.jumpdouble;

import fr.mrvolty.jumpdouble.managers.Registration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        getLogger().info("=====================================");
        getLogger().info("Plugin initialization in progress ...");

        Registration.eventsRegister();


        getLogger().info("Listener Event was load successfully !");

        Registration.cmdRegisters();
        getLogger().info("Command was load successfully !");

        getLogger().info("=====================================");
    }

    @Override
    public void onDisable() {
    }

    public static Main getInstance() {
        return instance;
    }

    public String getPrefix(){
        return "§f[§6Double Jump§f] §7";
    }
}
