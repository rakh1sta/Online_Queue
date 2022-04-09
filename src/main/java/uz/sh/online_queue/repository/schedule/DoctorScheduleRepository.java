package uz.sh.online_queue.repository.schedule;

import uz.sh.online_queue.entity.schedule.DoctorSchedule;
import uz.sh.online_queue.repository.AbstractRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule,String>, AbstractRepository {
    Optional<DoctorSchedule> findByDoctorIdAndDateAndDeletedFalse(String id, LocalDate date);
    Optional<DoctorSchedule> findDoctorScheduleByIdAndDeletedFalse(String id);

    @Transactional
    @Modifying
    @Query("update DoctorSchedule set deleted = true where id = :id")
    void deleteByIdTIme(String id);
}
