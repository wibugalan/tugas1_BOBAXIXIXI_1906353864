package apap.tugas.BOBAXIXIXI.controller;

import apap.tugas.BOBAXIXIXI.model.*;
import apap.tugas.BOBAXIXIXI.service.BobaTeaService;
import apap.tugas.BOBAXIXIXI.service.ManagerService;
import apap.tugas.BOBAXIXIXI.service.StoreBobaTeaService;
import apap.tugas.BOBAXIXIXI.service.StoreService;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class StoreController {

    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @Qualifier("bobaTeaServiceImpl")
    @Autowired
    private BobaTeaService bobaTeaService;

    @Qualifier("managerServiceImpl")
    @Autowired
    private ManagerService managerService;

    @Qualifier("storeBobaTeaServiceImpl")
    @Autowired
    private StoreBobaTeaService storeBobaTeaService;

    @GetMapping("/store")
    public String viewAllStore(Model model) {
        List<StoreModel> listStore = storeService.getStoreList();
        model.addAttribute("listStore", listStore);
        return "viewall-store";
    }

    @GetMapping("/store/{id}")
    public String viewStore(
            @PathVariable Long id,
            Model model
    ) {
        StoreModel store = storeService.getStoreById(id);
        List<BobaTeaModel> listBoba= new ArrayList<BobaTeaModel>();
        for (StoreBobaTeaModel s : store.getListStoreBobaTea()) {
            listBoba.add(s.getBobaTea());
        }
        model.addAttribute("listBoba", listBoba);
        model.addAttribute("store", store);
        return "view-store";
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

    @GetMapping("/store/{id}/assign-boba")
    public String assignStoreForm(
            @PathVariable Long id,
            Model model
    ) {
        StoreModel store = storeService.getStoreById(id);
        List<BobaTeaModel> listBoba = bobaTeaService.getBobaTeaList();
        model.addAttribute("storeBoba", new StoreBobaTeaModel());
        model.addAttribute("store", store);
        model.addAttribute("listBoba", listBoba);
        return "form-assign-boba";
    }

    @PostMapping("/store/{id}/assign-boba")
    public String assignStoreSubmit(
            @ModelAttribute StoreBobaTeaModel storeBoba,
            @PathVariable Long id,
            Model model,
            @RequestParam(value="boba")Long[] bobaList
    ) {
        StoreModel store = storeService.getStoreById(id);
        storeBoba.setStore(store);
        for(Long i: bobaList) {
            StoreBobaTeaModel temp = new StoreBobaTeaModel();
            temp.setStore(store);
            temp.setBobaTea(bobaTeaService.getBobaById(i));
            storeBobaTeaService.generateCode(temp);
            storeBobaTeaService.addStoreBoba(temp);
        }
        return "add-store";
    }
}
