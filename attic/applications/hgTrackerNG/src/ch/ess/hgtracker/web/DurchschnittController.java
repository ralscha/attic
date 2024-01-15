package ch.ess.hgtracker.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ch.ess.hgtracker.db.Club;
import ch.ess.hgtracker.service.ClubService;
import ch.ess.hgtracker.service.ReportService;

@Controller
public class DurchschnittController {

  @Autowired
  private ReportService reportService;
  
  @Autowired
  private ClubService clubService;
  
  @RequestMapping(value="/durchschnittList.do", method = RequestMethod.GET)
  public String processGet(@ModelAttribute("command") FormInput formInput, ModelMap modelMap) {       
    Club club = clubService.getClub(formInput.getWebLogin());
    
    List<Integer> jahrList = reportService.getJahre(club);
    modelMap.addAttribute("jahrOptions", jahrList);
  
    if (!jahrList.isEmpty()) {
      formInput.setJahr(jahrList.get(0));
    }
    modelMap.addAttribute("jahr", formInput.getJahr());
    modelMap.addAttribute("durchschnitte", reportService.getDurchschnitt(club, formInput.getJahr()));    
    return "durchschnitt.jsp";
  }
  
  
  @RequestMapping(value="/durchschnittList.do", method = RequestMethod.POST)
  public String processPost(@ModelAttribute("command") FormInput formInput, ModelMap modelMap) {
    Club club = clubService.getClub(formInput.getWebLogin());
    
    List<Integer> jahrList = reportService.getJahre(club);
    modelMap.addAttribute("jahrOptions", jahrList);
    modelMap.addAttribute("jahr", formInput.getJahr());
    modelMap.addAttribute("durchschnitte", reportService.getDurchschnitt(club, formInput.getJahr()));
    
    return "durchschnitt.jsp";
  }
  
  
  @RequestMapping("/durchschnittListDL.do")
  public String pageing(@ModelAttribute("command") FormInput formInput, ModelMap modelMap) {
    return processPost(formInput, modelMap);
  }
}
