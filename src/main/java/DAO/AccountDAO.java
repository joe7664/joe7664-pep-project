package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountDAO {
    public Account insertAccount(Account account){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username, password) VALUES (?, ?);";
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()){
                int gen_account_id = (int) rs.getLong(1);
                return new Account(gen_account_id, account.getUsername(), account.getPassword());
            }
            
            }catch(SQLException e){
                System.out.println(e.getMessage());
        }
        return null;
    }

    public String getUserByName(String name){
        Connection con = ConnectionUtil.getConnection();
        String ac="";
        try{
            String sql = "SELECT username FROM account WHERE username = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ac = rs.getString("username");
                return ac;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account verifyUser(Account account){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ?;";
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
