package uz.sh.online_queue.repository.department;

import uz.sh.online_queue.entity.department.Department;
import uz.sh.online_queue.repository.AbstractRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, String>, AbstractRepository {

    Optional<Department> findDepartmentByIdAndDeletedFalse(String id);

    Optional<Department> findDepartmentByIdAndDeletedFalseAndBlockFalse(String id);

    Optional<Department> findDepartmentByNameAndOrganizationIdAndDeletedFalse(String name, String id);


    @Transactional
    @Query(value = "select * from public.department  where deleted = false and block = false and organization_id = :id limit :size offset :page", nativeQuery = true)
    List<Department> findAllByCriteria(int page, int size, String id);

    @Transactional
    @Query(value = "select * from public.department where deleted = false and block and organization_id =:id limit :size offset :page", nativeQuery = true)
    List<Department> findAllByCriteriaAndOrganizationId(int page, int size, String id);


    @Transactional
    @Modifying
    @Query("update Department set deleted = true where id = :id")
    void deleteDepartment(String id);

    @Transactional
    @Modifying
    @Query("update Department set block = true where id = :id")
    void block(String id);

    @Transactional
    @Modifying
    @Query("update Department set block = false where id = :id")
    void unblock(String id);
}
