package apap.tugas.BOBAXIXIXI.service;

import apap.tugas.BOBAXIXIXI.model.BobaTeaModel;
import apap.tugas.BOBAXIXIXI.model.StoreBobaTeaModel;

import java.util.List;
import java.util.Optional;

public interface StoreBobaTeaService {
    void addStoreBoba(StoreBobaTeaModel storeBobaTea);
    void generateCode(StoreBobaTeaModel storeBobaTea);
    List<StoreBobaTeaModel> getStoreBobaTeaList();
    List<Long> getStoreByBoba(BobaTeaModel boba);
}
