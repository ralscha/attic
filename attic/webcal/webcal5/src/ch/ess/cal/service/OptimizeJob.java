package ch.ess.cal.service;

import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import ch.ess.cal.service.search.SearchEngine;

public class OptimizeJob extends QuartzJobBean {

  private SearchEngine searchEngine;

  public void setSearchEngine(SearchEngine searchEngine) {
    this.searchEngine = searchEngine;
  }

  @Override
  public void executeInternal(JobExecutionContext context) {
    searchEngine.optimize();
  }
}
