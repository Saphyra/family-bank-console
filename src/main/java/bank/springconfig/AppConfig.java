package bank.springconfig;

import java.io.InputStreamReader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import bank.app.Application;
import bank.app.dao.AccountDao;
import bank.app.dao.BankDao;
import bank.app.dao.RequestDao;
import bank.app.dao.TransactionDao;
import bank.app.service.AccountService;
import bank.app.service.BankService;
import bank.app.service.BankSession;
import bank.app.service.NewAccountService;
import bank.app.service.RequestService;
import bank.app.service.TransactionService;
import bank.app.ui.UserInterface;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.ConsoleUserInterface;
import bank.app.ui.console.menu.Menu;
import bank.app.ui.console.menu.impl.AccountMenu;
import bank.app.ui.console.menu.impl.MainMenu;
import bank.app.ui.console.uiservice.loginservice.ConsoleLoginService;
import bank.app.ui.console.uiservice.newaccountservice.ConsoleNewAccountService;
import bank.app.ui.console.uiservice.requestservice.ConsoleRequestService;
import bank.app.ui.console.uiservice.transactionservice.ConsoleTransactionService;

@Configuration
@EnableTransactionManagement
public class AppConfig {

    // APPLICATION
    @Bean
    public Application app() {
        System.out.println("Application bean created");
        return new Application();
    }

    @Bean
    public UserInterface ui() {
        System.out.println("UI bean created");
        return new ConsoleUserInterface();
    }

    // CONSOLE UI
    @Bean
    public ConsoleReader input() {
        System.out.println("Input bean created");
        return new ConsoleReader(new InputStreamReader(System.in));
    }

    @Bean
    public Menu<Integer, String> mainMenu() {
        System.out.println("MainMenu bean created");
        return new MainMenu(input());
    }

    @Bean
    Menu<Integer, String> accountMenu() {
        System.out.println("AccountMenu bean created");
        return new AccountMenu();
    }

    @Bean
    public ConsoleNewAccountService consoleNewAccountService() {
        System.out.println("ConsoleNewAccountService bean created");
        return new ConsoleNewAccountService();
    }

    @Bean
    public ConsoleLoginService consoleLoginService() {
        System.out.println("ConsoleLoginService bean created");
        return new ConsoleLoginService();
    }

    @Bean
    public ConsoleRequestService consoleRequestSercice() {
        System.out.println("ConsoleRequestService bean created");
        return new ConsoleRequestService();
    }

    @Bean
    public ConsoleTransactionService consolTransactionService() {
        System.out.println("ConsoleTransactionService bean created");
        return new ConsoleTransactionService();
    }

    // SERVICE
    @Bean
    public NewAccountService newAccountService() {
        System.out.println("NewAccountService bean created");
        return new NewAccountService();
    }

    @Bean
    public BankService bankService() {
        System.out.println("BankService bean created");
        return new BankService();
    }

    @Bean
    public BankSession bankSession() {
        System.out.println("BankSession bean created");
        return new BankSession();
    }

    @Bean
    public AccountService loginService() {
        System.out.println("AccountService bean created");
        return new AccountService();
    }

    @Bean
    public RequestService requestService() {
        System.out.println("RequestService bean created");
        return new RequestService();
    }

    @Bean
    public TransactionService transactionService() {
        System.out.println("TransactionService bean created");
        return new TransactionService();
    }

    // DAO
    @Bean
    public AccountDao accountDao() {
        System.out.println("AccountDao bean crated");
        return new AccountDao();
    }

    @Bean
    public BankDao bankDao() {
        System.out.println("BankDao bean created");
        return new BankDao();
    }

    @Bean
    public RequestDao requestDao() {
        System.out.println("RequestDao bean created");
        return new RequestDao();
    }

    @Bean
    public TransactionDao transactionDao() {
        System.out.println("TransactionDao bean created");
        return new TransactionDao();
    }

    // DB CONNECTION
    @Bean
    public LocalEntityManagerFactoryBean entityManagerFactory() {
        LocalEntityManagerFactoryBean emf = new LocalEntityManagerFactoryBean();
        emf.setPersistenceUnitName("pu");
        System.out.println("EntityManagerFactory Bean created");
        return emf;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager(entityManagerFactory().getObject());
        System.out.println("JpaTransactionManager Bean created");
        return tm;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor petpp() {
        PersistenceExceptionTranslationPostProcessor proc = new PersistenceExceptionTranslationPostProcessor();
        System.out.println("petpp Bean created");
        return proc;
    }
}
