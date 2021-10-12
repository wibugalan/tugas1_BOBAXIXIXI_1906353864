package apap.tugas.BOBAXIXIXI.repository;

import apap.tugas.BOBAXIXIXI.model.BobaTeaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BobaTeaDB extends JpaRepository<BobaTeaModel, Long> {
    Optional<BobaTeaModel> findById(Long id);
}
