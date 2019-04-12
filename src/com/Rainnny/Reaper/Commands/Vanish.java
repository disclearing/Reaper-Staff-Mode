package com.Rainnny.Reaper.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permissible;

import com.Rainnny.Reaper.StaffMode;
import com.Rainnny.Reaper.Utils.ColorUtil;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Vanish implements CommandExecutor {

	private StaffMode core;
	public Vanish(StaffMode core) {
		this.core = core;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player) || !sender.hasPermission("railed.staff") || !sender.hasPermission("essentials.vanish")) {
			sender.sendMessage(ChatColor.RED + "You don't have permission to use this.");
			return true;
		}
		Player p = (Player) sender;
		if(args.length == 0) {
			if(core.isInStaffMode(p)) {
				if(core.isVanished(p)) {
					ItemStack vanish = new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 8);
					ItemMeta vanishMeta = vanish.getItemMeta();
					vanishMeta.setDisplayName(ChatColor.DARK_RED + "Vanish");
					vanishMeta.setLore(core.vanishLore());
					vanish.setItemMeta(vanishMeta);
					
					p.getInventory().setItem(8, vanish);
					p.updateInventory();
					core.setVanished(p, false);
					p.sendMessage(ColorUtil.color(StaffMode.PREFIX + "Your vanish has been &cdisabled" + StaffMode.O + "!"));
				} else {
					ItemStack vanish = new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 10);
					ItemMeta vanishMeta = vanish.getItemMeta();
					vanishMeta.setDisplayName(ChatColor.DARK_RED + "Vanish");
					vanishMeta.setLore(core.vanishLore());
					vanish.setItemMeta(vanishMeta);
					
					p.getInventory().setItem(8, vanish);
					p.updateInventory();
					core.setVanished(p, true);
					p.sendMessage(ColorUtil.color(StaffMode.PREFIX + "Your vanish has been &aenabled" + StaffMode.O + "!"));
				}
			} else {
				if(core.isVanished(p)) {
					core.setVanished(p, false);
					p.sendMessage(ColorUtil.color(StaffMode.PREFIX + "Your vanish has been &cdisabled" + StaffMode.O + "!"));
				} else {
					core.setVanished(p, true);
					p.sendMessage(ColorUtil.color(StaffMode.PREFIX + "Your vanish has been &aenabled" + StaffMode.O + "!"));
				}
			}
		} else {
			if(args.length == 1) {
				Player argsPlayer = Bukkit.getPlayer(args[0]);
				if(core.isInStaffMode(argsPlayer)) {
					if(core.isVanished(p)) {
						ItemStack vanish = new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 8);
						ItemMeta vanishMeta = vanish.getItemMeta();
						vanishMeta.setDisplayName(ChatColor.DARK_RED + "Vanish");
						vanishMeta.setLore(core.vanishLore());
						vanish.setItemMeta(vanishMeta);
						
						p.getInventory().setItem(8, vanish);
						p.updateInventory();
						core.setVanished(argsPlayer, false);
						p.sendMessage(ColorUtil.color(StaffMode.PREFIX + "Set " + PermissionsEx.getUser(argsPlayer.getName()).getSuffix() + argsPlayer.getName() + "'s vanish to &cfalse"));
						argsPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', "&9&oYou have been put into vanish by " + p.getName() + "."));
					} else {
						ItemStack vanish = new ItemStack(Material.INK_SACK, 1, (short) 0, (byte) 10);
						ItemMeta vanishMeta = vanish.getItemMeta();
						vanishMeta.setDisplayName(ChatColor.DARK_RED + "Vanish");
						vanishMeta.setLore(core.vanishLore());
						vanish.setItemMeta(vanishMeta);
						
						p.getInventory().setItem(8, vanish);
						p.updateInventory();
						core.setVanished(argsPlayer, true);
						p.sendMessage(ColorUtil.color(StaffMode.PREFIX + "Set " + PermissionsEx.getUser(argsPlayer.getName()).getSuffix() + argsPlayer.getName() + "'s vanish to &atrue"));
						argsPlayer.sendMessage(ColorUtil.color(StaffMode.PREFIX + "Your vanish has been set to &atrue" + StaffMode.O + " by " + PermissionsEx.getUser(argsPlayer.getName()).getSuffix() + argsPlayer.getName()));
					}
				} else {
					if(core.isVanished(p)) {
						core.setVanished(argsPlayer, false);
						p.sendMessage(ColorUtil.color(StaffMode.PREFIX + "Set " + PermissionsEx.getUser(argsPlayer.getName()).getSuffix() + argsPlayer.getName() + "'s vanish to &cfalse"));
						argsPlayer.sendMessage(ColorUtil.color(StaffMode.PREFIX + "Your vanish has been set to &cfalse" + StaffMode.O + " by " + PermissionsEx.getUser(argsPlayer.getName()).getSuffix() + argsPlayer.getName()));
					} else {
						core.setVanished(argsPlayer, true);
						p.sendMessage(ColorUtil.color(StaffMode.PREFIX + "Set " + PermissionsEx.getUser(argsPlayer.getName()).getSuffix() + argsPlayer.getName() + "'s vanish to &atrue"));
						argsPlayer.sendMessage(ColorUtil.color(StaffMode.PREFIX + "Your vanish has been set to &atrue" + StaffMode.O + " by " + PermissionsEx.getUser(argsPlayer.getName()).getSuffix() + argsPlayer.getName()));
					}
				}
				return true;
			}
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cError: &7Too many arguments! &4&oUse /vanish <player>"));
		}
		return true;
	}

}
