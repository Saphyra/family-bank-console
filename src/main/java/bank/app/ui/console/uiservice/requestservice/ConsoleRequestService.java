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
        NewRequestCreator creator = new NewRequestCreator(input, accountService);
        Account account = session.getActualAccount();
        Request request = creator.createRequest(account);

        if (request != null) {
            sendRequest(request);
        }
    }

    private void sendRequest(Request request) {
        requestService.saveRequest(request);
        System.out.println("Request sent.");
        System.out.println(request.getRequestInfo());
    }

    public void viewNewRequests() {
        Account account = session.getActualAccount();
        String addresseeName = account.getName();
        List<Request> newRequests = requestService.getNewRequests(addresseeName);
        userInteractionWithNewRequests(newRequests);
    }

    private void userInteractionWithNewRequests(List<Request> requests) {
        if (requests.isEmpty()) {
            System.out.println("No new requests!");
        } else {
            NewRequestsMenu newRequestsMenu = new NewRequestsMenu(input);
            newRequestsMenu.addRequests(requests);
        }
    }

    public void viewAllRequests() {
        Account actualAccount = session.getActualAccount();
        String name = actualAccount.getName();
        List<Request> requests = requestService.getRequestsByFromName(name);

        System.err.println("\nYou have sent the following requests:");
        for (Request request : requests) {
            System.out.println(request.getRequestInfo());
        }
    }

    public void viewPendingRequests() {
        Account actualAccount = session.getActualAccount();
        String name = actualAccount.getName();
        PendingRequestMenu pendingRequestMenu = new PendingRequestMenu(input, requestService, name);
        pendingRequestMenu.interactUser();
    }
}
