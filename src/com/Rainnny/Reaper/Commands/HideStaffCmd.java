package com.Rainnny.Reaper.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.Rainnny.Reaper.StaffMode;
import com.Rainnny.Reaper.Utils.ColorUtil;

public class HideStaffCmd implements CommandExecutor {

	private StaffMode core;
	public HideStaffCmd(StaffMode core) {
		this.core = core;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("hide.staff") || !(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "No permission.");
			return true;
		}
		
		if(args.length != 0) {
			sender.sendMessage(ChatColor.DARK_RED + "Usage: /hidestaff");
			return true;
		}
		
		Player p = (Player) sender;
		if(!core.hiddenStaff.contains(p)) {
			if(!core.getStaffOnline().isEmpty()) {
				for(Player staff : core.getStaffOnline()) {
					p.hidePlayer(staff);
					core.hiddenStaff.add(p);
				}
			}
			p.sendMessage("§cSuccesfully disabled visibility of all staff.");
		} else {
			if(!core.getStaffOnline().isEmpty()) {
				for(Player staff : core.getStaffOnline()) {
					p.showPlayer(staff);
					core.hiddenStaff.remove(p);
				}
			}
			p.sendMessage("§cSuccesfully enabled visibility of all staff.");
		}
		
		return true;
	}

}
