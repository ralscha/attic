package ch.ess.issue.service;

import org.springframework.stereotype.Service;
import ch.ess.issue.entity.PriorityLevel;

@Service
public class PriorityLevelService extends AbstractService<PriorityLevel> {

  public PriorityLevelService() {
    super(PriorityLevel.class);

  }

}
