package sa.pg.event.ffa.utils.board;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import sa.pg.event.ffa.impl.player.data.DataSlot;
import sa.pg.event.ffa.impl.player.manager.PlayerManager;
import sa.pg.event.ffa.manager.EventManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ScoreboardUtils {

    private static Map<Player, ScoreboardObject> scoreboards;

    static {
        ScoreboardUtils.scoreboards = new HashMap<>();
    }

    @SuppressWarnings("ALL")
    public static ScoreboardObject assignScoreboard(Player player, ScoreboardType type) {
        if(type == null) {
            throw new NullPointerException("ScoreboardType cannot be null");
        }
        if(scoreboards.containsKey(player)) {
            scoreboards.get(player).delete();
            scoreboards.remove(player);
        }
        ScoreboardObject scoreboardObject = new ScoreboardObject(player);
        scoreboards.put(player, scoreboardObject);
        if(type == ScoreboardType.LOBBY) {
            scoreboardObject.updateTitle("§c§lEvent §7§l\u2503 §7Waiting");
            scoreboardObject.updateLines(
                    "",
                    "§c§lInformation",
                    "§fCurrent Event: §bFFA",
                    "§fWaiting players: §e" + Bukkit.getOnlinePlayers().size(),
                    "",
                    "§6mc.pg.sa"
            );
        }
        if(type == ScoreboardType.EVENT) {
            scoreboardObject.updateTitle("§c§lEvent §7§l\u2503 §7FFA");
            Date date = new Date();
            date.setMinutes(0);
            date.setSeconds(EventManager.getTimeLeft());
            scoreboardObject.updateLines(
                    "",
                    "§c§lInformation",
                    "§fTime left: §e" + new SimpleDateFormat("mm:ss").format(date),
                    "§fSurvivors: §e" + Bukkit.getOnlinePlayers().size(),
                    "",
                    "§b§lPersonal stats",
                    "§fKills: §e" + PlayerManager.getPlayer(player).getData().getValue(DataSlot.KILLS),
                    "§fDeaths: §e" + PlayerManager.getPlayer(player).getData().getValue(DataSlot.DEATHS),
                    "",
                    "§6mc.pg.sa"
            );
        }
        if(type == ScoreboardType.AFTER_EVENT) {
            scoreboardObject.updateTitle("§c§lEvent §7§l\u2503 §7FFA");
            scoreboardObject.updateLines(
                    "",
                    "§c§lInformation",
                    "§7Event finished, thank you",
                    "§7for playing with us <3",
                    "",
                    "§6mc.pg.sa"
            );
        }
        return scoreboardObject;
    }

    public static void rejectScoreboard(Player player) {
        if(!scoreboards.containsKey(player))
            throw new RuntimeException("Cannot reject scoreboard");
        ScoreboardObject scoreboardObject = scoreboards.get(player);
        if(scoreboardObject.isDeleted())
            throw new RuntimeException("Cannot reject scoreboard");
        scoreboardObject.delete();
        scoreboards.remove(player);
    }

    public static void updateBoard(Player player, ScoreboardType scoreboardType) {
        if(!scoreboards.containsKey(player))
            throw new RuntimeException("Cannot update scoreboard");
        ScoreboardObject scoreboardObject = scoreboards.get(player);
        if(scoreboardType == ScoreboardType.LOBBY) {
            scoreboardObject.updateTitle("§c§lEvent §7§l\u2503 §7Waiting");
            scoreboardObject.updateLines(
                    "",
                    "§c§lInformation",
                    "§fCurrent Event: §bFFA LMS",
                    "§fWaiting players: §e" + Bukkit.getOnlinePlayers().size(),
                    "",
                    "§6mc.pg.sa"
            );
        }
        if(scoreboardType == ScoreboardType.EVENT) {
            scoreboardObject.updateTitle("§c§lEvent §7§l\u2503 §7FFA");
            Date date = new Date();
            date.setMinutes(0);
            date.setSeconds(EventManager.getTimeLeft());
            scoreboardObject.updateLines(
                    "",
                    "§c§lInformation",
                    "§fTime left: §e" + new SimpleDateFormat("mm:ss").format(date),
                    "§fSurvivors: §e" + Bukkit.getOnlinePlayers().size(),
                    "",
                    "§b§lPersonal stats",
                    "§fKills: §e" + PlayerManager.getPlayer(player).getData().getValue(DataSlot.KILLS),
                    "§fDeaths: §e" + PlayerManager.getPlayer(player).getData().getValue(DataSlot.DEATHS),
                    "",
                    "§6mc.pg.sa"
            );
        }
        if(scoreboardType == ScoreboardType.AFTER_EVENT) {
            scoreboardObject.updateTitle("§c§lEvent §7§l\u2503 §7FFA");
            scoreboardObject.updateLines(
                    "",
                    "§c§lInformation",
                    "§7Event finished, thank you",
                    "§7for playing with us <3",
                    "",
                    "§6mc.pg.sa"
            );
        }
    }

}
