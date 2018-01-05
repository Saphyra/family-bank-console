package bank.app.dao;

import javax.persistence.Query;

import bank.app.domain.Account;

public class AccountDao extends AbstractDao {
    public void insert(Account newAccount) {
        entityManager.persist(newAccount);
    }

    public boolean isUsernameRegistered(Account newAccount) {
        String sql = "SELECT count(d.name) FROM Account d WHERE d.name= :name";
        Query query = entityManager.createQuery(sql);
        query.setParameter("name", newAccount.getName());
        long count = (long) query.getSingleResult();

        return !Long.valueOf(count).equals(Long.valueOf(0L));
    }
}
