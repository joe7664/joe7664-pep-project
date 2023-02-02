package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message createMessage(Message message){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()){
                return new Message(rs.getInt("message_id"),
                        message.getPosted_by(),
                        message.getMessage_text(),
                        message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public List<Message> getAllMessages(){
        Connection con = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message m = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(m);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
    
    public Message getMessageById(int id){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message WHERE message_id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message m = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                    return m;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public void deleteMessage(int id){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "DELETE FROM message WHERE message_id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void editMessage(int id, String message){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, message);
            ps.setInt(2, id);
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    
    public List<Message> getMessagesByUser(int id){
        Connection con = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try{
            String sql = "SELECT * FROM message WHERE posted_by = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message m = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(m);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }
}
