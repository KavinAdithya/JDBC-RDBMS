package JavaDaseConnectivity.Banking;

import java.sql.*;
import java.time.LocalDate;

public class Transaction {
    Connection connection = null;
    Statement statement = null;

    /**
     * This Method handles the complete transaction between two accounts.
     * This Method has two save points to roll back, namely after money deduction and after money added to another account.
     * @param fromAccount Account Number where money transferred from.
     * @param toAccount Account Number To Credit amount into another account.
     * @param amount Amount where transferred between two accounts.
     */
    public  void transferMoney(int fromAccount,  int toAccount, double amount) {

        // Checking the form account, to account and amount is non-negative.
        if (fromAccount < 0 || toAccount < 0 || amount <= 0)
            throw new RuntimeException("Invalid Details!!!");

        // Getting Database Connection Object.
         connection = DataAcessingObject.getConnection();

        // Getting Statement Object from DataAccessingObject utility class.
         statement = DataAcessingObject.getStatement();

         // Declaring two save point references.
         Savepoint afterDeduction = null;
         Savepoint amountCredited1 =  null;

        try {
            // Disabling Autocommit.
            connection.setAutoCommit(false);

            // Processing Amount deduction and creating save Point,
            amountDeduction(fromAccount, amount);
            afterDeduction = connection.setSavepoint("deductedMoney");



            // Amount Crediting to another account and creating save Point.
            amountCredited(toAccount, amount);
            amountCredited1 = connection.setSavepoint("amountCredited");



            String transactionCompleteQuery = "INSERT INTO transaction_log(from_account, to_account,  transaction_time, status) " +
                    "VALUES("+ fromAccount + ", " + toAccount + ",'" + LocalDate.now() + "', 's')";

            //Updating the transaction details in the database.
            int transactionSuccessfully = statement.executeUpdate(transactionCompleteQuery);

            // Checking the changes made successfully.
            if (transactionSuccessfully == 1)
                System.out.println("Amount Has been Successfully transferred from " + fromAccount + " to account " + toAccount + " Amount of : " + amount);

            // Committing the changes permanently to the database.
            connection.commit();
        } catch (SQLException | RuntimeException e) {

            // Roll back the changes made after amountCredited savePoint.
            if (amountCredited1 != null) {
                try {
                    connection.rollback(amountCredited1);
                    connection.commit();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            // Roll back the changes made after the afterDeduction savepoint.
            else if (afterDeduction != null) {
                try {
                    connection.rollback(afterDeduction);
                    connection.commit();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
            throw new RuntimeException(e);
        }
    }

    /**
     * This Method checks the balance of the given account number and also checks the account
     * has sufficient amount compared to given money.
     * @param fromAccount Account number to fetch account balance.
     * @param amount The Amount for checking the account contains the amount which is provided.
     * @return true, It will return true if an account has a sufficient amount.
     * @throws SQLException It will Throw SQLException in case of finding account balance.
     */

    private boolean isAvailableBalance(int fromAccount, double amount) throws SQLException {

        // Checking the account number and amount is non-negative.
        if (fromAccount <= 0 || amount < 0)
            throw new RuntimeException("Invalid Account Number or Amount");

        // Query to retrieve the balance of the given account number.
        String balanceQuery = "SELECT balance FROM account where account_id = " + fromAccount;

        // Result of the query execution.
        ResultSet resultSet = statement.executeQuery(balanceQuery);

        resultSet.next();

        // Fetching the amount from the resultSet.
        double currentBalance = resultSet.getDouble(1);

        // Checking the current balance is greater than or equal to given balance.
        return currentBalance >= amount;
    }


    /**
     * Amount Deduction Method is used to deduct the amount from the user account.
     * It Will first the available balance is sufficient.
     * In case of insufficient balance or failed to update the transaction,
     * it will throw RunTimeException.
     * The Invoker method is responsible to handle the exception or else
     * the Program will be terminated.
     * Here Explicitly throwing the Exceptions.
     * @param fromAccount Account number where amount to be debited.
     * @param amount      Amount to be debited from the given account.
     * @throws SQLException It will throw SQLException in case of failed to complete the transaction.
     */
    private void amountDeduction(int fromAccount, double amount) throws SQLException {

        // Checking the account number and amount is non-negative.
        if (fromAccount < 0 || amount <= 0)
            throw new RuntimeException("Invalid to account number or amount !!!");

        // Checking the balance of the account is enough.
        if ( ! isAvailableBalance(fromAccount, amount))
            throw new RuntimeException("Your Account balance is insufficient!!");

        // Query To update the MySQL database as deducting the amount in the account.
        String amountDeduction = "Update account SET balance = balance - " + amount + " WHERE account_id = " + fromAccount;

        // Executing the query to make changes on the database.
        int rowsAffected = statement.executeUpdate(amountDeduction);

        // If Changes made on a database without any failure, the method will return number which indicates the number of rows affected in the database.
        if (rowsAffected == 1) {
            System.out.println("Amount Has Been Successfully debited..");
        }
        else {
            // In case failure throwing exception.
            throw new RuntimeException("Failed To debit amount from your account! try Again...");
        }
    }

    /**
     * amountCredited method will credit the amount in another account, and it will update in a database.
     * @param toAccount Account Number to credit amount
     * @param amount    Amount to be Credited
     * @throws SQLException It will Throw Exception when it fails to complete transaction successfully
     */

    public void amountCredited(int toAccount, double amount) throws SQLException{

        // Checking the account number and amount is non-negative.
        if (toAccount < 0 || amount <= 0)
            throw new RuntimeException("Invalid Account Number or Amount");

        // Query To add money to another account.
        String creditAmountQuery = "UPDATE account SET balance = balance + " + amount + " WHERE account_id = " + toAccount;

        System.out.println("Created");
        // Executing the query to made changes on the database.
        int rowsAffected = statement.executeUpdate(creditAmountQuery);

        // Checking the database affected or not.
        if (rowsAffected == 1) {
            System.out.println("Amount Has Been Credited Successfully !!!");
        }
        else {
            // It will Throw exception in case failed to make changes on the database.
           throw new IllegalArgumentException("Amount Has Been failed to credit");
        }

    }
}