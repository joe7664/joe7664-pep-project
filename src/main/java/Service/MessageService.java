package Service;

import Model.Account;
import Model.Message;
import DAO.MessageDAO;
import DAO.AccountDAO;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message){
        if(message.getMessage_text() == "" || message.getMessage_text().length() >= 255 || accountDAO.getUserById(message.getPosted_by()) == null){
            return null;
        }
        return messageDAO.createMessage(message);
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id){
        if(id == (messageDAO.getMessageById(id).getMessage_id())){
            return messageDAO.getMessageById(id);
        }
        else{
            return null;
        }
    }

    public Message deleteMessageById(int id){
        Message m = messageDAO.getMessageById(id);
        if(m != null){
            messageDAO.deleteMessage(id);
            return m;
        }
        else{
            return null;
        }
    }

    public Message patchMessageById(int id, String message){
        if(message.length() >= 255 || message == "" || messageDAO.getMessageById(id) == null){
            return null;
        }
        else{
            messageDAO.editMessage(id, message);
            return messageDAO.getMessageById(id);
        }
    }

    public List<Message> getMessagesByUser(int id){
        return messageDAO.getMessagesByUser(id);
    }
}
