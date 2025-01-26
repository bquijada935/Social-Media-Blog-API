package Controller;

import java.util.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Service.AccountService;
import Model.Message;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountRegistrationHandler);
        app.post("/login", this::postAccountLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);

        return app;
    }

    /**
     * Handler to register an Account.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountRegistrationHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addUser(account);
        if (addedAccount==null) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(addedAccount));
            context.status(200);
        }
    }

    /**
     * Handler to check if login credentials are valid.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postAccountLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account accountCredentials = accountService.loginAccount(account);
        if (accountCredentials==null) {
            context.status(401);
        } else {
            context.json(mapper.writeValueAsString(accountCredentials));
            context.status(200);
        }
    }

    /**
     * Handler to post a Message.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message postedMessage = messageService.postMessage(message);
        if (postedMessage==null) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(postedMessage));
            context.status(200);
        }
    }

    /**
     * Handler to retrieve all messages.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    public void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.retrieveAllMessages();
        context.json(messages);
        context.status(200);
    }

    /**
     * Handler to get a message by its id.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    public void getMessageByIdHandler(Context context) throws JsonProcessingException {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message messageById = messageService.retrieveMessageById(message_id);
        if (messageById != null) {
            context.json(messageById);
        }
        context.status(200);
    }

    /**
     * Handler to delete a message by its id.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    public void deleteMessageByIdHandler(Context context) throws JsonProcessingException {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message messageById = messageService.retrieveMessageById(message_id);
        if (messageById != null) {
            context.json(messageService.removeMessageById(message_id));
        }
        messageService.removeMessageById(message_id);
        context.status(200);
    }

    /**
     * Handler to update a message by its id.
     * 
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    public void updateMessageByIdHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessageById(message, message_id);
        if (updatedMessage == null) {
            context.status(400);
        } else {
            context.json(mapper.writeValueAsString(updatedMessage));
            context.status(200);
        }
    }

}