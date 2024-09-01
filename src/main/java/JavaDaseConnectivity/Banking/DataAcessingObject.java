package JavaDaseConnectivity.Banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
  * Class holds all the connection objects.
 * Which is connected to the database.
 * This Class Ensures the Objects and resources are closed properly.
 */
public class DataAcessingObject {

    // Connection Object Responsible for transferring the data between java application and JDBC.
    private static final Connection connection;

    // Statement Object is used to store the Statement object and execute it.
    private static final Statement statement;

    // Username for the dataBase.
    private static final String userName;

    // URL to connect the database.
    private static final String url;

    // Password for the database
    private static final String password;

    //  Driver loader location
    private static final String driverLoader;




    // Making class as a private to avoid instantiating it.
    private DataAcessingObject() {
        // Avoiding Object Creation.
    }

    // Initializing the static variables during class has been loaded.

    static {
        userName = "root";
        password = "KavinDharani@3";
        url = "jdbc:mysql://localhost:3306/accounts";
        driverLoader = "com.mysql.cj.jdbc.Driver";
    }

    // Creating objects for static loader
    static  {
        try {

            // Loading the Driver
            Class.forName(driverLoader);
            // Creating a connection from database.
            connection = DriverManager.getConnection(url, userName, password);
            // Creating a statement object to store the queries and to execute it.
            statement = connection.createStatement();
        }
        catch (SQLException e) {
            throw new RuntimeException("Connection has failed due to technical issue ");
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to Load the Driver");
        }
    }

    // @return Returning the database connectivity to the caller.
    public static Connection getConnection() {
        return connection;
    }

    // @return Returning the statement object
    public static Statement getStatement() {
        return statement;
    }

    // Creating the table in the database account table
    static  {
        String accountQuery = "CREATE TABLE IF NOT EXISTS account(" +
                "account_id INT PRIMARY KEY," +
                "account_holder VARCHAR(255)," +
                "balance DECIMAL(65, 2))";
        try {
            statement.execute(accountQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Creating a table for managing all the transaction.
    static {
        String transactionQuery = "CREATE TABLE IF NOT EXISTS transaction_log(" +
                "transaction_id INT AUTO_INCREMENT  PRIMARY KEY," +
                "from_account INT ," +
                "to_account INT ," +
                "transaction_time DATE ," +
                "status VARCHAR(5))";

        try {
            statement.execute(transactionQuery);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}