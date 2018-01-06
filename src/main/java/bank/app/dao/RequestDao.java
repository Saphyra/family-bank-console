package bank.app.dao;

import bank.app.domain.Request;

public class RequestDao extends AbstractDao {
    public void save(Request request) {
        entityManager.persist(request);
    }
}
