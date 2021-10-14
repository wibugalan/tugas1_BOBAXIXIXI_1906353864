package apap.tugas.BOBAXIXIXI.repository;

import apap.tugas.BOBAXIXIXI.model.BobaTeaModel;
import apap.tugas.BOBAXIXIXI.model.ToppingModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ToppingDB extends JpaRepository<ToppingModel, Long> {
    Optional<ToppingModel> findByName(String name);
}
