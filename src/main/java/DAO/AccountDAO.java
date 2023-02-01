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
                int gen_account_id = rs.getInt("account_id");
                return new Account(gen_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getUserById(int id){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE account_id = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account ac = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return ac;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Account getUserByName(String username){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ?;";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account ac = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                return ac;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Account> getAllAccounts(){
        List<Account> list = new ArrayList<Account>();
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account;";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Account ac = new Account(rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
                list.add(ac);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return list;
    }
}
