package br.com.interagese;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudApplication {

    public static void main(String[] args) {
//        liquibase();
        SpringApplication.run(CrudApplication.class, args);
    }
    
//    public SpringLiquibase liquibase() {
//    SpringLiquibase liquibase = new SpringLiquibase();
//    liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
//    liquibase.setDataSource(dataSource());
//    return liquibase;
//}
}
