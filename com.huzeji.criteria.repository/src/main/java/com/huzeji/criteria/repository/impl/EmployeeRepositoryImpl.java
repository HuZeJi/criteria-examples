package com.huzeji.criteria.repository.impl;

import com.huzeji.criteria.model.*;
import com.huzeji.criteria.repository.EmployeeRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
class EmployeeRepositoryImpl implements EmployeeRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<Employee> findEmployeesByCriteria(String departmentName, Integer minAge, Double minSalary) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> query = cb.createQuery(Employee.class);
        Root<Employee> root = query.from(Employee.class);

        Predicate departmentPredicate = cb.equal(root.get("department"), departmentName);
        Predicate agePredicate = cb.greaterThanOrEqualTo(root.get("age"), minAge);
        Predicate salaryPredicate = cb.greaterThanOrEqualTo(root.get("salary"), minSalary);

        query.select(root).where(cb.and(departmentPredicate, agePredicate, salaryPredicate));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<String> findAllEmployeeNamesWithDepartment() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> query = cb.createQuery(String.class);
        Root<Employee> employee = query.from(Employee.class);
        Join<Employee, Department> department = employee.join("department");

        query.select(cb.concat(cb.concat(employee.get("name"), " - "), department.get("name")));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Object[]> findTotalSalaryByDepartment() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Employee> employee = query.from(Employee.class);
        Join<Employee, Department> department = employee.join("department");

        query.multiselect(department.get("name"), cb.sum(employee.get("salary")))
                .groupBy(department.get("name"));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Object[]> findAllEmployeesAndTheirProjects() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Employee> employee = query.from(Employee.class);
        Join<Employee, EmployeeProject> employeeProject = employee.join("projects");
        Join<EmployeeProject, Project> project = employeeProject.join("project");

        query.multiselect(employee.get("name"),
                        cb.function("GROUP_CONCAT", String.class, project.get("name")))
                .groupBy(employee.get("name"));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Object[]> findAverageRatingByDepartment() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Employee> employee = query.from(Employee.class);
        Join<Employee, PerformanceReview> performanceReview = employee.join("performanceReviews");
        Join<Employee, Department> department = employee.join("department");

        query.multiselect(department.get("name"), cb.avg(performanceReview.get("rating")))
                .groupBy(department.get("name"));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Object[]> findEmployeesWithFrequentAbsencesInDepartment() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Employee> employee = query.from(Employee.class);
        Join<Employee, Department> department = employee.join("department");
        Join<Employee, Attendance> attendance = employee.join("attendances");

        Predicate absentStatus = cb.equal(attendance.get("status"), Attendance.AttendanceStatus.Absent);
        Predicate withinLastMonth = cb.between(attendance.get("date"),
                cb.function("DATE_SUB", Date.class, cb.currentDate(), cb.literal(1)),
                cb.currentDate());

        query.multiselect(department.get("name"), cb.countDistinct(employee.get("id")))
                .where(cb.and(absentStatus, withinLastMonth))
                .groupBy(department.get("name"))
                .having(cb.gt(cb.count(attendance), 3));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<String> findEmployeesInBothProjects() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> query = cb.createQuery(String.class);
        Root<Employee> employee = query.from(Employee.class);
        Join<Employee, EmployeeProject> employeeProject = employee.join("projects");
        Join<EmployeeProject, Project> project1 = employeeProject.join("project");

        Subquery<Long> subquery = query.subquery(Long.class);
        Root<EmployeeProject> subRoot = subquery.from(EmployeeProject.class);
        Join<EmployeeProject, Project> project2 = subRoot.join("project");
        subquery.select(subRoot.get("employee").get("id"))
                .where(cb.equal(project2.get("name"), "Project Beta"));

        query.select(employee.get("name"))
                .where(cb.and(cb.equal(project1.get("name"), "Project Alpha"),
                        cb.in(employee.get("id")).value(subquery)))
                .groupBy(employee.get("name"));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Object[]> findHighestRatingByDepartment() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Employee> employee = query.from(Employee.class);
        Join<Employee, PerformanceReview> performanceReview = employee.join("performanceReviews");
        Join<Employee, Department> department = employee.join("department");

        query.multiselect(department.get("name"), cb.max(performanceReview.get("rating")))
                .groupBy(department.get("name"));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<String> findEmployeesWithHighPresenceAndRating() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<String> query = cb.createQuery(String.class);
        Root<Employee> employee = query.from(Employee.class);
        Join<Employee, Attendance> attendance = employee.join("attendances");
        Join<Employee, PerformanceReview> performanceReview = employee.join("performanceReviews");

        Predicate presentStatus = cb.equal(attendance.get("status"), Attendance.AttendanceStatus.Present);
        Predicate withinLastTwoMonths = cb.between(attendance.get("date"),
                cb.function("DATE_SUB", Date.class, cb.currentDate(), cb.literal(2)),
                cb.currentDate());

        query.select(employee.get("name"))
                .where(cb.and(presentStatus, withinLastTwoMonths))
                .groupBy(employee.get("name"))
                .having(cb.and(cb.ge(cb.count(attendance), 20),
                        cb.gt(cb.avg(performanceReview.get("rating")), 4)));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Object[]> findTotalProjectsByDepartment() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Employee> employee = query.from(Employee.class);
        Join<Employee, Department> department = employee.join("department");
        Join<Employee, EmployeeProject> employeeProject = employee.join("projects");

        query.multiselect(department.get("name"), cb.countDistinct(employeeProject.get("project").get("id")))
                .groupBy(department.get("name"));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Object[]> findDetailedEmployeeReport() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);
        Root<Employee> employee = query.from(Employee.class);
        Join<Employee, Department> department = employee.join("department");
        Join<Employee, EmployeeProject> employeeProject = employee.join("projects", JoinType.LEFT);
        Join<EmployeeProject, Project> project = employeeProject.join("project", JoinType.LEFT);
        Join<Employee, PerformanceReview> performanceReview = employee.join("performanceReviews", JoinType.LEFT);
        Join<Employee, Attendance> attendance = employee.join("attendances", JoinType.LEFT);

        Predicate reviewedInLastSixMonths = cb.between(performanceReview.get("reviewDate"),
                cb.function("DATE_SUB", Date.class, cb.currentDate(), cb.literal(6)),
                cb.currentDate());
        Predicate presentStatusInLastMonth = cb.equal(attendance.get("status"), Attendance.AttendanceStatus.Present);
        Predicate presentInLastMonth = cb.between(attendance.get("date"),
                cb.function("DATE_SUB", Date.class, cb.currentDate(), cb.literal(1)),
                cb.currentDate());

        query.multiselect(employee.get("name"),
                        department.get("name"),
                        employee.get("salary"),
                        cb.countDistinct(project.get("id")),
                        cb.avg(performanceReview.get("rating")),
                        cb.countDistinct(attendance.get("date")))
                .where(cb.and(reviewedInLastSixMonths, presentStatusInLastMonth, presentInLastMonth))
                .groupBy(employee.get("name"), department.get("name"), employee.get("salary"));

        return entityManager.createQuery(query).getResultList();
    }
}
