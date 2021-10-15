package apap.tugas.BOBAXIXIXI.controller;

import apap.tugas.BOBAXIXIXI.model.BobaTeaModel;
import apap.tugas.BOBAXIXIXI.model.StoreBobaTeaModel;
import apap.tugas.BOBAXIXIXI.model.StoreModel;
import apap.tugas.BOBAXIXIXI.model.ToppingModel;
import apap.tugas.BOBAXIXIXI.service.BobaTeaService;
import apap.tugas.BOBAXIXIXI.service.ToppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SearchController {
    @Qualifier("bobaTeaServiceImpl")
    @Autowired
    private BobaTeaService bobaTeaService;

    @Qualifier("toppingServiceImpl")
    @Autowired
    private ToppingService toppingService;

    @GetMapping("/search")
    public String search(
            @RequestParam(value = "bobaName", required = false) String namaBoba,
            @RequestParam(value = "toppingName", required = false) String namaTopping,
            Model model
    ) {
        Set<String> setNama = new HashSet<String>();
        List<BobaTeaModel> listBoba = bobaTeaService.getBobaTeaList();
        for (BobaTeaModel i: listBoba) {
            setNama.add(i.getName());
        }
        List<ToppingModel> listTopping = toppingService.getToppingList();
        model.addAttribute("listBoba", setNama);
        model.addAttribute("listTopping", listTopping);
        ToppingModel topping = toppingService.getToppingByName(namaTopping);
        List<BobaTeaModel> used = new ArrayList<BobaTeaModel>();
        List<StoreModel> usedStore = new ArrayList<StoreModel>();
        if (namaBoba != null & topping != null) {
            for(BobaTeaModel b: listBoba) {
                if (b.getName().equals(namaBoba)) {
                    if (b.getTopping().getName().equals(namaTopping)) {
                        for (StoreBobaTeaModel sb : b.getListStoreBobaTea()) {
                            usedStore.add(sb.getStore());
                            used.add(b);
                        }
                    }
                }
            }
        }
        System.out.println(used.size());
        System.out.println(usedStore.size());
        for(BobaTeaModel bb: used) {
            System.out.println(bb.getName());
        }
        for(StoreModel cc: usedStore) {
            System.out.println(cc.getName());
        }
        model.addAttribute("listCariStore", usedStore);
        model.addAttribute("listCariBoba", used);
        return "search";
    }
}
