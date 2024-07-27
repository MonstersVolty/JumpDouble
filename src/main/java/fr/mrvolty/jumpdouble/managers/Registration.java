package fr.mrvolty.jumpdouble.managers;

import fr.mrvolty.jumpdouble.Main;
import fr.mrvolty.jumpdouble.commands.JumpControl;
import fr.mrvolty.jumpdouble.listeners.DoubleJump;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class Registration {

    private static Main instance = Main.getInstance();

    /** Method for logging plugin events **/
    public static void eventsRegister(){
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new DoubleJump(), instance);
    }

    /** Method for logging plugin commands **/
    public static void cmdRegisters(){
        instance.getCommand("doublejump").setExecutor(new JumpControl());
        instance.getCommand("doublejump").setTabCompleter(new JumpControl());
    }

}
