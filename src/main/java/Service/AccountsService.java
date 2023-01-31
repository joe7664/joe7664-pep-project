package Service;

import Model.Account;

import java.util.List;
import java.util.ArrayList;

import DAO.AccountDAO;

public class AccountsService {
    private AccountDAO accountDAO;
    public AccountsService(){
        accountDAO = new AccountDAO();
    }
    public Account addUser(Account user) {
        if(user.getUsername()=="" || user.getPassword().length() < 4){
            return null;
        }
        else{
            return accountDAO.insertAccount(user);
        }
    }
    public Account verifyUser(Account user){
        List<Account> list = new ArrayList<Account>();
        list = accountDAO.getAllAccounts();
        if(list.size() == 0){
            return null;
        }
        Account ac = accountDAO.getUserByName(user.getUsername());
        if(user.getPassword().equals(ac.getPassword())){
            return ac;
        }
        return null;
    }
    
}
