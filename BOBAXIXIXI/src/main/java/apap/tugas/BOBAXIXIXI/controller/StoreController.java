package apap.tugas.BOBAXIXIXI.controller;

import apap.tugas.BOBAXIXIXI.model.BobaTeaModel;
import apap.tugas.BOBAXIXIXI.model.ManagerModel;
import apap.tugas.BOBAXIXIXI.model.StoreModel;
import apap.tugas.BOBAXIXIXI.model.ToppingModel;
import apap.tugas.BOBAXIXIXI.service.ManagerService;
import apap.tugas.BOBAXIXIXI.service.StoreService;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StoreController {

    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @Qualifier("managerServiceImpl")
    @Autowired
    private ManagerService managerService;

    @GetMapping("/store")
    public String viewAllStore(Model model) {
        List<StoreModel> listStore = storeService.getStoreList();
        model.addAttribute("listStore", listStore);
        return "viewall-store";
    }

    @GetMapping("/store/add")
    public String addStoreForm(Model model) {
        List<ManagerModel> listManager = managerService.getManagerList();
        model.addAttribute("listManager", listManager);
        model.addAttribute("store", new StoreModel());
        return "form-add-store";
    }

    @PostMapping("/store/add")
    public String addStoreSubmit(
            @ModelAttribute StoreModel storeModel,
            Model model
    ) {
        storeService.generateCode(storeModel);
        storeService.addStore(storeModel);
        model.addAttribute("name", storeModel.getName());
        model.addAttribute("kode", storeModel.getStore_code());
        return "add-store";
    }

    @GetMapping("/store/delete/{id}")
    public String deleteStoreForm(
            @PathVariable Long id,
            Model model
    ) {
        StoreModel store = storeService.getStoreById(id);
        model.addAttribute("store", store);
        return "form-delete-store";
    }

    @PostMapping("/store/delete")
    public String deleteStoreSubmit(
            @ModelAttribute StoreModel store,
            Model model
    ) {
        model.addAttribute("name", store.getName());
        storeService.deleteStore(store);
        return "delete-store";
    }

    @GetMapping("/store/update/{id}")
    public String updateStoreForm(
            @PathVariable Long id,
            Model model
    ) {
        StoreModel store = storeService.getStoreById(id);
        List<ManagerModel> listManager = managerService.getManagerList();
        model.addAttribute("listManager", listManager);
        model.addAttribute("store", store);
        return "form-update-store";
    }

    @PostMapping("/store/update")
    public String updateStoreSubmit(
            @ModelAttribute StoreModel store,
            Model model
    ) {
        storeService.generateCode(store);
        storeService.updateStore(store);
        model.addAttribute("name", store.getName());
        model.addAttribute("kode", store.getStore_code());
        return "update-store";
    }
}
