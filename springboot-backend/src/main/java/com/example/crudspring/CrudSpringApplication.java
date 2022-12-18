package com.example.crudspring;

import com.example.crudspring.models.Employe;
import com.example.crudspring.services.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrudSpringApplication implements CommandLineRunner {

    @Autowired
    private EmployeService employeService;

    public static void main(String[] args) {
        SpringApplication.run(CrudSpringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Employe employe = new Employe(1L, "kemane", "Donfack","kemane@gmail.com");
        Employe employe1 = new Employe(1L, "ivan", "Nafack","nafack@gmail.com");

        employeService.createEmployee(employe);
        employeService.createEmployee(employe1);
    }

}
