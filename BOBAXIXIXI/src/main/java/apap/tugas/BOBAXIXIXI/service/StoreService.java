package apap.tugas.BOBAXIXIXI.service;


import apap.tugas.BOBAXIXIXI.model.StoreModel;

import java.time.LocalTime;
import java.util.List;

public interface StoreService {
    List<StoreModel> getStoreList();
    void addStore(StoreModel store);
    void generateCode(StoreModel store);
    StoreModel getStoreById(Long id);
    void deleteStore(StoreModel store);
    void updateStore(StoreModel store);
    boolean cekBuka(String bukaJ, String tutupJ);
}
