package bank.app.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import bank.app.domain.Transaction;

public class TransactionDao extends AbstractDao {

    public List<Transaction> getAllTransactionsByName(String accountName) {
        String sql = "SELECT t FROM Transaction t WHERE t.fromname= :accountName OR t.addresseename= :accountName ORDER BY t.date DESC";
        TypedQuery<Transaction> query = entityManager.createQuery(sql, Transaction.class);
        query.setParameter("accountName", accountName);
        return query.getResultList();
    }

}
