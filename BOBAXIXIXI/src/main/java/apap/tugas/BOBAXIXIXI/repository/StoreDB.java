package apap.tugas.BOBAXIXIXI.repository;

import apap.tugas.BOBAXIXIXI.model.StoreModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreDB extends JpaRepository<StoreModel, Long> {
    Optional<StoreModel> findById(Long id);
}
