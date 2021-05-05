package com.aaron.ps;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	public static Main plugin;

	public void onEnable() {
		plugin = this;
	}

	public static Main getInstance() {
		return plugin;
	}

	public void onDisable() {
		plugin = null;
	}

	public void stackPotions(Player player) {
		short[] potion_types = new short[2866];
		short k = 0;
		short l = 0;
		do {
			potion_types[(l++)] = (k++);
			if (k == 100) {
				k = 8000;
			}
			if (k == 9000) {
				k = 16000;
			}
			if (k == 17000) {
				k = 32000;
			}
		} while (k != 32766);
		int[] potion_amt = new int[potion_types.length];

		ItemStack[] inventory_contents = player.getInventory().getContents();
		for (int i = 0; i < inventory_contents.length; i++) {
			ItemStack item = inventory_contents[i];
			if ((item != null) && (item.getType().equals(Material.POTION))) {
				short potion_type = item.getDurability();
				for (int j = 0; j < potion_types.length; j++) {
					if (potion_type == potion_types[j]) {
						potion_amt[j] += item.getAmount();
						player.getInventory().clear(i);
					}
				}
			}
		}
		for (int j = 0; j < potion_types.length; j++) {
			if (potion_amt[j] > 0) {
				ItemStack potions = new ItemStack(Material.POTION, potion_amt[j], potion_types[j]);
				ItemMeta meta = potions.getItemMeta();
				meta.setDisplayName("");
				potions.setItemMeta(meta);
				player.getInventory().addItem(new ItemStack[] { potions });
			}
		}
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			if (cmd.getName().equalsIgnoreCase("pstack")) {
				Player player = (Player) sender;
				this.stackPotions(player);
				player.sendMessage(ChatColor.GREEN + "(!) You have stacked all your potions in your inventory!");
				return true;
			}
		}
		return false;
	}
}