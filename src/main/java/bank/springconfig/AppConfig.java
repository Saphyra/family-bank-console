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
import bank.app.service.NewMemberService;
import bank.app.ui.UserInterface;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.ConsoleUserInterface;
import bank.app.ui.console.NewMemberCreator;
import bank.app.ui.console.menu.Menu;
import bank.app.ui.console.menu.impl.MainMenu;
import bank.app.ui.console.uiservice.ConsoleNewMemberCreator;

@Configuration
@EnableTransactionManagement
public class AppConfig {
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

    @Bean
    public ConsoleReader input() {
        System.out.println("Input bean created");
        return new ConsoleReader(new InputStreamReader(System.in));
    }

    @Bean
    public Menu mainMenu() {
        System.out.println("MainMenu bean created");
        return new MainMenu(input());
    }

    @Bean
    public NewMemberCreator newMemberCreator() {
        System.out.println("NewMemberCreator bean created");
        return new ConsoleNewMemberCreator();
    }

    @Bean
    public NewMemberService newMemberService() {
        System.out.println("NewMemberService bean created");
        return new NewMemberService();
    }

    @Bean
    public AccountDao memberDao() {
        System.out.println("AccountDao bean crated");
        return new AccountDao();
    }

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
