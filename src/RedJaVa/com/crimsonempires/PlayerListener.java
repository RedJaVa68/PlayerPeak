package RedJaVa.com.crimsonempires;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
public class PlayerListener implements Listener {
	public PlayerPeakCore p;
	public PlayerListener(PlayerPeakCore p) {
		this.p = p;
	}
	public MilestoneManager mm = new MilestoneManager(p);
	@EventHandler (priority = EventPriority.MONITOR)
	public void onPlayerJoinEvent(PlayerJoinEvent e) {
		int playerCount = p.getServer().getOnlinePlayers().length;
		if (playerCount > p.getConfig().getInt("peak-player-count")) {
			p.getConfig().set("peak-player-count", playerCount);
			p.saveConfig();
			Boolean milestoneReached = false;
			for (String ms : mm.listMilestones()) {
				if (playerCount > mm.getPeak(ms, 0)) {
					milestoneReached = true;
					p.broadcastMessage(decodeKeys(mm.getMessage(ms, mm.getDefaultMessage()), mm.getCash(ms, 0.0)));
					for (Player pl : p.getServer().getOnlinePlayers()) {
						PlayerPeakCore.economy.depositPlayer(pl.getName(), mm.getCash(ms, 0.0));
					}
				}
			}
			if (!milestoneReached) {
				p.broadcastMessage(decodeKeys(p.getConfig().getString("new-peak-message"), p.getConfig().getDouble("cash")));
				for (Player pl : p.getServer().getOnlinePlayers()) {
					if (e.getPlayer().hasPermission("peak.get")) {
						PlayerPeakCore.economy.depositPlayer(pl.getName(), p.getConfig().getDouble("cash"));
					}
				}
			}
		}
	}
	public String decodeKeys(String m, Double c) {
		m = m.replaceAll("&peak&", String.valueOf(p.getConfig().getInt("peak-player-count")));
		m = m.replaceAll("&money&", c.toString());
		m = m.replaceAll("&", "§");
		return m;
	}
}
