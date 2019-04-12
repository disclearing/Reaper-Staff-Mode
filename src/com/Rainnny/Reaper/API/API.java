package com.Rainnny.Reaper.API;

import org.bukkit.entity.Player;

import com.Rainnny.Reaper.StaffMode;

public class API {
	
	public API() {
		instance = this;
	}
	
	private static API instance;
	
	public boolean isInStaffMode(Player player) {
		
		return new StaffMode().isInStaffMode(player);
	}
	
	
	
	

}
