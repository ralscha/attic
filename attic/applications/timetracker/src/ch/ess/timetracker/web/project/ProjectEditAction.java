package ch.ess.timetracker.web.project;

import ch.ess.common.db.Persistent;
import ch.ess.common.web.PersistentAction;
import ch.ess.timetracker.db.Project;

/** 
  * @struts.action path="/newProject" name="projectForm" input=".project.list" scope="session" validate="false" roles="admin" parameter="add"
  * @struts.action path="/editProject" name="projectForm" input=".project.list" scope="session" validate="false" roles="admin" parameter="edit" 
  * @struts.action path="/storeProject" name="projectForm" input=".project.edit" scope="session" validate="true" parameter="store" roles="admin"
  * @struts.action path="/deleteProject" parameter="delete" validate="false" roles="admin"
  *
  * @struts.action-forward name="edit" path=".project.edit"
  * @struts.action-forward name="list" path="/listProject.do" redirect="true"
  * @struts.action-forward name="delete" path="/deleteProject.do" 
  * @struts.action-forward name="reload" path="/editProject.do" 
  */
public class ProjectEditAction extends PersistentAction {

  protected int deletePersistent(Long id) throws Exception {
    return Project.delete(id);
  }

  protected Persistent loadPersistent(Long id) throws Exception {
    return Project.load(id);
  }

  protected Persistent newPersistent() throws Exception {
    return new Project();
  }

}