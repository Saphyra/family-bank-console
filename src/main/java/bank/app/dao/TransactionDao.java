package bank.app.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import bank.app.domain.Transaction;

public class TransactionDao extends AbstractDao {

    public List<Transaction> getAllTransactionsByName(String accountName) {
        String sql = "SELECT t FROM Transaction t WHERE t.fromName= :accountName OR t.addresseeName= :accountName ORDER BY t.date DESC";
        TypedQuery<Transaction> query = entityManager.createQuery(sql, Transaction.class);
        query.setParameter("accountName", accountName);
        return query.getResultList();
    }

    public void save(Transaction transaction) {
        entityManager.persist(transaction);
    }
}
