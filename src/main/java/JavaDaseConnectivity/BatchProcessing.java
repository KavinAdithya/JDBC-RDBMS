package JavaDaseConnectivity;

import jdk.jshell.spi.ExecutionControl;

import java.awt.datatransfer.StringSelection;
import java.awt.dnd.DragGestureEvent;
import java.sql.*;
import java.util.Arrays;


public class BatchProcessing {
    private static Connection connection;

    private static final String userName = "root";
    private static final String password = "KavinDharani@3";

    private final static String url = "jdbc:mysql://localhost:3306/employee";

    private static final String loader = "com.mysql.cj.jdbc.Driver";

    private static Statement statement ;

    public static void initializeConnection() {
        try {
            Class.forName(loader);

            connection = DriverManager.getConnection(url, userName, password);


            statement = connection.createStatement();



        }
        catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void  batchProcessing() throws SQLException {
        initializeConnection();
//        statement.addBatch("INSERT INTO department VALUES('COMPU', 27)");
//        statement.addBatch("DELETE FROM department where depart_ID = 1");

//        statement.addBatch("SELECT * FROM department");
//        statement.addBatch("SELECT * FROM department WHERE depart_ID = 27");

//        int[] rowsAffected = statement.executeBatch();
//        ResultSet resultSet = statement.
        //int[] rowsAffected1 = statement.executeBatch();

        //System.out.println(Arrays.toString(rowsAffected1));
//        statement.executeBatch();
//
//        boolean asResult = statement.getMoreResults();
//
//        while (asResult) {
//            ResultSet resultSet = statement.getResultSet();
//
//            System.out.println("ooeopoe");
//
//            while (resultSet.next()) {
//                System.out.println(resultSet.getInt(2) + "ddf " + resultSet.getString(1));
//            }
//
//            asResult = statement.getMoreResults();
//        }

//        for (int rowAffected : rowsAffected)
//            System.out.println(rowAffected);


        Statement statement1 = connection.createStatement();

        ResultSet resultSet = null;
        ResultSet resultSet1 = null;
        try {
            connection.setAutoCommit(false);
            resultSet1 = statement1.executeQuery("SELECT * FROM department");
            //resultSet = statement.executeQuery("SELECT * FROM departmen");



            connection.commit();
        }
        catch (Exception e) {
            if (connection != null)
                connection.rollback();
        }

        if (resultSet1 != null) {
            System.out.println("rollBacked 1");

            while (resultSet1.next()) {
                System.out.println(resultSet1.getString(1) + " " + resultSet1.getInt(2));
            }

        }
        else if (resultSet == null) {
            System.out.println("Roll Backed 2");
            while(resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getInt(2));
            }

        }

    }

    public static void savePoints() {
        initializeConnection();
        Savepoint savepoint = null;

        try{
            connection.setAutoCommit(false);
            statement.executeUpdate("INSERT INTO department VALUES('Techcrack', 910)");
            savepoint = connection.setSavepoint("save");
            statement.executeUpdate("INSERT INTO department VALUES ('Techcrack', 910)");
            connection.commit();
        }
        catch (SQLException e) {
            try {
                System.out.println("Failed To insert data!");
                connection.rollback(savepoint);
                connection.commit();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
