package sa.pg.event.ffa.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import sa.pg.event.ffa.Main;
import sa.pg.event.ffa.impl.plugin.ICommand;
import sa.pg.event.ffa.manager.EventManager;
import sa.pg.event.ffa.manager.types.EventState;
import sa.pg.event.ffa.tasks.EventTask;
import sa.pg.event.ffa.utils.LocationUtils;
import sa.pg.event.ffa.utils.RegionUtils;
import sa.pg.event.ffa.utils.board.ScoreboardType;
import sa.pg.event.ffa.utils.board.ScoreboardUtils;

public class EventCommand extends ICommand {
    @Override
    public String getName() {
        return "event";
    }

    private static Location pos1, pos2;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        String PREFIX = "§8\u2503 §9§lP§6§lG §8» ";
        if(!sender.hasPermission("pg.event.admin")) {
            sender.sendMessage(PREFIX + "§cYou don't have permission to perform this command.");
            return false;
        }
        if(args.length == 0) {
            sender.sendMessage(PREFIX + "§cUnorganized command, try \"/event help\" for help.");
        } else if(args.length == 1) {
            if(args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(PREFIX + "§2/event help §fdisplay this menu");
                sender.sendMessage(PREFIX + "§2/event start §fstart the current event");
                sender.sendMessage(PREFIX + "§2/event end §fforce stop for the event");
                sender.sendMessage(PREFIX + "§2/event setspawn §fset spawn location");
                sender.sendMessage(PREFIX + "§2/event pos1 §fset spawn's region 1st position");
                sender.sendMessage(PREFIX + "§2/event pos2 §fset spawn's region 2nd position");
                sender.sendMessage(PREFIX + "§2/event top1 §fset top 1 npc location");
                sender.sendMessage(PREFIX + "§2/event top2 §fset top 2 npc location");
                sender.sendMessage(PREFIX + "§2/event top3 §fset top 3 npc location");
            } else if(args[0].equalsIgnoreCase("start")) {
                if(EventManager.getEventState() == EventState.IN_GAME) {
                    sender.sendMessage(PREFIX + "§cThis event is already started.");
                }
                if(EventManager.getEventState() == EventState.ENDING) {
                    sender.sendMessage(PREFIX + "§cThis event is already finished.");
                }
                if(EventManager.getEventState() == EventState.LOBBY) {
                    Bukkit.broadcastMessage(PREFIX + "§eFFA-LMS Event started by §6" + sender.getName());
                    EventManager.setEventState(EventState.IN_GAME);
                    Bukkit.getOnlinePlayers().forEach(player -> ScoreboardUtils.assignScoreboard(player, ScoreboardType.EVENT));
                    (EventManager.EVENT_TASK = new EventTask()).start();
                }
            } else if(args[0].equalsIgnoreCase("end")) {
                if(EventManager.getEventState() != EventState.IN_GAME) {
                    sender.sendMessage(PREFIX + "§cYou can't do that now.");
                } else {
                    if(EventManager.getTimeLeft() > 1) {
                        sender.sendMessage(PREFIX + "§cYou can't do that now.");
                    } else {
                        EventManager.setTimeLeft(1);
                        sender.sendMessage(PREFIX + "§eSuccessfully forced event to end.");
                    }
                }
            } else if(args[0].equalsIgnoreCase("pos1")) {
                if(!(sender instanceof Player)) {
                    sender.sendMessage(PREFIX + "§cThis command is only for players!");
                    return false;
                }
                Player player = (Player) sender;
                pos1 = player.getLocation();
                sender.sendMessage(PREFIX + "§eSuccessfully set §6position 1 §eof spawn region.");
            } else if(args[0].equalsIgnoreCase("pos2")) {
                if(!(sender instanceof Player)) {
                    sender.sendMessage(PREFIX + "§cThis command is only for players!");
                    return false;
                }
                if(pos1 == null) {
                    sender.sendMessage(PREFIX + "§cPlease set §4position 1 §cfirst!");
                    return false;
                }
                Player player = (Player) sender;
                pos2 = player.getLocation();
                RegionUtils.setSpawnRegion(Main.getInstance().getConfig(), pos1, pos2);
                sender.sendMessage(PREFIX + "§eSuccessfully set §6position 2 §eof spawn region & created region.");
            } else if(args[0].equalsIgnoreCase("setspawn")) {
                if(!(sender instanceof Player)) {
                    sender.sendMessage(PREFIX + "§cThis command is only for players!");
                    return false;
                }
                Player player = (Player) sender;
                LocationUtils.saveSpawn(Main.getInstance().getConfig(), player.getLocation());
                sender.sendMessage(PREFIX + "§eSuccessfully set spawn in your current location.");
            } else if(args[0].equalsIgnoreCase("top1")) {
                if(!(sender instanceof Player)) {
                    sender.sendMessage(PREFIX + "§cThis command is only for players!");
                    return false;
                }
                Player player = (Player) sender;
                LocationUtils.saveLocation(Main.getInstance().getConfig(), "npc.top.1", player.getLocation());
                sender.sendMessage(PREFIX + "§eSuccessfully saved top 1 npc location in your current location.");
            } else if(args[0].equalsIgnoreCase("top2")) {
                if(!(sender instanceof Player)) {
                    sender.sendMessage(PREFIX + "§cThis command is only for players!");
                    return false;
                }
                Player player = (Player) sender;
                LocationUtils.saveLocation(Main.getInstance().getConfig(), "npc.top.2", player.getLocation());
                sender.sendMessage(PREFIX + "§eSuccessfully saved top 2 npc location in your current location.");
            } else if(args[0].equalsIgnoreCase("top3")) {
                if(!(sender instanceof Player)) {
                    sender.sendMessage(PREFIX + "§cThis command is only for players!");
                    return false;
                }
                Player player = (Player) sender;
                LocationUtils.saveLocation(Main.getInstance().getConfig(), "npc.top.3", player.getLocation());
                sender.sendMessage(PREFIX + "§eSuccessfully saved top 3 npc location in your current location.");
            } else {
                sender.sendMessage(PREFIX + "§cUnorganized command, try \"/event help\" for help.");
            }
        } else {
            sender.sendMessage(PREFIX + "§cUnorganized command, try \"/event help\" for help.");
        }
        return true;
    }
}
