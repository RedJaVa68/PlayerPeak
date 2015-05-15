package RedJaVa.com.crimsonempires;

import java.util.List;
public class MilestoneManager {
	PlayerPeakCore p;
	MilestoneManager(PlayerPeakCore p) {
		this.p = p;
	}
	public List<String> listMilestones() {
		return p.getConfig().getStringList("milestones");
	}
	public int getPeak(String m, int d) {
		if (p.getConfig().contains("milestones." + m + ".peak")) {
			return p.getConfig().getInt("milestones." + m + ".peak");
		} else
			return d;
	}
	public double getCash(String m, double d) {
		if (p.getConfig().contains("milestones." + m + ".cash")) {
			return p.getConfig().getDouble("milestones." + m + ".cash");
		} else
			return d;
	}
	public String getMessage(String m, String d) {
		if (p.getConfig().contains("milestones." + m + ".message")) {
			return p.getConfig().getString("milestones." + m + ".message");
		} else
			return d;
	}
	public String getDefaultMessage() {
		if (p.getConfig().getString("new-milestone-message").substring(0).length() > 0) {
			return p.getConfig().getString("new-milestone-message");
		} else {
			return "Invalid default milestone Message.";
		}
	}
}
