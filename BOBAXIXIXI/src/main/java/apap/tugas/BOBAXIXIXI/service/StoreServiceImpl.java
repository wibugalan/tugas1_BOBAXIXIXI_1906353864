package apap.tugas.BOBAXIXIXI.service;

import apap.tugas.BOBAXIXIXI.model.StoreModel;
import apap.tugas.BOBAXIXIXI.repository.StoreDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class StoreServiceImpl implements StoreService{
    @Autowired
    StoreDB storeDB;

    @Override
    public List<StoreModel> getBioskopList() {
        return storeDB.findAll();
    }
}
