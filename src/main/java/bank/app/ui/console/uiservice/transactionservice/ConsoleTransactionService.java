package bank.app.ui.console.uiservice.transactionservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.domain.Transaction;
import bank.app.service.BankSession;
import bank.app.service.TransactionService;
import bank.app.ui.console.uiservice.AbstractConsoleService;

public class ConsoleTransactionService extends AbstractConsoleService {
    private @Autowired BankSession session;
    private @Autowired TransactionService transactionService;

    public void viewTransactions() {
        Account account = session.getActualAccount();
        String accountName = account.getName();
        List<Transaction> transactions = transactionService.getAllTransactionsByName(accountName);
        printTransactions(transactions);
    }

    private void printTransactions(List<Transaction> transactions) {
        for (Transaction transaction : transactions) {
            System.out.println(transaction.getTransactionInfo());
        }
    }

    public void newTransaction() {
        // TODO implement
    }
}
