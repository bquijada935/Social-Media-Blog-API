package Service;

import Model.Message;
import DAO.MessageDAO;

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
    public MessageService(){
        messageDAO = new MessageDAO();
    }

    /**
     * Constructor for a messageService when a messageDAO is provided.
     * @param messageDAO
     */
    public MessageService(MessageDAO messageDAO){
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
    public Message postMessage(Message message){
        if (message.getMessage_text().strip().length() == 0) {
            return null;
        } else {
            return messageDAO.createMessage(message);
        }
    }
    
}
