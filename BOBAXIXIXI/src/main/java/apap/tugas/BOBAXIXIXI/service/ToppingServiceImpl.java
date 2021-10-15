package apap.tugas.BOBAXIXIXI.service;

import apap.tugas.BOBAXIXIXI.model.BobaTeaModel;
import apap.tugas.BOBAXIXIXI.model.StoreModel;
import apap.tugas.BOBAXIXIXI.model.ToppingModel;
import apap.tugas.BOBAXIXIXI.repository.StoreDB;
import apap.tugas.BOBAXIXIXI.repository.ToppingDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ToppingServiceImpl implements ToppingService{
    @Autowired
    ToppingDB toppingDB;

    @Override
    public List<ToppingModel> getToppingList() {
        return toppingDB.findAll();
    }

    @Override
    public ToppingModel getToppingByName(String name) {
        Optional<ToppingModel> topping = toppingDB.findByName(name);
        if(topping.isPresent()){
            return topping.get();
        }
        return null;
    }
}
