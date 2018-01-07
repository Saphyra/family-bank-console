package bank.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import bank.app.dao.BankDao;
import bank.app.domain.Bank;

public class BankService extends AbstractService {
    private @Autowired BankDao bankDao;

    @Transactional
    public void save(Bank bank) {
        bankDao.save(bank);
    }

    public void update(Bank bank) {
        bankDao.update(bank);
    }

}
