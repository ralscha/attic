package ch.ess.issue.service;

import org.springframework.stereotype.Service;
import ch.ess.issue.entity.Resolution;

@Service
public class ResolutionService extends AbstractService<Resolution> {

  public ResolutionService() {
    super(Resolution.class);

  }

}
