package com.Rainnny.Reaper.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.Rainnny.Reaper.StaffMode;
import com.Rainnny.Reaper.Utils.ColorUtil;

public class StaffModeCmd implements CommandExecutor {
	
	private StaffMode core;
	public StaffModeCmd(StaffMode core) {
		this.core = core;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!sender.hasPermission("railed.staff") || !(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to use this.");
			return true;
		}
		if(args.length == 0) {
			Player p = (Player) sender;
			
			if(!core.inStaffMode.contains(p)) {
				core.setStaffMode(p, true);
				p.sendMessage(ColorUtil.color(StaffMode.PREFIX + "Your staff mode has been &aenabled" + StaffMode.O + "!"));
			} else {
				core.setStaffMode(p, false);
				p.sendMessage(ColorUtil.color(StaffMode.PREFIX + "Your staff mode has been &cdisabled" + StaffMode.O + "!"));
			}
		}
		return true;
	}

}
