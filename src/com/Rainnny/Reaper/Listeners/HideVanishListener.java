package com.Rainnny.Reaper.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.Rainnny.Reaper.StaffMode;

public class HideVanishListener implements Listener {

	private StaffMode core;
	public HideVanishListener(StaffMode core) {
		this.core = core;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if(core.inVanish.size() > 0) {
			for(Player p : core.inVanish) {
				e.getPlayer().hidePlayer(p);
			}
		}
		
		if(e.getPlayer().hasPermission("railed.staff")) {
			
			if(core.hiddenStaff.contains(e.getPlayer())) {
				for(Player staff : core.getStaffOnline()) {
					if(staff != e.getPlayer()) {
						e.getPlayer().hidePlayer(staff);
					}
					
				}
			}
			
			for(Player staff : core.hiddenStaff) {
				staff.hidePlayer(e.getPlayer());
			}
		}
		
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		if(core.isVanished(e.getPlayer())) {
			core.setStaffMode(e.getPlayer(), false);
		}
	}

}
