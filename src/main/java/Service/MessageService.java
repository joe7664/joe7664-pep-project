package Service;

import Model.Account;
import Model.Message;
import DAO.MessageDAO;

import java.util.List;

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message){
        if(message.getMessage_text() == "" || message.getMessage_text().length() >= 255){
            return null;
        }
        else{
            return messageDAO.createMessage(message);
        }
    }

    public List<Message> getAllMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(Message message){
        if(message.getMessage_id() == (messageDAO.getMessageById(message.getMessage_id()).getMessage_id())){
            return null;
        }
        else{
            return messageDAO.getMessageById(message.getMessage_id());
        }
    }

    public Message deleteMessageById(Message message){
        return messageDAO.getMessageById(message.getMessage_id());
    }

    public Message updateMessageById(Message message){
        if(message.getMessage_id() != (messageDAO.getMessageById(message.getMessage_id()).getMessage_id()) || message.getMessage_text().length()>255 || message.getMessage_text() == ""){
            return null;
        }
        else{
            return messageDAO.editMessage(message);
        }
    }

    public List<Message> getMessagesByUser(Account account){
        return messageDAO.getMessagesByUser(account.getAccount_id());
    }
}
