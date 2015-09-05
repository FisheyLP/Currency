package com.FisheyLP.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.OfflinePlayer;

public class User {

	private OfflinePlayer p;
	
	public User(OfflinePlayer p) {
		this.p = p;
	}
	
	public OfflinePlayer getPlayer() {
		return p;
	}
	
	public String getName() {
		return p.getName();
	}
	
	public UUID getUniqueId() {
		return p.getUniqueId();
	}
	
	public long getMoney() {
		ResultSet result = Database.executeQuery(
			"SELECT money FROM currency WHERE uuid = "+getUniqueId().toString());
		try {
			if (result.next())
			return result.getLong("money");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void setMoney(long amount) {
		ResultSet result = Database.executeQuery("SELECT * FROM currency "
				+ "WHERE uuid = '"+getUniqueId().toString()+"'");
		try {
			if (result.next()) Database.executeUpdate("UPDATE currency SET money = "+amount+
					" WHERE uuid = '"+p.getUniqueId().toString()+"'");
			else Database.executeUpdate("INSERT INTO currency VALUES ("+
					"'"+p.getUniqueId().toString()+"', "+
					"'"+getName()+"', "+amount+")");
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