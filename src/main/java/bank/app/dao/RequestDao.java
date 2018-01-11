package bank.app.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import bank.app.domain.Request;
import bank.app.domain.RequestStatus;

public class RequestDao extends AbstractDao {
    public void save(Request request) {
        entityManager.persist(request);
    }

    public List<Request> getNewRequests(String addresseeName) {
        String sql = "SELECT r FROM Request r WHERE r.addresseeName = :addresseeName AND (r.status = :sentStatus OR r.status = :pendingStatus) ORDER BY r.sendDate DESC";
        TypedQuery<Request> query = entityManager.createQuery(sql, Request.class);
        query.setParameter("addresseeName", addresseeName);
        query.setParameter("sentStatus", RequestStatus.SENT);
        query.setParameter("pendingStatus", RequestStatus.PENDING);

        return query.getResultList();
    }

    public void update(Request request) {
        entityManager.merge(request);
    }

    public List<Request> getRequestsByFromName(String fromName) {
        String sql = "SELECT r FROM Request r WHERE r.fromName= :fromName ORDER BY r.sendDate DESC";
        TypedQuery<Request> query = entityManager.createQuery(sql, Request.class);
        query.setParameter("fromName", fromName);
        return query.getResultList();
    }

    public List<Request> getPendingRequestsByFromName(String fromName) {
        String sql = "SELECT r FROM Request r WHERE r.fromName= :fromName AND (r.status = :sentStatus OR r.status = :pendingStatus) ORDER BY r.sendDate DESC";
        TypedQuery<Request> query = entityManager.createQuery(sql, Request.class);
        query.setParameter("fromName", fromName);
        query.setParameter("sentStatus", RequestStatus.SENT);
        query.setParameter("pendingStatus", RequestStatus.PENDING);
        return query.getResultList();
    }

    public Request findById(int id) {
        return entityManager.find(Request.class, id);
    }
}
