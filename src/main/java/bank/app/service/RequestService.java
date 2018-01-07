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
        requestDao.save(request);
    }

    @Transactional
    public List<Request> getNewRequests(String addresseeName) {
        List<Request> requests = requestDao.getNewRequests(addresseeName);
        setNewRequestsPending(requests);

        return requests;
    }

    private void setNewRequestsPending(List<Request> requests) {
        for (Request request : requests) {
            if (request.getStatus() == RequestStatus.SENT) {
                System.out.println(request.getSendDate());
                setRequestStatus(request, RequestStatus.PENDING);
            }
        }
    }

    @Transactional
    public void setRequestStatus(Request request, RequestStatus status) {
        requestDao.setRequestStatus(request, status);
    }

    @Transactional
    public List<Request> getRequestsByFromName(String fromName) {
        return requestDao.getRequestsByFromName(fromName);
    }

    @Transactional
    public List<Request> getPendingRequestsByFromName(String fromName) {
        return requestDao.getPendingRequestsByFromName(fromName);
    }

    @Transactional
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
