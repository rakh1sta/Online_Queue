package uz.sh.online_queue.repository.organization;

import uz.sh.online_queue.entity.organization.Organization;
import uz.sh.online_queue.repository.AbstractRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface OrganizationRepository extends JpaRepository<Organization, String>, AbstractRepository {

    Optional<Organization> findOrganizationByIdAndDeletedFalse(String id);
    Optional<Organization> findOrganizationByIdAndDeletedFalseAndBlockedFalse(String id);
    Optional<Organization> findOrganizationByNameAndDistrictAndDeletedFalseAndBlockedFalse(String name,String id);

    @Transactional
    @Query(value = "select * from organization  where deleted = false and blocked = false and region = :region limit :size offset :page", nativeQuery = true)
    List<Organization> findAllByCriteria(int page, int size, String region);

    @Transactional
    @Query(value = "select * from organization where deleted = false and blocked and region =:region limit :size offset :page", nativeQuery = true)
    List<Organization> findAllByCriteriaAndRegion(int page, int size, String region);

    @Transactional
    @Modifying
    @Query("update Organization set blocked = true where id = :id")
    void block(String id);

    @Transactional
    @Modifying
    @Query("update Organization set blocked = false where id = :id")
    void unblock(String id);

    @Transactional
    @Modifying
    @Query("update Organization set deleted = true where id = :id")
    void deleteOrganization(String id);
}
