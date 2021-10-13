package apap.tugas.BOBAXIXIXI.service;

import apap.tugas.BOBAXIXIXI.model.StoreBobaTeaModel;
import apap.tugas.BOBAXIXIXI.repository.StoreBobaTeaDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class StoreBobaTeaServiceImpl implements  StoreBobaTeaService{
    @Autowired
    StoreBobaTeaDB storeBobaTeaDB;

    @Override
    public void addStoreBoba(StoreBobaTeaModel storeBobaTea) {
        storeBobaTeaDB.save(storeBobaTea);
    }

    @Override
    public void generateCode(StoreBobaTeaModel storeBobaTea) {
        String ans = "PC";
        ans += String.format("%03d", storeBobaTea.getStore().getId());
        if (storeBobaTea.getBobaTea().getTopping() == null) {
            ans+="0";
        }
        else if (storeBobaTea.getBobaTea().getTopping() != null) {
            ans += "1";
        }
        ans += String.format("%03d", storeBobaTea.getBobaTea().getId());
        System.out.println(ans);
        storeBobaTea.setProduction_code(ans);
    }
}
