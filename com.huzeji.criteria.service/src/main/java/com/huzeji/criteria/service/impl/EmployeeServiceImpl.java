package com.huzeji.criteria.service.impl;

import com.huzeji.criteria.model.Employee;
import com.huzeji.criteria.repository.EmployeeRepository;
import com.huzeji.criteria.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public List<Employee> getEmployeesByCriteria(String departmentName, Integer minAge, Double minSalary) {
        return employeeRepository.findEmployeesByCriteria(departmentName, minAge, minSalary);
    }

    @Override
    public List<String> findAllEmployeeNamesWithDepartment() {
        return employeeRepository.findAllEmployeeNamesWithDepartment();
    }

    @Override
    public List<Object[]> findTotalSalaryByDepartment() {
        return employeeRepository.findTotalSalaryByDepartment();
    }

    @Override
    public List<Object[]> findAllEmployeesAndTheirProjects() {
        return employeeRepository.findAllEmployeesAndTheirProjects();
    }

    @Override
    public List<Object[]> findAverageRatingByDepartment() {
        return employeeRepository.findAverageRatingByDepartment();
    }

    @Override
    public List<Object[]> findEmployeesWithFrequentAbsencesInDepartment() {
        return employeeRepository.findEmployeesWithFrequentAbsencesInDepartment();
    }

    @Override
    public List<String> findEmployeesInBothProjects() {
        return employeeRepository.findEmployeesInBothProjects();
    }

    @Override
    public List<Object[]> findHighestRatingByDepartment() {
        return employeeRepository.findHighestRatingByDepartment();
    }

    @Override
    public List<String> findEmployeesWithHighPresenceAndRating() {
        return employeeRepository.findEmployeesWithHighPresenceAndRating();
    }

    @Override
    public List<Object[]> findTotalProjectsByDepartment() {
        return employeeRepository.findTotalProjectsByDepartment();
    }

    @Override
    public List<Object[]> findDetailedEmployeeReport() {
        return employeeRepository.findDetailedEmployeeReport();
    }
}
