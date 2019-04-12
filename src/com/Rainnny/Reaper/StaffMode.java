package com.Rainnny.Reaper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import com.Rainnny.Reaper.Commands.HideStaffCmd;
import com.Rainnny.Reaper.Commands.StaffModeCmd;
import com.Rainnny.Reaper.Commands.Vanish;
import com.Rainnny.Reaper.Listeners.HideVanishListener;
import com.Rainnny.Reaper.Listeners.StaffModeItems;
import com.Rainnny.Reaper.Listeners.StaffOnlineListener;
import com.Rainnny.Reaper.Utils.StaffOnlineGUI;


public class StaffMode extends JavaPlugin {
	
	public ArrayList<Player> inStaffMode = new ArrayList<Player>();
	public ArrayList<Player> staffOnline = new ArrayList<Player>();
	public ArrayList<Player> inVanish = new ArrayList<Player>();
	public HashMap<Player, ItemStack[]> staffInventories = new HashMap<Player, ItemStack[]>();
	public HashMap<Player, GameMode> staffLastGamemodes = new HashMap<Player, GameMode>();
	public ArrayList<Player> onlinePlayers = new ArrayList<Player>();
	public ArrayList<Player> hiddenStaff = new ArrayList<Player>();
	public ArrayList<Player> toTeleportTo = new ArrayList<Player>();
	public ArrayList<Player> miningPlayers = new ArrayList<Player>();
	public static String PREFIX;
    public static ChatColor M;
    public static ChatColor M2;
    public static ChatColor O;
    
    public static StaffMode instance;
	
	public StaffOnlineGUI staffOnlineGUI;
	
	public ArrayList<Player> getStaffInMode() {
		return inStaffMode;
	}
	
	public ArrayList<Player> getStaffOnline() {
		return staffOnline;
	}
	
	public ArrayList<Player> inVanish() {
		return inVanish;
	}
	
	public HashMap<Player, ItemStack[]> getStaffInvs() {
		return staffInventories;
	}
	
	public void setStaffMode(Player player, boolean inStaffMode) {
		if(inStaffMode == true) {
			this.getStaffInMode().add(player);
			if(!isVanished(player)) {
				this.setVanished(player, true);
				player.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "You were automatically put into vanish since you weren't already.");
			}
			this.giveItems(player);
			staffLastGamemodes.put(player, player.getGameMode());
			player.setGameMode(GameMode.CREATIVE);
		} else {
			this.getStaffInMode().remove(player);
			this.returnOldInv(player);
			this.setVanished(player, false);
			player.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC + "Automatically removed your vanish mode.");
			if(player.isOp() && staffLastGamemodes.get(player) == GameMode.CREATIVE) {
				player.setGameMode(staffLastGamemodes.get(player));
			} else {
				if(staffLastGamemodes.get(player) == GameMode.CREATIVE) {
					player.setGameMode(GameMode.SURVIVAL);
					return;
				}
				player.setGameMode(staffLastGamemodes.get(player));
			}
		}
	}
	
	public boolean isInStaffMode(Player player) {
		return getStaffInMode().contains(player);
	}
	
	private List<String> compassLore() {
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.translateAlternateColorCodes('&', "§4&lRIGHT CLICK &cto teleport through blocks."));
		lore.add(ChatColor.translateAlternateColorCodes('&', "§4&lLEFT CLICK &cto jump through blocks."));
		
		return lore;
	}
	
	private List<String> inspectLore() {
		List<String> lore = new ArrayList<String>();
		
		lore.add(ChatColor.translateAlternateColorCodes('&', "§4&lRIGHT CLICK &cto inspect a player's inventory."));
		
		return lore;
	}
	
	private List<String> worldeditLore() {
		List<String> lore = new ArrayList<String>();
		
		lore.add("§4§lLEFT CLICK §cto select position 1");
		lore.add("§4§lRIGHT CLICK §cto select position 2");
		
		return lore;
	}
	
	private List<String> freezeLore() {
		List<String> lore = new ArrayList<String>();
		
		lore.add(ChatColor.translateAlternateColorCodes('&', "&4&lRIGHT CLICK &ca player to freeze them."));
		
		return lore;
	}
	
	private List<String> randomLore() {
		List<String> lore = new ArrayList<String>();
		
		lore.add(ChatColor.translateAlternateColorCodes('&', "&4&lRIGHT CLICK &cto randomly teleport to a player!"));
		return lore;
	}
	
	public List<String> vanishLore() {
		List<String> lore = new ArrayList<String>();
		
		lore.add(ChatColor.translateAlternateColorCodes('&', "&4&lRIGHT CLICK &cto toggle your vanish."));
		
		return lore;
	}
	
	private List<String> staffOnlineLore() {
		List<String> lore = new ArrayList<String>();
		
		lore.add(ChatColor.translateAlternateColorCodes('&', "&4&lRIGHT CLICK &cto view online staff."));
		
		return lore;
	}
	
	public void giveItems(Player player) {
		ItemStack compass = new ItemStack(Material.COMPASS);
		ItemMeta compassMeta = compass.getItemMeta();
		
		compassMeta.setDisplayName(ChatColor.DARK_RED + "Teleport");
		compassMeta.setLore(compassLore());
		compass.setItemMeta(compassMeta);
		
		ItemStack inspect = new ItemStack(Material.BOOK);
		ItemMeta inspectMeta = compass.getItemMeta();
		
		inspectMeta.setDisplayName(ChatColor.DARK_RED + "Inspect Tool");
		inspectMeta.setLore(inspectLore());
		inspect.setItemMeta(inspectMeta);
		
		ItemStack worldedit = new ItemStack(Material.WOOD_AXE);
		ItemMeta worldeditMeta = worldedit.getItemMeta();
		
		worldeditMeta.setDisplayName("§4WorldEdit Wand");
		worldeditMeta.setLore(worldeditLore());
		worldedit.setItemMeta(worldeditMeta);
		
		
		ItemStack freeze = new ItemStack(Material.ICE);
		ItemMeta freezeMeta = freeze.getItemMeta();
		
		freezeMeta.setDisplayName(ChatColor.DARK_RED + "Freeze Player");
		freezeMeta.setLore(freezeLore());
		freeze.setItemMeta(freezeMeta);
		
		ItemStack randomtp = new ItemStack(Material.WATCH, 1);
		ItemMeta randomtpMeta = randomtp.getItemMeta();
		
		randomtpMeta.setDisplayName(ChatColor.DARK_RED + "Random Teleport");
		randomtpMeta.setLore(randomLore());
		randomtp.setItemMeta(randomtpMeta);
		
		ItemStack vanish = new ItemStack(Material.INK_SACK, 1, (short)0, (byte) 8);
		if(isVanished(player)) {
			vanish = new ItemStack(Material.INK_SACK, 1, (short)0, (byte) 10);
		}
	    ItemMeta vanishMeta = vanish.getItemMeta();
		
		vanishMeta.setDisplayName(ChatColor.BLUE + "Vanish");
		vanishMeta.setLore(vanishLore());
		
		vanish.setItemMeta(vanishMeta);
		
		ItemStack staffOnline = new ItemStack(Material.SKULL_ITEM, 1, (short)0, (byte) 3);
	    ItemMeta staffOnlineMeta = staffOnline.getItemMeta();
		
		staffOnlineMeta.setDisplayName(ChatColor.BLUE + "Staff Online");
		staffOnlineMeta.setLore(staffOnlineLore());
		
		staffOnline.setItemMeta(staffOnlineMeta);
		
		
		staffInventories.put(player, player.getInventory().getContents());
		player.getInventory().clear();
		player.getInventory().setItem(0, compass);
		player.getInventory().setItem(1, inspect);
		player.getInventory().setItem(2, worldedit);
		player.getInventory().setItem(3, freeze);
		player.getInventory().setItem(7, randomtp);
		player.getInventory().setItem(8, vanish);
		
	}
	
	public boolean isStaff(Player player) {
		return player.hasPermission("reaper.staff");
	}
	
	public void returnOldInv(Player player) {
		player.getInventory().clear();
		player.getInventory().setContents(getStaffInvs().get(player));
	}
	
	public void setVanished(Player player, boolean inVanish) {
		if(inVanish == true) {
			for(Player online : Bukkit.getOnlinePlayers()) {
				online.hidePlayer(player);
				
			}
			for(Player staff : this.staffOnline) {
				staff.canSee(player);
			}
			this.inVanish.add(player);
		} else {
			for(Player online : Bukkit.getOnlinePlayers()) {
				online.showPlayer(player);
			}
			this.inVanish.remove(player);
		}
	}
	
	public boolean isVanished(Player player) {
		return inVanish.contains(player);
	}

	public void onEnable() {
		
		//Config
		//File file = new File(getDataFolder(), "config.yml");
		//if(!file.exists()) {
			//getConfig().addDefault("Inventory", "");
			//getConfig().options().copyDefaults(true);
			//saveConfig();
		//}
		
		instance = this;
		
		//Registered Commands
		this.getCommand("staffmode").setExecutor(new StaffModeCmd(this));
		this.getCommand("vanish").setExecutor(new Vanish(this));
        this.getCommand("hidestaff").setExecutor(new HideStaffCmd(this));
		
		//Registering Listeners
		Bukkit.getPluginManager().registerEvents(new StaffModeItems(this), this);
		Bukkit.getPluginManager().registerEvents(new StaffOnlineListener(this), this);
		Bukkit.getPluginManager().registerEvents(new StaffOnlineListener(this), this);
		Bukkit.getPluginManager().registerEvents(new HideVanishListener(this), this);
		
		//Online player updater
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

			public void run() {
				if(Bukkit.getOnlinePlayers().length != 0) {
					for(int all = 0 ; all < onlinePlayers.size() ; all++) {
						onlinePlayers.remove(all);
					}
					for(Player online : Bukkit.getOnlinePlayers()) {
						onlinePlayers.add(online);
					}
					for(Player staff : staffOnline) {
						onlinePlayers.remove(staff);
					}
				}
				staffOnline.clear();
				miningPlayers.clear();
				for(Player online : Bukkit.getOnlinePlayers()) {
					if(online.hasPermission("railed.staff")) {
						staffOnline.add(online);
					}
				}
				
				for(Player online : Bukkit.getOnlinePlayers()) {
					if(online.getLocation().getBlockY() <= 20) {
						miningPlayers.add(online);
					}
				}
				
				if(!inVanish.isEmpty() && !staffOnline.isEmpty()) {
					for(Player staff : staffOnline) {
						for(Player vanished : inVanish) {
							staff.showPlayer(vanished);
						}
						for(Player ops : Bukkit.getOnlinePlayers()) {
							if(isVanished(ops) && ops.isOp() && !staff.isOp()) {
								staff.hidePlayer(ops);
							}
						}
					}
				}
				if(!hiddenStaff.isEmpty() && !staffOnline.isEmpty()) {
					for(Player staff : inStaffMode) {
						for(Player hidden : hiddenStaff) {
							hidden.hidePlayer(staff);
						}
					}
				}
				if(Bukkit.getOnlinePlayers().length != 0) {
					for(int i = 0 ; i < toTeleportTo.size() ; i++) {
						toTeleportTo.remove(i);
					}
					for(Player online : Bukkit.getOnlinePlayers()) {
						toTeleportTo.add(online);
					}
				}
				if(!staffOnline.isEmpty()) {
					for(Player staff : staffOnline) {
						toTeleportTo.remove(staff);
					}
				}
				
			}
		}, 20L, 20L);
		
		//Registering Utils
		staffOnlineGUI = new StaffOnlineGUI(this);
	}
	
	static {
        StaffMode.PREFIX = "§4Railed §8» §7";
        StaffMode.M = ChatColor.DARK_RED;
        StaffMode.M2 = ChatColor.RED;
        StaffMode.O = ChatColor.GRAY;
    }
	
	public void onDisable() {
		for(Player pp : Bukkit.getOnlinePlayers()) {
			if(isInStaffMode(pp)) {
				setStaffMode(pp, false);
			}
		}
	}

}