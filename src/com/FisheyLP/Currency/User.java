package com.FisheyLP.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class User {

    private OfflinePlayer offlinePlayer;

    private UUID id;

    public User(OfflinePlayer p) {
        this.offlinePlayer = p;
        this.id = p.getUniqueId();
    }

    public String getName() {
        return getPlayer().getName();
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(id);
    }

    public UUID getUniqueId() {
        return id;
    }

    public long getMoney() {
        ResultSet result = Currency.getInstance().getDatabaseConnector().executeQuery(
                "SELECT money FROM currency WHERE uuid = " + getUniqueId()
                        .toString());
        try {
            if (result.next()) {
                return result.getLong("money");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setMoney(long amount) {
        ResultSet result = Currency.getInstance().getDatabaseConnector().executeQuery(
                "SELECT * " +
                        "FROM currency " + "WHERE uuid = '" + getUniqueId()
                        .toString() + "'");
        try {
            if (result.next()) {
                Currency.getInstance().getDatabaseConnector()
                        .executeUpdate("UPDATE currency SET" +
                                " money = " +
                                amount +
                                " WHERE uuid = '" + offlinePlayer.getUniqueId()
                                .toString() + "'");
            } else {
                Currency.getInstance().getDatabaseConnector().executeUpdate("INSERT INTO " +
                        "currency VALUES (" +
                        "'" + offlinePlayer.getUniqueId().toString() + "', " +
                        "'" + getName() + "', " + amount + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMoney(long amount) {
        setMoney(getMoney() + amount);
    }

    public void takeMoney(long amount) {
        setMoney(getMoney() - amount);
    }

    public void transfer(User other, long amount) {
        other.addMoney(amount);
        takeMoney(amount);
    }
}