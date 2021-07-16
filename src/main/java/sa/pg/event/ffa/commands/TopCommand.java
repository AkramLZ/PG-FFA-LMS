package sa.pg.event.ffa.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import sa.pg.event.ffa.Main;
import sa.pg.event.ffa.impl.player.data.DataSlot;
import sa.pg.event.ffa.impl.player.manager.PlayerManager;
import sa.pg.event.ffa.impl.plugin.ICommand;
import sa.pg.event.ffa.manager.EventManager;
import sa.pg.event.ffa.manager.types.EventState;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TopCommand extends ICommand {
    @Override
    public String getName() {
        return "top";
    }

    private final String PREFIX = "§8\u2503 §9§lP§6§lG §8» ";
    private final ExecutorService pool = Executors.newCachedThreadPool();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(PREFIX + "§cUsage: /top");
            return false;
        }
        pool.execute(() -> {
            Bukkit.getOnlinePlayers().forEach(all -> {
                for(DataSlot dataSlot : DataSlot.values()) {
                    Main.getInstance().getData().setValue(all, dataSlot, PlayerManager.getPlayer(all).getData().getValue(dataSlot));
                }
            });
            try {
                if(EventManager.getEventState() == EventState.LOBBY) {
                    sender.sendMessage(PREFIX + "§cYou can't do that now!");
                    return;
                }
                sender.sendMessage(PREFIX + "§6Top §e#5 §6killers in this event:");
                sender.sendMessage(PREFIX + "§e#1: §6" + Main.getInstance().getData().getTop(1) +
                        " §ewith §6" + Main.getInstance().getData().getValue(Main.getInstance().getData().getTop(1), DataSlot.KILLS) + " §ekills");
                sender.sendMessage(PREFIX + "§e#2: §6" + Main.getInstance().getData().getTop(2) +
                        " §ewith §6" + Main.getInstance().getData().getValue(Main.getInstance().getData().getTop(2), DataSlot.KILLS) + " §ekills");
                sender.sendMessage(PREFIX + "§e#3: §6" + Main.getInstance().getData().getTop(3) +
                        " §ewith §6" + Main.getInstance().getData().getValue(Main.getInstance().getData().getTop(3), DataSlot.KILLS) + " §ekills");
                sender.sendMessage(PREFIX + "§e#4: §6" + Main.getInstance().getData().getTop(4) +
                        " §ewith §6" + Main.getInstance().getData().getValue(Main.getInstance().getData().getTop(4), DataSlot.KILLS) + " §ekills");
                sender.sendMessage(PREFIX + "§e#5: §6" + Main.getInstance().getData().getTop(5) +
                        " §ewith §6" + Main.getInstance().getData().getValue(Main.getInstance().getData().getTop(5), DataSlot.KILLS) + " §ekills");
            } catch (ExecutionException | InterruptedException e) {
                if(Main.getInstance().isPrintStacktrace()) {
                    e.printStackTrace();
                }
                sender.sendMessage(PREFIX + "§cAn error occurred when performing command, please contact with developers or administrators.");
            }
        });
        return true;
    }
}
