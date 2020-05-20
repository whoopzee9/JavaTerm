package com.course.repository;

import com.course.entity.DepartmentsEmployees;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface DepartmentsEmployeesRepository extends CrudRepository<DepartmentsEmployees, Long> {
    @Query("select de from DepartmentsEmployees de inner join Departments d on " +
            "d.id = de.departments.id where d.name = :name") //TODO проверить без inner join
    Collection<DepartmentsEmployees> findByDepartmentName(@Param("name") String name);

    @Query("select de from DepartmentsEmployees de where de.departments.id = :id")
    Collection<DepartmentsEmployees> findByDepartmentId(@Param("id") Long id);

    @Query("select de from DepartmentsEmployees de where de.employees.id = :id")
    Collection<DepartmentsEmployees> findByEmployeeId(@Param("id") Long id);

    @Query("select de from DepartmentsEmployees de inner join Employees e on " +
            "e.id = de.employees.id where e.firstName = :first and e.lastName = :last and " +
            "e.patherName = :pather")
    Collection<DepartmentsEmployees> findByEmployeeName(@Param("first") String first, @Param("last") String last, @Param("pather") String pather);

    @Query("delete from DepartmentsEmployees de where de.employees.id = :eId and de.departments.id = :dId")
    void deleteByIds(@Param("eId") Long eId, @Param("dId") Long dId);

    @Query("delete from DepartmentsEmployees de where de.employees.lastName = :last and " +
            "de.employees.firstName = :first and de.employees.patherName = :pather and de.departments.name = :dName")
    void deleteByNames(@Param("last") String last, @Param("first") String first,
                       @Param("pather") String pather, @Param("dName") String name);
}
