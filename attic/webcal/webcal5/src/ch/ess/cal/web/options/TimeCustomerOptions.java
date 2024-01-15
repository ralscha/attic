package ch.ess.cal.web.options;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ch.ess.base.annotation.option.Option;
import ch.ess.base.web.options.AbstractOptions;
import ch.ess.cal.dao.TimeCustomerDao;
import ch.ess.cal.model.TimeCustomer;

@Option(id = "timeCustomerOptions")
public class TimeCustomerOptions extends AbstractOptions {

  private TimeCustomerDao timeCustomerDao;

  public void setTimeCustomerDao(TimeCustomerDao timeCustomerDao) {
    this.timeCustomerDao = timeCustomerDao;
  }

  @Override
  public void init(final HttpServletRequest request) {

    List<TimeCustomer> customerList = timeCustomerDao.findAll();

    for (TimeCustomer customer : customerList) {
      add(customer.getName(), customer.getId().toString());
    }

    sort();
  }

}
