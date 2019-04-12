package com.Rainnny.Reaper.Utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import com.Rainnny.Reaper.StaffMode;

public class StaffOnlineGUI implements Listener {

	private Inventory rm;
	private ItemStack skullHead;
	
	private StaffMode core;

	public StaffOnlineGUI(StaffMode p) {
		this.core = p;
	}
	
	public void create_inventory() {
		rm = Bukkit.createInventory(null, 18, "Staff Online");
		if (core.staffOnline == null) {
			return;
		}
		for(Player staff : core.staffOnline) {
			for(int i = 0; i < core.staffOnline.size() ; i++) {
				skullHead = addStaff(staff.getName());
				rm.setItem(i, skullHead);
			}
		}
	}
	
	private ItemStack addStaff(String name) {
		ItemStack i = new ItemStack(Material.SKULL, 1, (short) 0, (byte) 3);
		SkullMeta im = (SkullMeta) i.getItemMeta();
		
		im.setDisplayName(ChatColor.RED + name);
		im.setOwner(Bukkit.getPlayer(name).getName());
		i.setItemMeta(im);
		
		return i;
	}
	
	public void open(Player player) {
		create_inventory();
		player.openInventory(rm);
	}
	
	 @EventHandler
     public void onInventoryClick(InventoryClickEvent e) {
             if (!e.getInventory().getName().equalsIgnoreCase(rm.getName())) return;
             if (e.getCurrentItem().getItemMeta() == null) return;
             e.setCancelled(true);
     }

}