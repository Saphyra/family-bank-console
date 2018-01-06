package bank.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import bank.app.dao.RequestDao;
import bank.app.domain.Request;

public class RequestService extends AbstractService {
    private @Autowired RequestDao requestDao;

    @Transactional
    public void saveRequest(Request request) {
        requestDao.save(request);
    }
}
