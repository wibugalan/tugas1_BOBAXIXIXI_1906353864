package apap.tugas.BOBAXIXIXI.service;

import apap.tugas.BOBAXIXIXI.model.BobaTeaModel;
import apap.tugas.BOBAXIXIXI.model.StoreModel;
import apap.tugas.BOBAXIXIXI.repository.StoreDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional
public class StoreServiceImpl implements StoreService{
    @Autowired
    StoreDB storeDB;

    @Override
    public List<StoreModel> getStoreList() {
        return storeDB.findAll();
    }

    @Override
    public void addStore(StoreModel store){
        storeDB.save(store);
    }

    @Override
    public void generateCode(StoreModel store) {
        String ans = "SC";
        String temp =  store.getName().substring(0, 3);
        StringBuilder reversed = new StringBuilder();
        reversed.append(temp);
        reversed.reverse();
        ans +=  reversed.toString();
        ans += store.getOpen_hour().toString().substring(0,2);
        int close = Integer.parseInt(store.getClose_hour().toString().substring(0,2));
        if (close<10) {

        }
        else {
            close = Math.floorDiv(close,10);
        }
        ans += String.valueOf(close);
        String Alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(2);
        for (int i = 0; i < 2; i++) {
            int index = (int)(Alpha.length() * Math.random());
            sb.append(Alpha.charAt(index));
        }
        ans += sb.toString();
        ans = ans.toUpperCase(Locale.ROOT);
        store.setStore_code(ans);
    }

    @Override
    public StoreModel getStoreById(Long id) {
        Optional<StoreModel> store = storeDB.findById(id);
        if(store.isPresent()){
            return store.get();
        }
        return null;
    }

    @Override
    public void deleteStore(StoreModel store) {
        storeDB.delete(store);
    }

    @Override
    public void updateStore(StoreModel store) {
        storeDB.save(store);
    }
}
