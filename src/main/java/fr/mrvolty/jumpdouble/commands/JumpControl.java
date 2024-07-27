package fr.mrvolty.jumpdouble.commands;

import fr.mrvolty.jumpdouble.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class JumpControl implements CommandExecutor, TabCompleter {

    public static Set<Player> whoWantJump = new HashSet<>();
    private String prefix = Main.getInstance().getPrefix();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        /* Method that checks whether the sender is a player */
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + "Este comando es sólo para jugadores.");
            return false;
        }

        Player p = (Player) sender;

        /* Method that checks whether the player has the right permission */
        if (!p.hasPermission("doublejump.control")) {
            p.sendMessage(prefix + "No tiene permiso para ejecutar este comando.");
            return false;
        }

        /* Method that checks whether the player's order is the right lenght */
        if (args.length != 1) {
            p.sendMessage(Main.getInstance().getPrefix() + "El comando es: §7/doublejump [on|off]");
            return false;
        }

        String state = args[0].toLowerCase();

        /* Method that checks whether the command argument is: on / off */
        if (!state.equals("on") && !state.equals("off")) {
            p.sendMessage(prefix + "El comando es: §7/doublejump [on|off]");
            return false;
        }

        boolean isEnabling = state.equals("on");

        /* Method that performs an action depending on whether the player has set it to on or off */
        if (isEnabling) {
            if (whoWantJump.add(p)) {
                p.setFlying(false);
                p.setAllowFlight(true);
                p.sendMessage(prefix + "Has activado la opción §7DoubleJump.");
            } else {
                p.sendMessage(prefix + "Ya ha activado la opción §7DoubleJump.");
            }
        } else {
            if (whoWantJump.remove(p)) {
                p.setFlying(false);
                p.setAllowFlight(false);
                p.sendMessage(prefix + "Has desactivado la opción §7DoubleJump.");
            } else {
                p.sendMessage(prefix + "Ya ha desactivado la opción §7DoubleJump.");
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {

        List<String> completions = new ArrayList<>();
        List<String> results = new ArrayList<>();

        /* Method to check whether we are in the right configuration */
        if (args.length == 1 && sender.hasPermission("doublejump.control")){
            completions.addAll(Arrays.asList("on", "off"));
        }

        /* Method that checks whether the letter entered by the player is the beginning of the word, otherwise nothing is displayed. */
        for (String str : completions){
            if (str.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                results.add(str);
            }
        }

        return results.isEmpty() ? null : results;
    }
}