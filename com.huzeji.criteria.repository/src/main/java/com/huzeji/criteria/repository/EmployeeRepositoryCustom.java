package com.huzeji.criteria.repository;

import com.huzeji.criteria.model.Employee;

import java.util.List;

public interface EmployeeRepositoryCustom {
    List<Employee> findEmployeesByCriteria( String departmentName, Integer minAge, Double minSalary);
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
