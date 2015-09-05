package com.FisheyLP.Currency.commands;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.FisheyLP.Currency.Currency;
import com.FisheyLP.Currency.User;

public class CommandPay implements CommandExecutor {

private Currency c = Currency.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias,
			String[] args) {
		if (!sender.hasPermission("Currency.pay")) {
			sender.sendMessage(c.l+"§4You don't have permission to use this command.");
			return true;
		} else if (args.length != 2) {
			sender.sendMessage(c.l+"Usage: §6/"+alias+" <player> <amount>§7.");
			return true;
		} else {
			User u = c.getUser(sender.getName());
			User other = null;
			long amount;
			
			try {
				other = c.getUser(UUID.fromString(args[0]));
			} catch (Exception e) {}
			
			try {
				amount = Long.parseLong(args[1]);
			} catch (Exception e) {
				sender.sendMessage(c.l+"§c"+args[1]+" §4isn't a valid amount.");
				return true;
			}
			
			if (u.getMoney() < amount) {
				sender.sendMessage(c.l+"§4You only have §6"+u.getMoney()+" "
			+c.getSymbol()+"§7.");
				return true;
			}
			
			u.transfer(other, amount);
			sender.sendMessage(c.l+"You transfered §6"+amount+" "+c.getSymbol()+" §7to §6"
			+other.getName()+"§7.");
			if (other.getPlayer().isOnline()) {
				Player p = (Player) other.getPlayer();
				p.sendMessage(c.l+"§6"+u.getName()+" §7trasnfered §6"+amount+" "
				+c.getSymbol()
				+" §7to you.");
			}
		}
		
		return true;
	}
	
}