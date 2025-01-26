package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.*;

/**
 * A DAO is a class that mediates the transformation of data between the format of objects in Java to rows in a
 * database.
 * 
 * We may assume that the database has already created a table named 'message'.
 * It contains similar values as the Message class:
 * message_id, which is of type int and is a primary key,
 * posted_by, which is of type int and is a foreign key that references account_id,
 * message_text, which is of type varchar(255),
 * time_posted_epoch, which is of type bigint.
 */
public class MessageDAO {

    /**
     * Creates a message in the message table.
     * 
     * @param message an object modelling a Message. The message object does not contain a message ID.
     * @return successfully created message or null.
     */
    public Message createMessage(Message message) {

        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if (pkeyResultSet.next()) {
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    /**
     * Retrieves all messages from the message table.
     * 
     * @return all Messages.
     */
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> allMessages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                allMessages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return allMessages;
    }

    /**
     * Retrieves a message from the message table based on its message_id.
     * 
     * @param messageId the message id.
     * @return a message identified by message_id.
     */
    public Message getMessageById(int messageId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Deletes a message from the message table based on its message_id.
     * 
     * @param messageId the message id.
     */
    public void deleteMessageById(int messageId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageId);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Updates a message from the message table based on its message_id.
     * 
     * @param message a message.
     * @param messageId the message id.
     */
    public void updateMessage(Message message, int messageId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE message SET posted_by = ?, message_text = ?, time_posted_epoch = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.setInt(4, messageId);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

}