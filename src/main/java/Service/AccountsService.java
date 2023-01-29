package Service;

import Model.Account;
import DAO.AccountDAO;

import java.util.List;

public class AccountsService {
    private AccountDAO accountDAO;
    public AccountsService(){
        accountDAO = new AccountDAO();
    }
    public Account addUser(Account user) {
        return accountDAO.insertAccount(user);
    }
    public Account verifyUser(Account user){
        return accountDAO.verifyUser(user);
    }
    
}
