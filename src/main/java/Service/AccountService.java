package Service;

import Model.Account;
import DAO.AccountDAO;

/**
 * The purpose of a Service class is to contain "business logic" that sits between the web layer (controller) and
 * persistence layer (DAO). That means that the Service class performs tasks that aren't done through the web or
 * SQL: programming tasks like checking that the input is valid, conducting additional security checks, or saving the
 * actions undertaken by the API to a logging file.
 */
public class AccountService {
    
    AccountDAO accountDAO;

    /**
     * No-args constructor for a accountService instantiates a plain accountDAO.
     */
    public AccountService() {
        accountDAO = new AccountDAO();
    }

    /**
     * Constructor for a accountService when a accountDAO is provided.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /**
     * This method should also return the added account. When this method is used, it should return the new registered account,
     * which will contain the account's id. This way, any part of the application that uses this method has
     * all information about the new account, because knowing the new account's ID is necessary. This means that the
     * method should return the Account returned by the accountDAO's registerUser method, and not the account provided by
     * the parameter 'account'.
     *
     * @param account an object representing a new Account.
     * @return the newly added account if the add operation was successful, including the account_id. We do this to
     *         inform our provide the front-end client with information about the added Account.
     */
    public Account addUser(Account account) {
        if (account.getUsername().strip().length() == 0) {
            return null;
        } else if (account.getPassword().length() < 4) {
            return null;
        } else {
            return accountDAO.registerUser(account);
        }
    }

    /**
     * This method should call the checkLogin method from the accountDAO to check if the credentials are valid.
     *
     * @param account an object representing a new Account.
     * @return the account information if the login credentials were valid.
     */
    public Account loginAccount(Account account) {
        return accountDAO.checkLogin(account);
    }

}
