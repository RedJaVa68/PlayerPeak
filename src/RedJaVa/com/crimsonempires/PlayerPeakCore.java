package RedJaVa.com.crimsonempires;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Level;
public class PlayerPeakCore extends JavaPlugin {
	public static Economy economy = null;
	public void onEnable() {
		if (getServer().getPluginManager().getPlugin("Vault") != null) {
			this.saveDefaultConfig();
			this.getConfig().options().copyDefaults(true);
			getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
			setupEconomy();
		} else {
			getLogger().log(Level.SEVERE, "Vault cannot be found! Disabling Plugin.");
			getServer().getPluginManager().disablePlugin(this);
		}
	}
	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		if (l.equalsIgnoreCase("peak") && s.hasPermission("peak.command")) {
			if (a.length >= 1) {
				if(l.equalsIgnoreCase("info")) {
					sendMessage(s, "PlayerPeak keeps a record of the highest number of people that have been on the server at once.");
					sendMessage(s, "Every time the server's player count exceeds the previous record every player will receive money.");
				} else if (a[0].equalsIgnoreCase("reload") && s.hasPermission("peak.command.admin")) {
					sendMessage(s, "Configuration Reloaded.");
					this.reloadConfig();
				}
			} else {
				sendMessage(s, "The current peak is: " + getConfig().getInt("peak-player-count"));
			}
		}
		return true;
	}
	/**
	 * send a pre-formatted message to the server's players. Sender is "Server"
	 * @param m Message to be broadcasted
	 */
	public void broadcastMessage(String m) {
		Bukkit.broadcastMessage(ChatColor.GOLD + "| " + ChatColor.DARK_RED + "PlayerPeak" + ChatColor.GOLD + " | " + ChatColor.WHITE + m);
	}
	/**
	 * send a pre-formatted message to the CommandSender.
	 * @param s CommandSender to send the message to
	 * @param m Message to send to the player
	 */
	public void sendMessage (CommandSender s, String m) {
		if (s instanceof Player)
			s.sendMessage(ChatColor.GOLD + "| " + ChatColor.DARK_RED + "PlayerPeak" + ChatColor.GOLD + " | " + ChatColor.WHITE + m);
	}
	private boolean setupEconomy(){
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}
		return (economy != null);
	}
}