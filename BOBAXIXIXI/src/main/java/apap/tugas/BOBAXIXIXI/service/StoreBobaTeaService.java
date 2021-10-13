package apap.tugas.BOBAXIXIXI.service;

import apap.tugas.BOBAXIXIXI.model.BobaTeaModel;
import apap.tugas.BOBAXIXIXI.model.StoreBobaTeaModel;

public interface StoreBobaTeaService {
    void addStoreBoba(StoreBobaTeaModel storeBobaTea);
    void generateCode(StoreBobaTeaModel storeBobaTea);
}
