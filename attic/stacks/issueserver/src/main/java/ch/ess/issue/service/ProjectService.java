package ch.ess.issue.service;

import org.springframework.stereotype.Service;
import ch.ess.issue.entity.Project;

@Service
public class ProjectService extends AbstractService<Project> {

  public ProjectService() {
    super(Project.class);

  }

}
