package bank.app.dao;

import javax.persistence.TypedQuery;

import bank.app.domain.Bank;

public class BankDao extends AbstractDao {
    public Bank getBank(String bankName) {
        String sql = "SELECT b FROM Bank b WHERE b.name = :name";
        TypedQuery<Bank> query = entityManager.createQuery(sql, Bank.class);
        query.setParameter("name", bankName);
        return query.getSingleResult();
    }

    public void save(Bank bank) {
        entityManager.persist(bank);
    }

    public void update(Bank bank) {
        entityManager.merge(bank);
    }
}
