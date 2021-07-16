package sa.pg.event.ffa.tasks;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import sa.pg.event.ffa.Main;
import sa.pg.event.ffa.impl.task.FTask;
import sa.pg.event.ffa.impl.task.TaskType;
import sa.pg.event.ffa.utils.LocationUtils;
import sa.pg.event.ffa.utils.board.ScoreboardType;
import sa.pg.event.ffa.utils.board.ScoreboardUtils;

import java.util.concurrent.ExecutionException;

public class EndTask extends FTask {
    public EndTask() {
        super(TaskType.ASYNC);
    }

    private int counter = 0;

    @Override
    public Runnable getRunnable() {
        return () -> {
            counter++;
            Location npc1 = LocationUtils.getLocation(Main.getInstance().getConfig(), "npc.top.1");
            Location npc2 = LocationUtils.getLocation(Main.getInstance().getConfig(), "npc.top.2");
            Location npc3 = LocationUtils.getLocation(Main.getInstance().getConfig(), "npc.top.3");
            if(counter % 5 == 0) {
                MinecraftServer.getServer().postToMainThread(() -> {
                    if(npc1 != null)
                        npc1.getWorld().spawnEntity(npc1, EntityType.FIREWORK);
                    if(npc2 != null)
                        npc2.getWorld().spawnEntity(npc2, EntityType.FIREWORK);
                    if(npc3 != null)
                        npc3.getWorld().spawnEntity(npc3, EntityType.FIREWORK);
                });
            }
            Bukkit.getOnlinePlayers().forEach(all -> ScoreboardUtils.updateBoard(all, ScoreboardType.AFTER_EVENT));
            if(counter == 3) {
                if(npc1 != null) {
                    try {
                        String name = Main.getInstance().getData().getTop(1);
                        MinecraftServer.getServer().postToMainThread(() -> {
                            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);
                            npc.spawn(npc1);
                        });
                    } catch (ExecutionException | InterruptedException e) {
                        if(Main.getInstance().isPrintStacktrace()) {
                            e.printStackTrace();
                        }
                    }
                }
                if(npc2 != null) {
                    try {
                        String name = Main.getInstance().getData().getTop(2);
                        MinecraftServer.getServer().postToMainThread(() -> {
                            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);
                            npc.spawn(npc2);
                        });
                    } catch (ExecutionException | InterruptedException e) {
                        if(Main.getInstance().isPrintStacktrace()) {
                            e.printStackTrace();
                        }
                    }
                }
                if(npc3 != null) {
                    try {
                        String name = Main.getInstance().getData().getTop(3);
                        MinecraftServer.getServer().postToMainThread(() -> {
                            NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);
                            npc.spawn(npc3);
                        });
                    } catch (ExecutionException | InterruptedException e) {
                        if(Main.getInstance().isPrintStacktrace()) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
    }

    @Override
    public long getDelay() {
        return 0L;
    }

    @Override
    public long getDuration() {
        return 20L;
    }
}
