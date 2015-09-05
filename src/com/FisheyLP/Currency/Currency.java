package com.FisheyLP.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import com.FisheyLP.Currency.commands.CommandMoney;
import com.FisheyLP.Currency.commands.CommandPay;

public class Currency extends JavaPlugin {

	private static Currency instance;
	public String l = "§7[§3Currency§7] ";
	private String symbol = "gp";
	private List<User> users = new ArrayList<User>();
	
	@Override
	public void onEnable() {
		instance = this;
		saveDefaultConfig();
		
		new Database(getConfig().getString("MySQL.host"), 
				getConfig().getString("MySQL.port"), getConfig().getString("MySQL.database"),
				getConfig().getString("MySQL.user"), getConfig().getString("MySQL.password"));
		
		getCommand("money").setExecutor(new CommandMoney());
		getCommand("pay").setExecutor(new CommandPay());
	}
	
	@Override
	public void onDisable() {
		instance = null;
	}
	
	public User getUser(OfflinePlayer player) {
		for (User user : users)
			if (user.getPlayer().equals(player))
				return user;
		
		User u = new User(player);
		users.add(u);
		return u;
	}
	
	@SuppressWarnings("deprecation")
	public User getUser(String name) {
		return getUser(Bukkit.getOfflinePlayer(name));
	}
	
	public User getUser(UUID uuid) {
		return getUser(Bukkit.getOfflinePlayer(uuid));
	}
	
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public static Currency getInstance() {
		return instance;
	}
}
