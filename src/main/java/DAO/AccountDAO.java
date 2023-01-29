package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {
    public Account insertAccount(Account account){
        if(account.getUsername()!="" && account.getPassword().length() >= 4){
            Connection con = ConnectionUtil.getConnection();
            try{
                String accs = "SELECT username FROM socialmedia WHERE username = ?;";
                PreparedStatement prep = con.prepareStatement(accs);
                ResultSet res = prep.executeQuery();
                while(res.next()){
                    String name = res.getString("username");
                    if(name==account.getUsername()){
                        return null;
                    }
                }

                String sql = "INSERT INTO socialmedia (username, password) VALUES (?, ?);";
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, account.getUsername());
                ps.setString(2, account.getPassword());
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next()){
                    int gen_account_id = (int) rs.getLong(1);
                    return new Account(gen_account_id, account.getUsername(), account.getPassword());
                }
            
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public Account verifyUser(Account account){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM socialmedia WHERE username = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account ac = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                if(account.getPassword().equals(ac.getPassword())){
                    return ac;
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
