package apap.tugas.BOBAXIXIXI.service;
import apap.tugas.BOBAXIXIXI.model.BobaTeaModel;
import apap.tugas.BOBAXIXIXI.repository.BobaTeaDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BobaTeaServiceImpl implements BobaTeaService{
    @Autowired
    BobaTeaDB bobaTeaDB;

    @Override
    public List<BobaTeaModel> getBobaTeaList() {
        return bobaTeaDB.findAll();
    }

    @Override
    public void addBoba(BobaTeaModel boba){
        bobaTeaDB.save(boba);
    }

    @Override
    public BobaTeaModel getBobaById(Long id) {
        Optional<BobaTeaModel> boba = bobaTeaDB.findById(id);
        if(boba.isPresent()){
            return boba.get();
        }
        return null;
    }

    @Override
    public BobaTeaModel getBobaByName(String name) {
        Optional<BobaTeaModel> boba = bobaTeaDB.findByName(name);
        if(boba.isPresent()){
            return boba.get();
        }
        return null;
    }

    @Override
    public void deleteBoba(BobaTeaModel boba) {
        bobaTeaDB.delete(boba);
    }

    @Override
    public void updateBoba(BobaTeaModel boba) {
        bobaTeaDB.save(boba);
    }
}
