package bank.app.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import bank.app.dao.RequestDao;
import bank.app.domain.Request;
import bank.app.domain.RequestStatus;
import bank.app.domain.Transaction;

public class RequestService extends AbstractService {
    private @Autowired RequestDao requestDao;
    private @Autowired TransactionService transactionService;

    @Transactional
    public void saveRequest(Request request) {
        request.setSendDate(new Date());
        requestDao.save(request);
    }

    @Transactional(readOnly = true)
    public List<Request> getNewRequests(String addresseeName) {
        List<Request> requests = requestDao.getNewRequestsByAddresseeName(addresseeName);
        setNewRequestsPending(requests);

        return requests;
    }

    private void setNewRequestsPending(List<Request> requests) {
        for (Request request : requests) {
            if (request.getStatus() == RequestStatus.SENT) {
                System.out.println(request.getSendDate());
                request.setStatus(RequestStatus.PENDING);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<Request> getRequestsByFromName(String fromName) {
        return requestDao.getRequestsByFromName(fromName);
    }

    @Transactional(readOnly = true)
    public List<Request> getPendingRequestsByFromName(String fromName) {
        return requestDao.getPendingRequestsByFromName(fromName);
    }

    public void cancelRequest(int requestId) {
        Request request = requestDao.findById(requestId);
        request.setStatus(RequestStatus.CANCELLED);
    }

    @Transactional
    public void deny(Request request, String answerMessage) {
        request.setStatus(RequestStatus.DENIED);
        request.setAnswerMessage(answerMessage);
        request.setAnswerDate(new Date());
        requestDao.update(request);
    }

    @Transactional
    public void accept(Request request, String answerMessage) {
        request.setStatus(RequestStatus.ACCEPTED);
        request.setAnswerMessage(answerMessage);
        request.setAnswerDate(new Date());

        Transaction transaction = Transaction.requestTransaction(request);
        transactionService.sendTransaction(transaction);

        requestDao.update(request);
    }
}
