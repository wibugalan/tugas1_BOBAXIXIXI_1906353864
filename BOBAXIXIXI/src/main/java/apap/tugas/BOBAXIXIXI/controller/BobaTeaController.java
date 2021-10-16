package apap.tugas.BOBAXIXIXI.controller;

import apap.tugas.BOBAXIXIXI.model.BobaTeaModel;
import apap.tugas.BOBAXIXIXI.model.StoreBobaTeaModel;
import apap.tugas.BOBAXIXIXI.model.StoreModel;
import apap.tugas.BOBAXIXIXI.model.ToppingModel;
import apap.tugas.BOBAXIXIXI.service.BobaTeaService;
import apap.tugas.BOBAXIXIXI.service.StoreBobaTeaService;
import apap.tugas.BOBAXIXIXI.service.StoreService;
import apap.tugas.BOBAXIXIXI.service.ToppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
public class BobaTeaController {

    @Qualifier("bobaTeaServiceImpl")
    @Autowired
    private BobaTeaService bobaTeaService;

    @Qualifier("toppingServiceImpl")
    @Autowired
    private ToppingService toppingService;

    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @Qualifier("storeBobaTeaServiceImpl")
    @Autowired
    private StoreBobaTeaService storeBobaTeaService;

    @GetMapping("/boba")
    public String viewAllBoba(Model model) {
        List<BobaTeaModel> listBobaTea = bobaTeaService.getBobaTeaList();
        model.addAttribute("listBobaTea", listBobaTea);
        return "viewall-boba";
    }

    @GetMapping("/boba/add")
    public String addBobaForm(Model model) {
        List<ToppingModel> listTopping =  toppingService.getToppingList();
        model.addAttribute("listTopping", listTopping);
        model.addAttribute("boba", new BobaTeaModel());
        return "form-add-boba";
    }

    @PostMapping("/boba/add")
    public String addBobaSubmit(
            @ModelAttribute BobaTeaModel bobaTea,
            Model model
    ) {
        bobaTeaService.addBoba(bobaTea);
        model.addAttribute("name", bobaTea.getName());
        return "add-boba";
    }

    @GetMapping("/boba/delete/{id}")
    public String deleteBobaForm(
            @PathVariable Long id,
            Model model
    ) {
        BobaTeaModel boba = bobaTeaService.getBobaById(id);
        model.addAttribute("boba", boba);
        return "form-delete-boba";
    }

    @PostMapping("/boba/delete")
    public String deleteBobaSubmit(
            @ModelAttribute BobaTeaModel boba,
            Model model
    ) {
        List<StoreBobaTeaModel> listSB = storeBobaTeaService.getStoreBobaTeaList();
        boolean temp = false;
        for (StoreBobaTeaModel l : listSB) {
            if(l.getBobaTea().getId() == boba.getId()) {
                temp = true;
                break;
            }
        }
        if (temp) {
            return "delete-boba-assigned";
        }
        else {
            model.addAttribute("name", boba.getName());
            bobaTeaService.deleteBoba(boba);
            return "delete-boba";
        }
    }

    @GetMapping("/boba/update/{id}")
    public String updateBobaForm(
            @PathVariable Long id,
            Model model
    ) {
        BobaTeaModel boba = bobaTeaService.getBobaById(id);
        List<ToppingModel> listTopping =  toppingService.getToppingList();
        model.addAttribute("hargaTopping", boba.getTopping().getPrice());
        model.addAttribute("listTopping", listTopping);
        model.addAttribute("boba", boba);
        return "form-update-boba";
    }

    @PostMapping("/boba/update")
    public String updateBobaSubmit(
            @ModelAttribute BobaTeaModel boba,
            Model model,
            @RequestParam(value="toppingPrice")Integer toppingPrice
    ) {
        List<StoreBobaTeaModel> listSB = storeBobaTeaService.getStoreBobaTeaList();
        boolean temp = false;
        for (StoreBobaTeaModel l : listSB) {
            if(l.getBobaTea().getId() == boba.getId()) {
                temp = true;
                break;
            }
        }
        if (temp) {
            return "update-boba-assigned";
        }
        else {
            bobaTeaService.updateBoba(boba, toppingPrice);
            model.addAttribute("name", boba.getName());
            return "update-boba";
        }
    }

    @GetMapping("/boba/{id}/assign-store")
    public String assignBobaForm(
            @PathVariable Long id,
            Model model
    ) {
        BobaTeaModel boba = bobaTeaService.getBobaById(id);
        List<StoreModel> listStore = storeService.getStoreList();
        List<Boolean> list= new ArrayList<Boolean>(Arrays.asList(new Boolean[listStore.size()]));
        Collections.fill(list, Boolean.FALSE);

        List<StoreModel> listUsedByBoba = new ArrayList<>();
        for(StoreBobaTeaModel s: boba.getListStoreBobaTea()) {
            if (s.getBobaTea().getId() == boba.getId()) {
                listUsedByBoba.add(s.getStore());
            }
        }

        for (int i = 0; i < listStore.size(); i++) {
            for (StoreModel s: listUsedByBoba) {
                if (listStore.get(i).getId() == s.getId()) {
                    list.set(i, true);
                }
            }
        }
        model.addAttribute("listChecker", list);
        model.addAttribute("storeBoba", new StoreBobaTeaModel());
        model.addAttribute("boba", boba);
        model.addAttribute("listStore", listStore);

        return "form-assign-store";
    }

    @PostMapping("/boba/{id}/assign-store")
    public String assignBobaSubmit(
            @ModelAttribute StoreBobaTeaModel storeBoba,
            @PathVariable Long id,
            Model model,
            @RequestParam(value="store",required = false)Long[] storeList
    ) {
        BobaTeaModel boba = bobaTeaService.getBobaById(id);

        List<StoreBobaTeaModel> tempList = new ArrayList<>();
        for (StoreBobaTeaModel s: storeBobaTeaService.getStoreBobaTeaList()) {
            if (s.getBobaTea().getId() == id) {
                tempList.add(s);
            }
        }
        int loop = tempList.size();
        for (int i = 0; i < loop; i++) {
            storeBobaTeaService.deleteStoreBobaTeaService(tempList.get(i));
        }
        List<StoreModel> listStore = new ArrayList<>();
        if( storeList != null) {
            storeBoba.setBobaTea(boba);
            for (Long i : storeList) {
                StoreBobaTeaModel temp = new StoreBobaTeaModel();
                temp.setBobaTea(boba);
                temp.setStore(storeService.getStoreById(i));
                storeBobaTeaService.generateCode(temp);
                storeBobaTeaService.addStoreBoba(temp);
            }
            for (StoreBobaTeaModel s : boba.getListStoreBobaTea()) {
                listStore.add(s.getStore());
            }
        }
        model.addAttribute("boba", boba);
        model.addAttribute("listStore", listStore);
        return "assigned-store";

    }
}