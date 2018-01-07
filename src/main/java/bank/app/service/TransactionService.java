package bank.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import bank.app.dao.TransactionDao;
import bank.app.domain.Account;
import bank.app.domain.Bank;
import bank.app.domain.Transaction;

public class TransactionService extends AbstractService {
    private @Autowired TransactionDao transactionDao;
    private @Autowired BankService bankService;
    private @Autowired AccountService accountService;
    private @Autowired BankSession session;

    @Transactional
    public List<Transaction> getAllTransactionsByName(String accountName) {
        return transactionDao.getAllTransactionsByName(accountName);
    }

    @Transactional
    public void sendTransaction(Transaction transaction) {
        switch (transaction.getType()) {
        case DEPOSIT:
            executeDepositTransaction(transaction);
            break;
        case REQUEST:
            // TODO implement
            break;
        case SEND:
            executeSendTransaction(transaction);
            break;
        case WITHDRAW:
            executeWithdrawTransaction(transaction);
            break;
        case EARN:
            executeEarnTransaction(transaction);
            break;
        case SPEND:
            executeSpendTransaction(transaction);
            break;
        }

        transactionDao.save(transaction);
    }

    private void executeDepositTransaction(Transaction transaction) {
        Bank bank = session.getActualBank();
        Account account = session.getActualAccount();

        bank.addBalance(transaction.getMoney());
        account.addBankBalance(transaction.getMoney());
        account.deductPrivateBalance(transaction.getMoney());

        bankService.update(bank);
        accountService.update(account);
    }

    private void executeWithdrawTransaction(Transaction transaction) {
        Bank bank = session.getActualBank();
        Account account = session.getActualAccount();

        bank.deductBalance(transaction.getMoney());
        account.deductBankBalance(transaction.getMoney());
        account.addPrivateBalance(transaction.getMoney());

        bankService.update(bank);
        accountService.update(account);
    }

    private void executeEarnTransaction(Transaction transaction) {
        Account account = session.getActualAccount();
        account.addPrivateBalance(transaction.getMoney());
        accountService.update(account);
    }

    private void executeSpendTransaction(Transaction transaction) {
        Account account = session.getActualAccount();
        account.deductPrivateBalance(transaction.getMoney());
        accountService.update(account);
    }

    private void executeSendTransaction(Transaction transaction) {
        Account sender = session.getActualAccount();
        Account addressee = accountService.findByName(transaction.getAddresseeName());

        sender.deductPrivateBalance(transaction.getMoney());
        addressee.addPrivateBalance(transaction.getMoney());

        accountService.update(sender);
    }
}
