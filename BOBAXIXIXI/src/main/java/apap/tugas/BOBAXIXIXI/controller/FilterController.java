package apap.tugas.BOBAXIXIXI.controller;

import apap.tugas.BOBAXIXIXI.model.BobaTeaModel;
import apap.tugas.BOBAXIXIXI.model.ManagerModel;
import apap.tugas.BOBAXIXIXI.model.StoreModel;
import apap.tugas.BOBAXIXIXI.service.BobaTeaService;
import apap.tugas.BOBAXIXIXI.service.ManagerService;
import apap.tugas.BOBAXIXIXI.service.StoreBobaTeaService;
import apap.tugas.BOBAXIXIXI.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class FilterController {
    @Qualifier("bobaTeaServiceImpl")
    @Autowired
    private BobaTeaService bobaTeaService;

    @Qualifier("storeBobaTeaServiceImpl")
    @Autowired
    private StoreBobaTeaService storeBobaTeaService;

    @Qualifier("managerServiceImpl")
    @Autowired
    private ManagerService managerService;

    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @GetMapping("/filter/manager")
    public String filter(
            @RequestParam(value = "nameBoba", required = false) String namaBoba,
            Model model
    ) {
        System.out.println(namaBoba);
        Set<String> setNama = new HashSet<String>();
        List<BobaTeaModel> listNamaBoba = bobaTeaService.getBobaTeaList();
        for (BobaTeaModel i: listNamaBoba) {
            setNama.add(i.getName());
        }
        model.addAttribute("listBoba", setNama);
        List<StoreModel> listStoreUsed = new ArrayList<StoreModel>();
        List<BobaTeaModel> listBobaUsed = new ArrayList<BobaTeaModel>();
        List<BobaTeaModel> listBoba = bobaTeaService.getBobaTeaList();
        for (BobaTeaModel l : listBoba) {
            if (l.getName().equals(namaBoba)) {
                listBobaUsed.add(l);
            }
        }
        Set<Long> listIdStore = new HashSet<Long>();
        for (BobaTeaModel b: listBobaUsed) {
            if (storeBobaTeaService.getStoreByBoba(b).size() != 0) {
                List<Long> listTemp =  storeBobaTeaService.getStoreByBoba(b);
                listIdStore.addAll(listTemp);

            }

        }
        for (Long i : listIdStore) {
            listStoreUsed.add(storeService.getStoreById(i));
        }
        List<ManagerModel> listUsedManager = new ArrayList<>();
        for (StoreModel str: listStoreUsed) {
            listUsedManager.add(str.getManager());
        }
        model.addAttribute("listManager", listUsedManager);
        return "filter";
    }
}
