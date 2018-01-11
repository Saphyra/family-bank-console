package bank.app.ui.console.uiservice.requestservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.domain.Request;
import bank.app.service.AccountService;
import bank.app.service.BankSession;
import bank.app.service.RequestService;
import bank.app.ui.console.uiservice.AbstractConsoleService;

public class ConsoleRequestService extends AbstractConsoleService {
    private @Autowired BankSession session;
    private @Autowired AccountService accountService;
    private @Autowired RequestService requestService;

    public void createNewRequest() {
        Request request = getNewRequest();
        if (request != null) {
            sendRequest(request);
        }
    }

    private Request getNewRequest() {
        NewRequestCreator creator = new NewRequestCreator(input, accountService, session);
        Request request = creator.createRequest();
        return request;
    }

    private void sendRequest(Request request) {
        requestService.saveRequest(request);
        printSuccessMessage(request);
    }

    private void printSuccessMessage(Request request) {
        System.err.println("Request sent.");
        printRequest(request);
    }

    public void viewNewRequests() {
        Account account = session.getActualAccount();
        String addresseeName = account.getName();
        List<Request> newRequests = requestService.getNewRequests(addresseeName);
        interactionWithNewRequests(newRequests);
    }

    private void interactionWithNewRequests(List<Request> requests) {
        if (requests.isEmpty()) {
            printNoNewRequestMessage();
        } else {
            interactWithNewRequests();
        }
    }

    private void interactWithNewRequests() {
        NewRequestsMenu newRequestsMenu = new NewRequestsMenu(input, requestService, session.getActualAccount());
        newRequestsMenu.interactUser();
    }

    private void printNoNewRequestMessage() {
        System.out.println("No new requests!");
    }

    public void viewAllRequests() {
        List<Request> requests = getAllRequests();
        printAllRequests(requests);
    }

    private List<Request> getAllRequests() {
        Account actualAccount = session.getActualAccount();
        String name = actualAccount.getName();
        List<Request> requests = requestService.getRequestsByFromName(name);
        return requests;
    }

    private void printAllRequests(List<Request> requests) {
        System.err.println("\nYou have sent the following requests:");
        for (Request request : requests) {
            printRequest(request);
        }
    }

    private void printRequest(Request request) {
        System.out.println(request.getRequestInfo());
    }

    public void viewPendingRequests() {
        Account actualAccount = session.getActualAccount();
        String name = actualAccount.getName();
        PendingRequestMenu pendingRequestMenu = new PendingRequestMenu(input, requestService, name);
        pendingRequestMenu.interactUser();
    }
}
