package ch.ess.issue.service;

import org.springframework.stereotype.Service;
import ch.ess.issue.entity.IssueType;

@Service
public class IssueTypeService extends AbstractService<IssueType> {

  public IssueTypeService() {
    super(IssueType.class);

  }

}
