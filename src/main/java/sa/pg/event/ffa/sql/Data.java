package sa.pg.event.ffa.sql;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import sa.pg.event.ffa.Main;
import sa.pg.event.ffa.impl.player.data.DataSlot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class Data {

    private final SQLConnection     connection;
    private final ExecutorService   pool;

    public Data(SQLConnection connection) {
        this.connection = connection;
        this.pool       = Executors.newCachedThreadPool();
    }

    public void createTable() {
        pool.execute(() -> {
            String taskId = generateId("CREATE");
            try {
                PreparedStatement statement = connection.preparedStatement(
                        "CREATE TABLE IF NOT EXISTS player_data (Playername VARCHAR(100), UUID VARCHAR(100), Kills INT(100), Deaths INT(100))"
                );
                statement.executeUpdate();
                statement.close();
                Main.getInstance().getLogger().log(Level.INFO, "[SQL] Successfully executed and closed task " + taskId);
            } catch (SQLException exception) {
                if(Main.getInstance().isPrintStacktrace()) {
                    exception.printStackTrace();
                }
                Main.getInstance().getLogger().log(Level.SEVERE, "[SQL] Failed to execute task " + taskId + ", " + exception.getMessage());
            }
        });
    }

    public void register(Player player) {
        pool.execute(() -> {
            String taskId = generateId("SET");
            try {
                PreparedStatement statement = connection.preparedStatement(
                        "INSERT INTO player_data (Playername, UUID, Kills, Deaths) VALUES (?, ?, ?, ?)"
                );
                statement.setString(1, player.getName());
                statement.setString(2, player.getUniqueId().toString());
                statement.setInt(3, 0);
                statement.setInt(4, 0);
                statement.executeUpdate();
                statement.close();
                Main.getInstance().getLogger().log(Level.INFO, "[SQL] Successfully executed and closed task " + taskId);
            } catch (SQLException exception) {
                if(Main.getInstance().isPrintStacktrace()) {
                    exception.printStackTrace();
                }
                Main.getInstance().getLogger().log(Level.SEVERE, "[SQL] Failed to execute task " + taskId + ", " + exception.getMessage());
            }
        });
    }

    public String getTop(int top) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            String name = "None";
            String taskId = generateId("GET");
            try {
                PreparedStatement statement = connection.preparedStatement(
                        "SELECT * FROM player_data ORDER BY Kills DESC LIMIT " + top
                );
                ResultSet resultSet = statement.executeQuery();
                while(resultSet.next()) {
                    name = Bukkit.getOfflinePlayer(UUID.fromString(resultSet.getString("UUID"))).getName();
                }
                resultSet.close();
                statement.close();
                Main.getInstance().getLogger().log(Level.INFO, "[SQL] Successfully executed and closed task " + taskId);
            } catch (SQLException exception) {
                if(Main.getInstance().isPrintStacktrace()) {
                    exception.printStackTrace();
                }
                Main.getInstance().getLogger().log(Level.SEVERE, "[SQL] Failed to execute task " + taskId + ", " + exception.getMessage());
            }
            return name;
        }).get();
    }

    public boolean registered(Player player) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            boolean registered = false;
            String taskId = generateId("CHECK");
            try {
                PreparedStatement statement = connection.preparedStatement(
                        "SELECT * FROM player_data WHERE UUID='" + player.getUniqueId() + "'"
                );
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    resultSet.close();
                    statement.close();
                    registered = true;
                    Main.getInstance().getLogger().log(Level.INFO, "[SQL] Successfully executed and closed task " + taskId);
                }
            } catch (SQLException exception) {
                if(Main.getInstance().isPrintStacktrace()) {
                    exception.printStackTrace();
                }
                Main.getInstance().getLogger().log(Level.SEVERE, "[SQL] Failed to execute task " + taskId + ", " + exception.getMessage());
            }
            return registered;
        }).get();
    }

    public void setValue(Player player, DataSlot dataSlot, int value) {
        pool.execute(() -> {
            String taskId = generateId("SET");
            try {
                PreparedStatement statement = connection.preparedStatement(
                        "UPDATE player_data SET " + dataSlot.getName() + "=" + value + " WHERE UUID='" + player.getUniqueId() + "'"
                );
                statement.executeUpdate();
                statement.close();
                Main.getInstance().getLogger().log(Level.INFO, "[SQL] Successfully executed and closed task " + taskId);
            } catch (SQLException exception) {
                if(Main.getInstance().isPrintStacktrace()) {
                    exception.printStackTrace();
                }
                Main.getInstance().getLogger().log(Level.SEVERE, "[SQL] Failed to execute task " + taskId + ", " + exception.getMessage());
            }
        });
    }

    public int getValue(Player player, DataSlot dataSlot) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            int value = -1;
            String taskId = generateId("GET");
            try {
                PreparedStatement statement = connection.preparedStatement(
                        "SELECT * FROM player_data WHERE UUID='" + player.getUniqueId() + "'"
                );
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()) {
                    value = resultSet.getInt(dataSlot.getName());
                }
                resultSet.close();
                statement.close();
                Main.getInstance().getLogger().log(Level.INFO, "[SQL] Successfully executed and closed task " + taskId);
            } catch (SQLException exception) {
                if(Main.getInstance().isPrintStacktrace()) {
                    exception.printStackTrace();
                }
                Main.getInstance().getLogger().log(Level.SEVERE, "[SQL] Failed to execute task " + taskId + ", " + exception.getMessage());
            }
            return value;
        }).get();
    }

    public int getValue(String name, DataSlot dataSlot) throws ExecutionException, InterruptedException {
        return CompletableFuture.supplyAsync(() -> {
            int value = -1;
            String taskId = generateId("GET");
            try {
                PreparedStatement statement = connection.preparedStatement(
                        "SELECT * FROM player_data WHERE Playername='" + name + "'"
                );
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()) {
                    value = resultSet.getInt(dataSlot.getName());
                }
                resultSet.close();
                statement.close();
                Main.getInstance().getLogger().log(Level.INFO, "[SQL] Successfully executed and closed task " + taskId);
            } catch (SQLException exception) {
                if(Main.getInstance().isPrintStacktrace()) {
                    exception.printStackTrace();
                }
                Main.getInstance().getLogger().log(Level.SEVERE, "[SQL] Failed to execute task " + taskId + ", " + exception.getMessage());
            }
            return value;
        }).get();
    }

    protected String generateId(String prefix) {
        return prefix + "-" + (new Random().nextInt(899) + 100);
    }

}
