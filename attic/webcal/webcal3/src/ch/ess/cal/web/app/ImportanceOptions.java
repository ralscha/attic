package ch.ess.cal.web.app;

import javax.servlet.http.HttpServletRequest;

import ch.ess.cal.enums.ImportanceEnum;
import ch.ess.cal.web.AbstractOptions;

/**
 * @author sr
 * @version $Revision: 1.2 $ $Date: 2005/04/04 11:31:10 $
 */
public class ImportanceOptions extends AbstractOptions {

  @Override
  public void init(final HttpServletRequest request) {
    clear();
    addTranslate(request, ImportanceEnum.NORMAL.getKey(), String.valueOf(ImportanceEnum.NORMAL.getValue()));
    addTranslate(request, ImportanceEnum.HIGH.getKey(), String.valueOf(ImportanceEnum.HIGH.getValue()));
    addTranslate(request, ImportanceEnum.LOW.getKey(), String.valueOf(ImportanceEnum.LOW.getValue()));
  }

}