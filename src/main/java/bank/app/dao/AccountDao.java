package bank.app.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

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

    public List<Account> getAllAccounts() {
        String sql = "SELECT a FROM Account a";
        TypedQuery<Account> query = entityManager.createQuery(sql, Account.class);
        List<Account> result = query.getResultList();

        return result;
    }

    public Account findById(int accountId) {
        return entityManager.find(Account.class, accountId);
    }

    public void update(Account account) {
        entityManager.merge(account);
    }

    public Account findByName(String name) {
        String sql = "SELECT a FROM Account a WHERE a.name = :name";
        TypedQuery<Account> query = entityManager.createQuery(sql, Account.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
