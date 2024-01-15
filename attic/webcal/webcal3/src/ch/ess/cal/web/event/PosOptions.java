package ch.ess.cal.web.event;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.enums.PosEnum;
import ch.ess.cal.web.AbstractOptions;

/**
 * @author sr
 * @version $Revision: 1.3 $ $Date: 2005/04/04 11:31:11 $
 */
public class PosOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
    clear();

    addTranslate(request, PosEnum.FIRST.getKey(), String.valueOf(PosEnum.FIRST.getValue()));
    addTranslate(request, PosEnum.SECOND.getKey(), String.valueOf(PosEnum.SECOND.getValue()));
    addTranslate(request, PosEnum.THIRD.getKey(), String.valueOf(PosEnum.THIRD.getValue()));
    addTranslate(request, PosEnum.FOURTH.getKey(), String.valueOf(PosEnum.FOURTH.getValue()));
    addTranslate(request, PosEnum.LAST.getKey(), String.valueOf(PosEnum.LAST.getValue()));
  }

}