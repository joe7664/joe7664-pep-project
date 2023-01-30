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

    public Message getMessageById(int id){
        if(id == (messageDAO.getMessageById(id).getMessage_id())){
            return null;
        }
        else{
            return messageDAO.getMessageById(id);
        }
    }

    public Message deleteMessageById(int id){
        return messageDAO.getMessageById(id);
    }

    public Message patchMessageById(int id, Message message){
        if(id != (messageDAO.getMessageById(id).getMessage_id()) || message.getMessage_text().length()>255 || message.getMessage_text() == ""){
            return null;
        }
        else{
            return messageDAO.editMessage(messageDAO.getMessageById(id));
        }
    }

    public List<Message> getMessagesByUser(int id){
        return messageDAO.getMessagesByUser(id);
    }
}
