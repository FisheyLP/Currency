package com.FisheyLP.Currency.commands;

import com.FisheyLP.Currency.Currency;
import com.FisheyLP.Currency.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

public class CommandMoney implements CommandExecutor {

    private Currency parent = Currency.getInstance();


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias,
                             String[] args) {
        if (args.length == 0) {
            User u = parent.getUser(sender.getName());
            sender.sendMessage(Currency.LABEL + "You have " + ChatColor.GRAY + u
                    .getMoney() + " " + Currency.SYMBOL + ChatColor.GRAY + ".");
            return true;
        } else if (args.length == 1) {
            if (!sender.hasPermission("Currency.money.other")) {
                sender.sendMessage(
                        Currency.LABEL + "" + ChatColor.DARK_RED +
                        "You don't have permission to use this command.");
                return true;
            }
            User other = parent.getUser(args[0]);

            try {
                other = parent.getUser(UUID.fromString(args[0]));
            } catch (Exception ignored) {
            }

            sender.sendMessage(Currency.LABEL + ChatColor.GRAY + other.getName() + " " + ChatColor.GRAY + "has "
                    + ChatColor.GRAY + other.getMoney() +
                    " " + Currency.SYMBOL + ChatColor.GRAY + ".");
            return true;
        }
        sender.sendMessage(
                Currency.LABEL + "Usage: " + ChatColor.GRAY + "/" + alias + " [player]" + ChatColor.GRAY + ".");
        return true;
    }

    public abstract static class MoneyCallback implements Runnable {
        private final String result;

        public MoneyCallback(String result) {
            this.result = result;
        }
    }

}