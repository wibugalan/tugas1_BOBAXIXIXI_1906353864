package apap.tugas.BOBAXIXIXI.service;

import apap.tugas.BOBAXIXIXI.model.BobaTeaModel;
import apap.tugas.BOBAXIXIXI.model.StoreBobaTeaModel;
import apap.tugas.BOBAXIXIXI.repository.StoreBobaTeaDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<StoreBobaTeaModel> getStoreBobaTeaList() {
        return storeBobaTeaDB.findAll();
    }

    @Override
    public List<Long> getStoreByBoba(BobaTeaModel boba) {
//        Optional<StoreBobaTeaModel> opt =  storeBobaTeaDB.findByBobaTea_Id(boba.getId());
//        List<StoreBobaTeaModel> listStoreBoba = opt.stream().collect(Collectors.toList());
        List<StoreBobaTeaModel> listStoreBoba = new ArrayList<>();

        for (StoreBobaTeaModel s: getStoreBobaTeaList()) {
            if (s.getBobaTea().getId() == boba.getId()) {
                listStoreBoba.add(s);
            }
        }

        List<Long> out = new ArrayList<>();
        for (StoreBobaTeaModel s: listStoreBoba) {
            out.add(s.getStore().getId());
        }
        return out;

    }

    @Override
    public void deleteStoreBobaTeaService(StoreBobaTeaModel storeBobaTea) {
        storeBobaTeaDB.delete(storeBobaTea);
    }
}
