package bank.app.service;

import javax.annotation.Resource;

import org.springframework.transaction.PlatformTransactionManager;

public abstract class AbstractService {
    protected @Resource PlatformTransactionManager txManager;
}
