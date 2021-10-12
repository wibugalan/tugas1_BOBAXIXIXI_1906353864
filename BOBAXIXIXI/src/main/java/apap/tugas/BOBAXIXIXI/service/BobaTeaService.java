package apap.tugas.BOBAXIXIXI.service;

import apap.tugas.BOBAXIXIXI.model.BobaTeaModel;

import java.util.List;

public interface BobaTeaService {
    List<BobaTeaModel> getBobaTeaList();
    void addBoba(BobaTeaModel boba);
    BobaTeaModel getBobaById(Long id);
    void deleteBoba(BobaTeaModel boba);
    void updateBoba(BobaTeaModel boba);
}
