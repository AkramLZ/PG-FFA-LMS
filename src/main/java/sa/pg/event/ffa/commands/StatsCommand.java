package sa.pg.event.ffa.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sa.pg.event.ffa.Main;
import sa.pg.event.ffa.impl.player.data.DataSlot;
import sa.pg.event.ffa.impl.player.data.FData;
import sa.pg.event.ffa.impl.player.manager.PlayerManager;
import sa.pg.event.ffa.impl.plugin.ICommand;

import java.util.concurrent.ExecutionException;

public class StatsCommand extends ICommand {
    @Override
    public String getName() {
        return "stats";
    }

    private final String PREFIX = "§8\u2503 §9§lP§6§lG §8» ";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            if(!(sender instanceof Player)) {
                sender.sendMessage(PREFIX + "§cThis command is only for players!");
                return false;
            }
            Player player = (Player) sender;
            player.sendMessage(PREFIX + "§6Your personal statistics:");
            player.sendMessage(PREFIX + "§eKills: " + PlayerManager.getPlayer(player).getData().getValue(DataSlot.KILLS));
            player.sendMessage(PREFIX + "§eDeaths: " + PlayerManager.getPlayer(player).getData().getValue(DataSlot.DEATHS));
        } else if(args.length == 1) {
            String name = args[0];
            if(Bukkit.getPlayerExact(name) == null) {
                sender.sendMessage(PREFIX + "§cThis player is not online!");
                return false;
            }
            Player target = Bukkit.getPlayerExact(name);
            try {
                if(Main.getInstance().getData().registered(target)) {
                    sender.sendMessage(PREFIX + "§cUnable to load player's data!");
                    return false;
                }
                FData data = PlayerManager.getPlayer(target).getData();
                sender.sendMessage(PREFIX + "§6" + target.getName() + "'s personal statistics:");
                sender.sendMessage(PREFIX + "§eKills: " + data.getValue(DataSlot.KILLS));
                sender.sendMessage(PREFIX + "§eDeaths: " + data.getValue(DataSlot.DEATHS));
            } catch (ExecutionException | InterruptedException e) {
                if(Main.getInstance().isPrintStacktrace()) {
                    e.printStackTrace();
                }
                sender.sendMessage(PREFIX + "§cAn error occurred when performing command, please contact with developers or administrators.");
            }
        } else {
            sender.sendMessage(PREFIX + "§cUsage: /stats <player>");
        }
        return true;
    }
}
