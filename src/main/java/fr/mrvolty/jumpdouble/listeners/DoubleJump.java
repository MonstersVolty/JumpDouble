package fr.mrvolty.jumpdouble.listeners;

import fr.mrvolty.jumpdouble.commands.JumpControl;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import java.util.ArrayList;
import java.util.List;

public class DoubleJump implements Listener {

    private List<String> players = new ArrayList<>();

    @EventHandler
    public void onChangeGameMode(PlayerGameModeChangeEvent e){
        if (e.getNewGameMode().equals(GameMode.SURVIVAL)){
            e.getPlayer().setAllowFlight(true);
            e.getPlayer().setFlying(false);
        }
    }


    @EventHandler
    public void onPlayerJump(PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();

        if (!JumpControl.whoWantJump.contains(p)){
            return;
        }else if (p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR || p.isFlying() || players.contains(p.getName())) {
            return;
        } else {
            players.add(p.getName());
            e.setCancelled(true);
            p.setAllowFlight(false);
            p.setFlying(false);
            p.setVelocity(e.getPlayer().getLocation().getDirection().multiply(0.5).setY(0.5));
            p.setFallDistance(100);
        }

    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player p = (Player) e.getEntity();
            if (players.contains(p.getName())) {
                e.setCancelled(true);
                p.setAllowFlight(true);
                p.setFlying(false);
                players.remove(p.getName());
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (players.contains(e.getPlayer().getName())) players.remove(e.getPlayer().getName());
    }
}
