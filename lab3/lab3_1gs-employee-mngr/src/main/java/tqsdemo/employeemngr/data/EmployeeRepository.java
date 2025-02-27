package tqsdemo.employeemngr.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * in a production application, you would likely more data access methods
 * specially methods that my have a complex query expressions attached
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByName(String name);

    List<Employee> findAll();

    /**
     * Custom query to find employees whose email ends with the given domain.
     * Example usage:
     * findEmplyeedByOrganizationDomain("ua.pt")
     * would return all employees with emails like 'someone@ua.pt'.
     */
    // JQL(Java Query Language) query NOT SQL
    // Se não tiver a dependência no projecto, pode não ser possível ter um BD em memória
    @Query("SELECT e FROM Employee e WHERE e.email LIKE concat('%', :domain)")
    List<Employee> findEmplyeedByOrganizationDomain(@Param("domain") String domain);


}
