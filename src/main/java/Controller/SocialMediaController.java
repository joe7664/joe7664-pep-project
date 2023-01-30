package Controller;

import Model.Account;
import Model.Message;
import Service.AccountsService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonpCharacterEscapes;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountsService accountsService;
    MessageService messageService;
    public SocialMediaController(){
        this.accountsService=new AccountsService();
        this.messageService=new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postUserHandler);
        app.get("/login", this::getUserHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}", this::patchMessageById);
        app.get("/accounts/{account_id}/messages", this::getUserMessages);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account user = om.readValue(ctx.body(), Account.class);
        Account newUser = accountsService.addUser(user);
        ctx.contentType("application/json");
        if(newUser!=null){
            ctx.json(om.writeValueAsString(newUser));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    private void getUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account user = om.readValue(ctx.body(), Account.class);
        if(accountsService.verifyUser(user)!=null){
            ctx.json(om.writeValueAsString(user));
            ctx.status(200);
        }else{
            ctx.status(401);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        if(messageService.createMessage(message) != null){
            ctx.json(om.writeValueAsString(message));
            ctx.status(200);
        }else{
            ctx.status(400);
        }
    }

    public void getAllMessagesHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        List<Message> messages = messageService.getAllMessages();
        ctx.json(om.writeValueAsString(messages));
        ctx.status(200);
    }

    public void getMessageById(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        String x = ctx.pathParam("message_id");
        Message message = om.readValue(ctx.body(), Message.class);
        ctx.json(om.writeValueAsString(message));
        ctx.status(200);
    }

    public void deleteMessageById(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        ctx.json(om.writeValueAsString(message));
        ctx.status(200);
    }

    public void patchMessageById(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        if(messageService.updateMessageById(message) != null){
            ctx.json(om.writeValueAsString(message));
            ctx.status(200);
        }
        else{
            ctx.status(400);
        }
    }

    public void getUserMessages(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account user = om.readValue(ctx.body(), Account.class);
        List<Message> messages = messageService.getMessagesByUser(user);
        ctx.json(om.writeValueAsString(messages));
        ctx.status(200);
    }
}