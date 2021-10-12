package apap.tugas.BOBAXIXIXI.controller;

import apap.tugas.BOBAXIXIXI.model.BobaTeaModel;
import apap.tugas.BOBAXIXIXI.model.ToppingModel;
import apap.tugas.BOBAXIXIXI.service.BobaTeaService;
import apap.tugas.BOBAXIXIXI.service.ToppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BobaTeaController {

    @Qualifier("bobaTeaServiceImpl")
    @Autowired
    private BobaTeaService bobaTeaService;

    @Qualifier("toppingServiceImpl")
    @Autowired
    private ToppingService toppingService;

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
        model.addAttribute("name", boba.getName());
        bobaTeaService.deleteBoba(boba);
        return "delete-boba";
    }

    @GetMapping("/boba/update/{id}")
    public String updateBobaForm(
            @PathVariable Long id,
            Model model
    ) {
        BobaTeaModel boba = bobaTeaService.getBobaById(id);
        List<ToppingModel> listTopping =  toppingService.getToppingList();
        model.addAttribute("listTopping", listTopping);
        model.addAttribute("boba", boba);
        return "form-update-boba";
    }

    @PostMapping("/boba/update")
    public String updateBobaSubmit(
            @ModelAttribute BobaTeaModel boba,
            Model model
    ) {
        bobaTeaService.updateBoba(boba);
        model.addAttribute("name", boba.getName());
        return "update-boba";
    }

}