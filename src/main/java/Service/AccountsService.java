package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountsService {
    private AccountDAO accountDAO;
    public AccountsService(){
        accountDAO = new AccountDAO();
    }
    public Account addUser(Account user) {
        if(user.getUsername()=="" || user.getPassword().length() < 4){ //|| user.getUsername().equals(accountDAO.verifyUser(user).getUsername())){
            return null;
        }
        else{
            return accountDAO.insertAccount(user);
        }
    }
    public Account verifyUser(Account user){
        return accountDAO.verifyUser(user);
    }
    
}
