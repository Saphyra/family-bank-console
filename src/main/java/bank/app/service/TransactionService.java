package bank.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.dao.TransactionDao;
import bank.app.domain.Transaction;

public class TransactionService extends AbstractService {
    private @Autowired TransactionDao transactionDao;

    public List<Transaction> getAllTransactionsByName(String accountName) {
        return transactionDao.getAllTransactionsByName(accountName);
    }

}
