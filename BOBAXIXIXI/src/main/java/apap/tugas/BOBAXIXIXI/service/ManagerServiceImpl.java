package apap.tugas.BOBAXIXIXI.service;

import apap.tugas.BOBAXIXIXI.model.ManagerModel;
import apap.tugas.BOBAXIXIXI.repository.ManagerDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {
    @Autowired
    ManagerDB managerDB;

    @Override
    public List<ManagerModel> getManagerList() {
        return managerDB.findAll();
    }
}
