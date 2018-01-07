package bank.app.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import bank.app.dao.AccountDao;
import bank.app.domain.Account;

public class AccountService extends AbstractService {
    private @Autowired AccountDao accountDao;

    @Transactional(readOnly = true)
    public Account getAccount(String username, String password) throws NoResultException {
        return accountDao.getAccount(username, password);
    }

    @Transactional(readOnly = true)
    public List<Account> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    @Transactional(readOnly = true)
    public Account findById(int accountId) {
        return accountDao.findById(accountId);
    }

    @Transactional
    public List<Account> getAddressees(int accountId) {
        List<Account> accounts = getAllAccounts();
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
