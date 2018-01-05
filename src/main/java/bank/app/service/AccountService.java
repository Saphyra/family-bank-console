package bank.app.service;

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
}
