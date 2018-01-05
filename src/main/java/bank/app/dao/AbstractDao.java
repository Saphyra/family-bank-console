package bank.app.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class AbstractDao {
    @PersistenceContext
    protected EntityManager entityManager;
}
