package bank.app.dao;

import javax.persistence.TypedQuery;

import bank.app.domain.Bank;

public class BankDao extends AbstractDao {
    public Bank getBank() {
        String sql = "SELECT b FROM Bank b WHERE b.name = :name";
        TypedQuery<Bank> query = entityManager.createNamedQuery(sql, Bank.class);
        query.setParameter("name", "Bank");
        return query.getSingleResult();
    }

    public void save(Bank bank) {
        entityManager.persist(bank);
    }

    public void update(Bank bank) {
        entityManager.merge(bank);
    }
}
