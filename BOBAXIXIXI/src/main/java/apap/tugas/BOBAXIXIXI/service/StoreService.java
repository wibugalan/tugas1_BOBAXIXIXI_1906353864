package apap.tugas.BOBAXIXIXI.service;


import apap.tugas.BOBAXIXIXI.model.StoreModel;

import java.util.List;

public interface StoreService {
    List<StoreModel> getBioskopList();
    void addStore(StoreModel store);
    void generateCode(StoreModel store);
    StoreModel getStoreById(Long id);
    void deleteStore(StoreModel store);
    void updateStore(StoreModel store);
}
