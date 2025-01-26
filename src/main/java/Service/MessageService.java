package Service;

import Model.Message;
import DAO.MessageDAO;

import java.util.*;

/**
 * The purpose of a Service class is to contain "business logic" that sits between the web layer (controller) and
 * persistence layer (DAO). That means that the Service class performs tasks that aren't done through the web or
 * SQL: programming tasks like checking that the input is valid, conducting additional security checks, or saving the
 * actions undertaken by the API to a logging file.
 */
public class MessageService {

    MessageDAO messageDAO;

    /**
     * No-args constructor for a messageService instantiates a plain messageDAO.
     */
    public MessageService() {
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for a messageService when a messageDAO is provided.
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    /**
     * This method should also return the created message. When this method is used, it should return the new created message,
     * which will contain the message's id. This way, any part of the application that uses this method has
     * all information about the new message, because knowing the new message's ID is necessary. This means that the
     * method should return the Message returned by the messageDAO's createMessage method, and not the message provided by
     * the parameter 'message'.
     *
     * @param message an object representing a new Message.
     * @return the newly created message if the create operation was successful, including the message_id. We do this to
     *         inform our provide the front-end client with information about the added Message.
     */
    public Message postMessage(Message message) {
        if (message.getMessage_text().length() > 255) {
            return null;
        } else if (message.getMessage_text().strip().length() == 0) {
            return null;
        } else {
            return messageDAO.createMessage(message);
        }
    }

    /**
     * Retrieves all messages.
     * 
     * @return all messages.
     */
    public List<Message> retrieveAllMessages() {
        return messageDAO.getAllMessages();
    }

    /**
     * An message_id will be provided in Message. Method should check if the message_id already exists before it attempts to
     * retrieve a message.
     * 
     * @param message_id the message id.
     * @return message if it message_id exists or null.
     */
    public Message retrieveMessageById(int message_id) {
        return messageDAO.getMessageById(message_id);
    }

    /**
     * An message_id will be provided in Message. Method should delete a message from the message table based on its id.
     * 
     * @param message_id the message id.
     * @return message if it message_id exists or null.
     */
    public Message removeMessageById(int message_id) {
        Message deletedMessage = messageDAO.getMessageById(message_id);
        messageDAO.deleteMessageById(message_id);
        return deletedMessage;
    }

    /**
     * An message_id will be provided in Message. Method should update a message from the message table based on its id.
     * 
     * @param message a message object.
     * @param message_id the message id.
     * @return updates a message if message_id exists or null.
     */
    public Message updateMessageById(Message message, int message_id) {
        if (messageDAO.getMessageById(message_id) == null) {
            return null;
        } else if (message.getMessage_text().length() > 255) {
            return null;
        } else if (message.getMessage_text().strip().length() == 0) {
            return null;
        } else {
            messageDAO.updateMessage(message, message_id);
            return messageDAO.getMessageById(message_id);
        }
    }

    /**
     * Retrieves all messages of a given user.
     * 
     * @param account_id the account id.
     * @return all user messages.
     */
    public List<Message> retrieveUserMessages(int account_id) {
        return messageDAO.getUserMessages(account_id);
    }
    
}
