package ch.ess.issue.service;

import org.springframework.stereotype.Service;
import ch.ess.issue.entity.Issue;

@Service
public class IssueService extends AbstractService<Issue> {

  public IssueService() {
    super(Issue.class);

  }

}
