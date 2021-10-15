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

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        List<ManagerModel> listManagerNotUsed = new ArrayList<>();
        for (ManagerModel m: listManager) {
            if (m.getStore()== null) {
                listManagerNotUsed.add(m);
            }
        }
        model.addAttribute("listManager", listManagerNotUsed);
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
            Model model,
            @RequestParam(value="jamBuka")String buka,
            @RequestParam(value="jamTutup")String tutup
    ) {
        List<StoreBobaTeaModel> listSB = storeBobaTeaService.getStoreBobaTeaList();
        boolean temp = false;
        for (StoreBobaTeaModel l : listSB) {
            if(l.getStore().getId() == store.getId()) {
                temp = true;
                break;
            }
        }
        if (storeService.cekBuka(buka, tutup)) {
            return "delete-store-buka";
        }
        else if (temp) {
            return "delete-store-boba";
        }
        else {
            model.addAttribute("name", store.getName());
            storeService.deleteStore(store);
            return "delete-store";
        }
    }

    @GetMapping("/store/update/{id}")
    public String updateStoreForm(
            @PathVariable Long id,
            Model model
    ) {
        StoreModel store = storeService.getStoreById(id);
        List<ManagerModel> listManager = managerService.getManagerList();
        List<ManagerModel> listManagerNotUsed = new ArrayList<>();
        listManagerNotUsed.add(store.getManager());
        for (ManagerModel m: listManager) {
            if (m.getStore()== null) {
                listManagerNotUsed.add(m);
            }
        }
        model.addAttribute("listManager", listManagerNotUsed);
        model.addAttribute("store", store);

        return "form-update-store";
    }

    @PostMapping("/store/update")
    public String updateStoreSubmit(
            @ModelAttribute StoreModel store,
            Model model,
            @RequestParam(value="jamBuka")String buka,
            @RequestParam(value="jamTutup")String tutup
    ) {
        if (storeService.cekBuka(buka,tutup)) {
            return "update-store-buka";
        }
        else {
            model.addAttribute("name", store.getName());
            storeService.generateCode(store);
            storeService.updateStore(store);
            model.addAttribute("kode", store.getStore_code());
            return "update-store";
        }
    }

    @GetMapping("/store/{id}/assign-boba")
    public String assignStoreForm(
            @PathVariable Long id,
            Model model
    ) {
        StoreModel store = storeService.getStoreById(id);
        List<BobaTeaModel> listBoba = bobaTeaService.getBobaTeaList();
        List<Boolean> list= new ArrayList<Boolean>(Arrays.asList(new Boolean[listBoba.size()]));
        Collections.fill(list, Boolean.FALSE);

        List<BobaTeaModel> listUsedByBoba = new ArrayList<>();
        for(StoreBobaTeaModel s: store.getListStoreBobaTea()) {
            if (s.getStore().getId() == store.getId()) {
                listUsedByBoba.add(s.getBobaTea());
            }
        }

        for (int i = 0; i < listBoba.size(); i++) {
            for (BobaTeaModel s: listUsedByBoba) {
                if (listBoba.get(i).getId() == s.getId()) {
                    list.set(i, true);
                }
            }
        }
        model.addAttribute("listChecker", list);
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
            @RequestParam(value="boba",required = false)Long[] bobaList
    ) {
        StoreModel store = storeService.getStoreById(id);

        List<StoreBobaTeaModel> tempList = new ArrayList<>();
        for (StoreBobaTeaModel s: storeBobaTeaService.getStoreBobaTeaList()) {
            if (s.getStore().getId() == id) {
                tempList.add(s);
            }
        }
        int loop = tempList.size();
        for (int i = 0; i < loop; i++) {
            storeBobaTeaService.deleteStoreBobaTeaService(tempList.get(i));
        }
        List<BobaTeaModel> listBoba= new ArrayList<BobaTeaModel>();
        if (bobaList != null) {
            storeBoba.setStore(store);
            for (Long i : bobaList) {
                StoreBobaTeaModel temp = new StoreBobaTeaModel();
                temp.setStore(store);
                temp.setBobaTea(bobaTeaService.getBobaById(i));
                storeBobaTeaService.generateCode(temp);
                storeBobaTeaService.addStoreBoba(temp);
            }
            for (StoreBobaTeaModel s : store.getListStoreBobaTea()) {
                listBoba.add(s.getBobaTea());
            }
        }
        model.addAttribute("listBoba", listBoba);
        model.addAttribute("store", store);
        return "assigned-boba";
    }
}
