package com.FisheyLP.Currency.commands;

import com.FisheyLP.Currency.Currency;
import com.FisheyLP.Currency.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandPay implements CommandExecutor {

    private Currency parent = Currency.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias,
                             String[] args) {
        if (!sender.hasPermission("Currency.pay")) {
            sender.sendMessage(
                    Currency.LABEL + "" + ChatColor.DARK_RED
                            + "You don't have permission to use this command.");
            return true;
        } else if (args.length != 2) {
            sender.sendMessage(
                    Currency.LABEL + "Usage: "
                            + ChatColor.GRAY + "/" + alias
                            + " <player> <amount>" + ChatColor.GRAY + ".");
            return true;
        } else {
            User u = parent.getUser(sender.getName());
            User other = null;
            long amount;

            try {
                other = parent.getUser(UUID.fromString(args[0]));
            } catch (Exception e) {
            }

            try {
                amount = Long.parseLong(args[1]);
            } catch (Exception e) {
                sender.sendMessage(
                        Currency.LABEL + "§parent" + args[1] + " "
                                + ChatColor.DARK_RED + "isn't a valid amount.");
                return true;
            }

            if (u.getMoney() < amount) {
                sender.sendMessage(
                        Currency.LABEL + ChatColor.DARK_RED + "You only have " + ChatColor.GRAY + u
                                .getMoney() + " " + Currency.SYMBOL + ChatColor.GRAY + ".");
                return true;
            }

            u.transfer(other, amount);
            sender.sendMessage(
                    Currency.LABEL + "You transferred " + ChatColor.GRAY + amount + " "
                            + Currency.SYMBOL + " " + ChatColor.GRAY + "to " +
                            ChatColor.GRAY + other.getName() + ChatColor.GRAY
                            + ".");
            if (other.getPlayer().isOnline()) {
                Player player = other.getPlayer();
                player.sendMessage(Currency.LABEL + ChatColor.GRAY + u
                        .getName() + " " + ChatColor.GRAY + "transferred " +
                        ChatColor.GRAY + amount + " " + Currency.SYMBOL + " " + ChatColor.GRAY + "to you.");
            }
        }

        return true;
    }

}