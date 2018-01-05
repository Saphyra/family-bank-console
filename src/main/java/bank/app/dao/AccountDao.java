package bank.app.dao;

import javax.persistence.Query;

import bank.app.domain.Account;

public class AccountDao extends AbstractDao {
    public void insert(Account newAccount) {
        entityManager.persist(newAccount);
    }

    public Account getAccount(String username, String password) {
        Account result = null;

        String sql = "SELECT a FROM Account a WHERE a.name= :username AND a.password= :password";
        Query query = entityManager.createQuery(sql);
        query.setParameter("username", username);
        query.setParameter("password", password);

        result = (Account) query.getSingleResult();

        return result;
    }

    public boolean isUsernameRegistered(Account newAccount) {
        String sql = "SELECT count(a.name) FROM Account a WHERE a.name= :username";
        Query query = entityManager.createQuery(sql);
        query.setParameter("username", newAccount.getName());
        long count = (long) query.getSingleResult();

        return !Long.valueOf(count).equals(Long.valueOf(0L));
    }
}
