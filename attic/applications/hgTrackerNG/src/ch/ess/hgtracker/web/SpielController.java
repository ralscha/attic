package ch.ess.hgtracker.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ch.ess.hgtracker.db.Art;
import ch.ess.hgtracker.db.Club;
import ch.ess.hgtracker.model.JahrItem;
import ch.ess.hgtracker.service.ClubService;
import ch.ess.hgtracker.service.ReportService;
import ch.ess.hgtracker.service.SpielService;

@Controller
public class SpielController {

  @Autowired
  private ReportService reportService;
  
  @Autowired
  private SpielService spielService;
  
  @Autowired
  private ClubService clubService;
  
  @RequestMapping(value="/spielList.do")
  public String processGet(@ModelAttribute("command") FormInput formInput, ModelMap modelMap) {       
    Club club = clubService.getClub(formInput.getWebLogin());
    
    if (!formInput.isAll()) {
      List<Integer> jahrList = reportService.getJahre(club);
      modelMap.addAttribute("jahrOptions", jahrList);
    
      List<Art> artList = reportService.getArtenOnly(club);
      modelMap.addAttribute("artOptions", artList);                  
    }
    

    modelMap.addAttribute("jahr", formInput.getJahr());

    
    if (formInput.isAll()) {

      JahrItem ji = new JahrItem();
      ji.setJahr(formInput.getJahr());
  
      modelMap.addAttribute("spielList", spielService.getAll(club, ji, null));    

      return "spielListWebSpielplan.jsp";
    } 
    
    
    modelMap.addAttribute("spielList", spielService.getSpielAnzeige(club, formInput.getJahr(), formInput.getArt()));
    return "spielListWeb.jsp";
    
  }
  
  
}
