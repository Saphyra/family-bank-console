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
import bank.app.dao.RequestDao;
import bank.app.service.AccountService;
import bank.app.service.BankSession;
import bank.app.service.NewAccountService;
import bank.app.service.RequestService;
import bank.app.ui.UserInterface;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.ConsoleUserInterface;
import bank.app.ui.console.menu.Menu;
import bank.app.ui.console.menu.impl.AccountMenu;
import bank.app.ui.console.menu.impl.MainMenu;
import bank.app.ui.console.uiservice.ConsoleLoginService;
import bank.app.ui.console.uiservice.ConsoleNewMemberService;
import bank.app.ui.console.uiservice.ConsoleRequestService;

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
    public Menu<String, String> mainMenu() {
        System.out.println("MainMenu bean created");
        return new MainMenu(input());
    }

    @Bean
    Menu<String, String> accountMenu() {
        System.out.println("AccountMenu bean created");
        return new AccountMenu();
    }

    @Bean
    public ConsoleNewMemberService consoleNewMemberService() {
        System.out.println("ConsoleNewMemberService bean created");
        return new ConsoleNewMemberService();
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

    // SERVICE
    @Bean
    public NewAccountService newMemberService() {
        System.out.println("NewMemberService bean created");
        return new NewAccountService();
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

    // DAO
    @Bean
    public AccountDao memberDao() {
        System.out.println("AccountDao bean crated");
        return new AccountDao();
    }

    @Bean
    public RequestDao requestDao() {
        System.out.println("RequestDao bean created");
        return new RequestDao();
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
