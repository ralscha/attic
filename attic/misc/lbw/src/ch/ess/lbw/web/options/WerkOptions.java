package ch.ess.lbw.web.options;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.lbw.dao.WerkDao;
import ch.ess.lbw.model.Werk;

@Option(id = "werkOptions")
public class WerkOptions extends AbstractOptions {

  private WerkDao werkDao;

  public void setWerkDao(final WerkDao werkDao) {
    this.werkDao = werkDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    List<Werk> werke = werkDao.findAll();

    for (Werk werk : werke) {
      add(werk.getName(), werk.getId().toString());
    }

    sort();
  }

}