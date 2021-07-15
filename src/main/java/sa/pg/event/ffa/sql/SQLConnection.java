package sa.pg.event.ffa.sql;

import sa.pg.event.ffa.Main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLConnection {

    private Connection connection;
    private String     errorMessage;

    public SQLConnection() {
        this.errorMessage = "N/A";
    }

    public ConnectionResult connect() {
        try {
            if(Main.getInstance().getDataFolder().mkdir()) {
                Main.getInstance().getLogger().log(Level.WARNING, "Plugin data directory doesn't exists, creating it...");
            }
            File dataFile = new File(Main.getInstance().getDataFolder(), "data.db");
            if(dataFile.createNewFile()) {
                Main.getInstance().getLogger().log(Level.WARNING, "data.db file doesn't exists, creating it...");
            }
            Class.forName("org.sqlite.JDBC");
            if(connection != null && !connection.isClosed())
                return ConnectionResult.ALREADY_CONNECTED;
            connection = DriverManager.getConnection("jdbc:sqlite:" + dataFile);
            return ConnectionResult.SUCCESS;
        } catch (SQLException exception) {
            errorMessage = exception.getMessage();
            return ConnectionResult.CONNECTION_ERROR;
        } catch (IOException exception) {
            errorMessage = exception.getMessage();
            return ConnectionResult.FILE_ERROR;
        } catch (ClassNotFoundException exception) {
            errorMessage = exception.getMessage();
            return ConnectionResult.LIBRARY_ERROR;
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public PreparedStatement preparedStatement(String statement) throws SQLException {
        return connection.prepareStatement(statement);
    }

}
