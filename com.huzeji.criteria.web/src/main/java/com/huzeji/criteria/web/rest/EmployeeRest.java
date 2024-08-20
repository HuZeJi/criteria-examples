package com.huzeji.criteria.web.rest;

import com.huzeji.criteria.model.Employee;
import com.huzeji.criteria.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeRest {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> getEmployees(
            @RequestParam String department,
            @RequestParam int minAge,
            @RequestParam double minSalary) {
        return employeeService.getEmployeesByCriteria(department, minAge, minSalary);
    }

    @GetMapping("/example-1/names-with-department")
    public List<String> findAllEmployeeNamesWithDepartment() {
        return employeeService.findAllEmployeeNamesWithDepartment();
    }

    @GetMapping("/example-2/total-salary-by-department")
    public List<Object[]> findTotalSalaryByDepartment() {
        return employeeService.findTotalSalaryByDepartment();
    }

    @GetMapping("/example-3/employees-and-their-projects")
    public List<Object[]> findAllEmployeesAndTheirProjects() {
        return employeeService.findAllEmployeesAndTheirProjects();
    }

    @GetMapping("/example-4/average-rating-by-department")
    public List<Object[]> findAverageRatingByDepartment() {
        return employeeService.findAverageRatingByDepartment();
    }

    @GetMapping("/example-5/frequent-absences-by-department")
    public List<Object[]> findEmployeesWithFrequentAbsencesInDepartment() {
        return employeeService.findEmployeesWithFrequentAbsencesInDepartment();
    }

    @GetMapping("/example-6/employees-in-both-projects")
    public List<String> findEmployeesInBothProjects() {
        return employeeService.findEmployeesInBothProjects();
    }

    @GetMapping("/example-7/highest-rating-by-department")
    public List<Object[]> findHighestRatingByDepartment() {
        return employeeService.findHighestRatingByDepartment();
    }

    @GetMapping("/example-8/high-presence-and-rating")
    public List<String> findEmployeesWithHighPresenceAndRating() {
        return employeeService.findEmployeesWithHighPresenceAndRating();
    }

    @GetMapping("/example-9/total-projects-by-department")
    public List<Object[]> findTotalProjectsByDepartment() {
        return employeeService.findTotalProjectsByDepartment();
    }

    @GetMapping("/example-10/detailed-employee-report")
    public List<Object[]> findDetailedEmployeeReport() {
        return employeeService.findDetailedEmployeeReport();
    }
}
