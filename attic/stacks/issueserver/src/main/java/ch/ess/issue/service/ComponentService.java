package ch.ess.issue.service;

import org.springframework.stereotype.Service;
import ch.ess.issue.entity.Component;

@Service
public class ComponentService extends AbstractService<Component> {

  public ComponentService() {
    super(Component.class);

  }

}
