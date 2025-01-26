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
    public Message createMessage(Message message){

        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()) {
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    /**
     * TODO: retrieve all authors from the Author table.
     * You only need to change the sql String.
     * @return all Authors.
     */
    /*public List<Author> getAllAuthors(){
        Connection connection = ConnectionUtil.getConnection();
        List<Author> authors = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "SELECT * FROM author";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Author author = new Author(rs.getInt("id"), rs.getString("name"));
                authors.add(author);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return authors;
    }*/

    /**
     * TODO: insert an author into the Author table.
     * The author_id should be automatically generated by the sql database if it is not provided because it was
     * set to auto_increment. Therefore, you only need to insert a record with a single column (name).
     * You only need to change the sql String and leverage PreparedStatements' setString methods.
     */
    /*public Author insertAuthor(Author author){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO author(id, name) VALUES(?, ?)" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString method here.
            preparedStatement.setInt(1, author.getId());
            preparedStatement.setString(2, author.getName());
            
            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_author_id = (int) pkeyResultSet.getLong(1);
                return new Author(generated_author_id, author.getName());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }*/

}