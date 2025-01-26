package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;

/**
 * A DAO is a class that mediates the transformation of data between the format of objects in Java to rows in a
 * database.
 * 
 * We may assume that the database has already created a table named 'account'.
 * It contains similar values as the Account class:
 * account_id, which is of type int and is a primary key,
 * username, which is of type varchar(255) and is a unique value,
 * password, which is of type varchar(255).
 */
public class AccountDAO {
    
    /**
     * Registers a user in the account table.
     * 
     * @param account an object modelling a Account. The account object does not contain an account ID.
     * @return successfully registered accounts or null.
     */
    public Account registerUser(Account account) {

        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO account(username, password) VALUES(?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    /**
     * Checks login credentials.
     * 
     * @param account an object modelling a Account. The account object does not contain an account ID.
     * @return account information if login credentials match.
     */
    public Account checkLogin(Account account) {

        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Account accountInformation = new Account(rs.getInt("account_id"), 
                rs.getString("username"),
                rs.getString("password"));
                return accountInformation;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

}
