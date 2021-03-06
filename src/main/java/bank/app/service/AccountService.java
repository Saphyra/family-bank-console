package bank.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import bank.app.dao.AccountDao;
import bank.app.domain.Account;

public class AccountService extends AbstractService {
    private @Autowired AccountDao accountDao;

    @Transactional(readOnly = true)
    public Account getAccount(String username, String password) throws NoSuchElementException {
        return accountDao.getAccount(username, password);
    }

    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    @Transactional(readOnly = true)
    public Account getAccount(int accountId) {
        return accountDao.findById(accountId);
    }

    @Transactional(readOnly = true)
    public List<Account> getAddressees(int accountId) {
        List<Account> accounts = getAllAccounts();
        return selectAddresseesFromUsers(accountId, accounts);
    }

    private List<Account> selectAddresseesFromUsers(int accountId, List<Account> accounts) {
        List<Account> addressees = new ArrayList<>();

        for (Account account : accounts) {
            if (account.getId() != accountId) {
                addressees.add(account);
            }
        }
        return addressees;
    }

    @Transactional
    public void update(Account account) {
        accountDao.update(account);
    }

    @Transactional
    public Account findByName(String accountName) {
        return accountDao.findByName(accountName);
    }
}
