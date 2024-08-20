package com.huzeji.criteria.service;

import com.huzeji.criteria.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployeesByCriteria(String departmentName, Integer minAge, Double minSalary);

    List<String> findAllEmployeeNamesWithDepartment();

    List<Object[]> findTotalSalaryByDepartment();

    List<Object[]> findAllEmployeesAndTheirProjects();

    List<Object[]> findAverageRatingByDepartment();

    List<Object[]> findEmployeesWithFrequentAbsencesInDepartment();

    List<String> findEmployeesInBothProjects();

    List<Object[]> findHighestRatingByDepartment();

    List<String> findEmployeesWithHighPresenceAndRating();

    List<Object[]> findTotalProjectsByDepartment();

    List<Object[]> findDetailedEmployeeReport();
}
