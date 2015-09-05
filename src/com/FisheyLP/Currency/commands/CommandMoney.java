package com.FisheyLP.Currency.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.FisheyLP.Currency.Currency;
import com.FisheyLP.Currency.User;

public class CommandMoney implements CommandExecutor {

	private Currency c = Currency.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias,
			String[] args) {
		if (args.length == 0) {
			User u = c.getUser(sender.getName());
			sender.sendMessage(c.l+"You have §6"+u.getMoney()+" "+c.getSymbol()+"§7.");
			return true;
		} else if (args.length == 1) {
			if (!sender.hasPermission("Currency.money.other")) {
				sender.sendMessage(c.l+"§4You don't have permission to use this command.");
				return true;
			}
			User other = c.getUser(args[0]);
			
			try {
				other = c.getUser(UUID.fromString(args[0]));
			} catch (Exception e) {}
			
				sender.sendMessage(c.l+"§6"+other.getName()+" §7has §6"+other.getMoney()+
						" "+c.getSymbol()+"§7.");
			return true;
		}
		sender.sendMessage(c.l+"Usage: §6/"+alias+" [player]§7.");
		return true;
	}
	
}