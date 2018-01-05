package bank.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import bank.app.ui.UserInterface;
import bank.springconfig.AppConfig;

public class Application {
    private @Autowired UserInterface ui;

    public static void main(String[] args) {
        System.out.println("Application started.");
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            Application app = context.getBean(Application.class);
            app.start();
        }
        System.out.println("Application exits.");
    }

    private void start() {
        ui.run();
    }
}
