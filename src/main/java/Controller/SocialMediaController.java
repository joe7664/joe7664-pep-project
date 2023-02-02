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
        app.post("/login", this::postLoginUserHandler);
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
        if(newUser!=null){
            ctx.json(newUser);
        }else{
            ctx.status(400);
        }
    }

    private void postLoginUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account user = om.readValue(ctx.body(), Account.class);
        Account login = accountsService.verifyUser(user);
        if(login != null){
            ctx.json(login);
        }else{
            ctx.status(401);
        }
    }

    private void postMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        Message newMessage = messageService.createMessage(message);
        if(newMessage != null){
            ctx.json(newMessage);
        }else{
            ctx.status(400);
        }
    }

    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException{
        ctx.json(messageService.getAllMessages());
    }

    private void getMessageById(Context ctx) throws JsonProcessingException{
        String id_input = ctx.pathParam("message_id");
        int id = Integer.parseInt(id_input);
        Message message = messageService.getMessageById(id);
        if(message != null){
            ctx.json(message);
        }
    }

    private void deleteMessageById(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        String id_input = ctx.pathParam("message_id");
        int id = Integer.parseInt(id_input);
        Message message = messageService.deleteMessageById(id);
        if(message != null){
            ctx.json(om.writeValueAsString(message));
        }
    }

    private void patchMessageById(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        String m = message.getMessage_text();
        String id_input = ctx.pathParam("message_id");
        int id = Integer.parseInt(id_input);
        Message patchedMessage = messageService.patchMessageById(id, m);
        if(patchedMessage != null){
            ctx.json(patchedMessage);
        }
        else{
            ctx.status(400);
        }
    }

    private void getUserMessages(Context ctx) throws JsonProcessingException{
        String user_input = ctx.pathParam("account_id");
        int user = Integer.parseInt(user_input);
        ctx.json(messageService.getMessagesByUser(user));
    }
}