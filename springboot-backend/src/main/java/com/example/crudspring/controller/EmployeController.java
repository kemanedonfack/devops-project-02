package com.example.crudspring.controller;

import com.example.crudspring.exception.ResourceNotFoundException;
import com.example.crudspring.models.Employe;
import com.example.crudspring.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin(origins = "http://react-frontend:3000")
@RequestMapping("/employes")
public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;

    // get all employees
    @GetMapping("/all")
    public List<Employe> getAllEmployees(){
        return employeRepository.findAll();
    }

    // create employee rest api
    @PostMapping("/create")
    public Employe createEmployee(@RequestBody Employe employe) {
        return employeRepository.save(employe);
    }

    // get employee by id rest api
    @GetMapping("/{id}")
    public ResponseEntity<Employe> getEmployeeById(@PathVariable Long id) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        return ResponseEntity.ok(employe);
    }

    // update employee rest api

    @PutMapping("/{id}")
    public ResponseEntity<Employe> updateEmployee(@PathVariable Long id, @RequestBody Employe employeeDetails){
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employe.setFirstName(employeeDetails.getFirstName());
        employe.setLastName(employeeDetails.getLastName());
        employe.setEmailId(employeeDetails.getEmailId());

        Employe updatedEmployee = employeRepository.save(employe);
        return ResponseEntity.ok(updatedEmployee);
    }

    // delete employee rest api
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));

        employeRepository.delete(employe);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
