package sa.pg.event.ffa.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sa.pg.event.ffa.Main;
import sa.pg.event.ffa.impl.plugin.ICommand;

public class BuildCommand extends ICommand {
    @Override
    public String getName() {
        return "build";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        final String PREFIX = "§8\u2503 §9§lP§6§lG §8» ";
        if(sender instanceof Player) {
            if(!sender.hasPermission("pg.event.build")) {
                sender.sendMessage(PREFIX + "§cYou don't have permission to perform this command.");
                return false;
            }
            if(args.length != 0) {
                sender.sendMessage(PREFIX + "§cUsage: /build");
            }
            if(Main.getInstance().getBuild().contains(((Player) sender).getUniqueId())) {
                Main.getInstance().getBuild().remove(((Player) sender).getUniqueId());
                sender.sendMessage(PREFIX + "§3You have successfully §cdisabled §3build mode.");
            } else {
                Main.getInstance().getBuild().add(((Player) sender).getUniqueId());
                sender.sendMessage(PREFIX + "§3You have successfully §eenabled §3build mode.");
            }
            return true;
        }
        return false;
    }
}
