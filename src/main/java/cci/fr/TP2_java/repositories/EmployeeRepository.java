package cci.fr.TP2_java.repositories;

import cci.fr.TP2_java.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}