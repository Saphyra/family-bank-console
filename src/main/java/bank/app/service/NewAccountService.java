package bank.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import bank.app.dao.AccountDao;
import bank.app.domain.Account;

public class NewAccountService extends AbstractService {
    private @Autowired AccountDao accountDao;

    @Transactional
    public void save(Account newAccount) {
        accountDao.insert(newAccount);
    }

    @Transactional(readOnly = true)
    public boolean isUsernameRegistered(Account newAccount) {
        return accountDao.isUsernameRegistered(newAccount);
    }
}
