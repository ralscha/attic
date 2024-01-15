package ch.ess.hgtracker.web;

import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ch.ess.hgtracker.db.Spiel;
import ch.ess.hgtracker.model.PunkteResult;
import ch.ess.hgtracker.model.SpielForm;
import ch.ess.hgtracker.service.SpielService;

@Controller
public class PunktController {

  @Autowired
  private SpielService spielService;

  @RequestMapping(value = "/punkteEdit.do")
  public String processGet(@RequestParam("spielId")
  int spielId, ModelMap modelMap) {

    Spiel spiel = spielService.getSpiel(spielId);

    SpielForm spielForm = new SpielForm();

    spielForm.setSpielArt(spiel.getArt().getSpielArt());
    spielForm.setGegner(spiel.getGegner());
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    spielForm.setDatum(sdf.format(spiel.getDatum()));
    spielForm.setKampfrichter(spiel.getKampfrichter());
    spielForm.setKampfrichterGegner(spiel.getKampfrichterGegner());
    spielForm.setTotalNr(spiel.getTotalNrGegner().toString());
    spielForm.setTotalNrHeim(spiel.getTotalNr().toString());
    spielForm.setSchlagPunkteGegner(spiel.getSchlagPunkteGegner().toString());

    
    PunkteResult pr = spielService.getPunkte(spiel);    

    modelMap.addAttribute("meisterschaft", spiel.getArt().getMeisterschaft());
    modelMap.addAttribute("spielForm", spielForm);
    modelMap.addAttribute("result", pr);

    return "spielPunkteWeb.jsp";

  }

}
