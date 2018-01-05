package bank.app.service;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import bank.app.dao.AccountDao;
import bank.app.domain.Account;

public class LoginService extends AbstractService {
    private @Autowired AccountDao accountDao;

    @Transactional(readOnly = true)
    public Account getAccount(String username, String password) throws NoResultException {
        return accountDao.getAccount(username, password);
    }
}
