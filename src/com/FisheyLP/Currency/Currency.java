package com.FisheyLP.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import com.FisheyLP.Currency.commands.CommandMoney;
import com.FisheyLP.Currency.commands.CommandPay;


public class Currency extends JavaPlugin {

    private static Currency instance;
    public static final String LABEL = ChatColor.GRAY + "[" + ChatColor
            .DARK_AQUA + "Currency" + ChatColor.GRAY + "] ";
    public static final String SYMBOL = "gp";

    private List<User> users = new ArrayList<User>();

    private Database database;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        database = new Database(getConfig().getString("MySQL.host"),
                getConfig().getString("MySQL.port"),
                getConfig().getString("MySQL.database"),
                getConfig().getString("MySQL.user"),
                getConfig().getString("MySQL.password"));
        getCommand("money").setExecutor(new CommandMoney());
        getCommand("pay").setExecutor(new CommandPay());
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public Database getDatabaseConnector() {
        return database;
    }

    public User getUser(OfflinePlayer player) {
        for (User user : users) {
            if (user.getPlayer().equals(player)) {
                return user;
            }
        }

        User newUser = new User(player);
        users.add(newUser);
        return newUser;
    }

    @SuppressWarnings("deprecation")
    public User getUser(String name) {
        return getUser(Bukkit.getOfflinePlayer(name));
    }

    public User getUser(UUID uuid) {
        return getUser(Bukkit.getOfflinePlayer(uuid));
    }

    public static Currency getInstance() {
        return instance;
    }
}
