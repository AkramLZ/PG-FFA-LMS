package sa.pg.event.ffa.sql;

import sa.pg.event.ffa.Main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;

public class SQLConnection {

    private Connection        connection;
    private String            errorMessage;
    private final String      connectionId;
    private long              elapsedTime;
    private ConnectionResult  connectionResult;

    public SQLConnection() {
        this.errorMessage = "N/A";
        this.connectionId = generateID();
    }

    public ConnectionResult connect() {
        long startTime = System.currentTimeMillis();
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

            this.connection       = DriverManager.getConnection("jdbc:sqlite:" + dataFile);
            this.elapsedTime      = System.currentTimeMillis() - startTime;
            this.connectionResult = ConnectionResult.SUCCESS;

        } catch (SQLException exception) {
            if(Main.getInstance().isPrintStacktrace()) {
                exception.printStackTrace();
            }
            this.errorMessage     = exception.getMessage();
            this.connectionResult = ConnectionResult.CONNECTION_ERROR;

        } catch (IOException exception) {
            if(Main.getInstance().isPrintStacktrace()) {
                exception.printStackTrace();
            }

            this.errorMessage     = exception.getMessage();
            this.connectionResult = ConnectionResult.FILE_ERROR;

        } catch (ClassNotFoundException exception) {
            if(Main.getInstance().isPrintStacktrace()) {
                exception.printStackTrace();
            }
            errorMessage          = exception.getMessage();
            this.connectionResult = ConnectionResult.LIBRARY_ERROR;
        }
        return connectionResult;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public PreparedStatement preparedStatement(String statement) throws SQLException {
        return connection.prepareStatement(statement);
    }

    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException exception) {
            if(Main.getInstance().isPrintStacktrace())
                exception.printStackTrace();
            return false;
        }
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public boolean close() throws SQLException {
        if(!isConnected())
            return false;
        connection.close();
        return true;
    }

    protected String generateID() {
        return "SQL-" + (new Random().nextInt(899) + 100);
    }

    public String getConnectionID() {
        return connectionId;
    }

}
