package JavaDaseConnectivity;


import org.example.Banking.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException {
        // BatchProcessing.batchProcessing();
        // BatchProcessing.savePoints();

        try {
            Class.forName("org.example.Banking.DataAcessingObject");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Transaction transaction = new Transaction();

        transaction.transferMoney(1,4, 1767.69);

//        System.out.println(LocalDate.now());
    }
}