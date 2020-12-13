package com.javasampleapproach.springrest.mysql.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.javasampleapproach.springrest.mysql.model.Employee;
import com.javasampleapproach.springrest.mysql.repo.EmployeeRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/")
public class EmployeeController {
	

	@Autowired
	EmployeeRepository repository;

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		System.out.println("Get all Customers...");

		List<Employee> employees = new ArrayList<>();
		repository.findAll().forEach(employees::add);

		return employees;
	}

	@PostMapping(value = "/employee/create")
	public Employee postEmployee(@RequestBody Employee employee) {

		Employee _employee = repository.save(new Employee());
		return _employee;
	}

	@DeleteMapping("/empoyees/{emp_id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("emp_id") long emp_id) {
		System.out.println("Delete employee with ID = " + emp_id + "...");

		repository.deleteById(emp_id);

		return new ResponseEntity<>("Employee has been deleted!", HttpStatus.OK);
	}

	@DeleteMapping("/employees/delete")
	public ResponseEntity<String> deleteAllEmployees() {
		System.out.println("Delete All Employees...");

		repository.deleteAll();

		return new ResponseEntity<>("All employees have been deleted!", HttpStatus.OK);
	}

	@GetMapping(value = "employess/department/{department}")
	public List<Employee> findByDepartment(@PathVariable String department) {

		List<Employee> employees = repository.findByDepartment(department);
		return employees;
	}

	@PutMapping("/employees/{emp_id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("emp_id") long emp_id, @RequestBody Employee employee) {
		System.out.println("Update Employee with ID = " + emp_id + "...");

		Optional<Employee> employeeData = repository.findById(emp_id);

		if (employeeData.isPresent()) {
			Employee _employee = employeeData.get();
            _employee.setFirstname(employee.getFirstname());
            _employee.setLastname(employee.getLastname());
            _employee.setDepartment(employee.getDepartment());
            _employee.setEmail_id(employee.getEmail_id());
		
			return new ResponseEntity<>(repository.save(_employee), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		}
	}
}
