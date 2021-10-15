package apap.tugas.BOBAXIXIXI.repository;

import apap.tugas.BOBAXIXIXI.model.BobaTeaModel;
import apap.tugas.BOBAXIXIXI.model.StoreBobaTeaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreBobaTeaDB extends JpaRepository<StoreBobaTeaModel, Long> {
//    Optional<StoreBobaTeaModel> findByBobaTea_Id(Long id);
}
