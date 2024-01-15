package ch.ess.sandbox;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.Session;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;

@Name("badCourseAction")
public class BadCourseAction implements Serializable {

  private static final long serialVersionUID = 1L;

  @In
  private Session hibernateSession;

  private DataModel coursesModel;

  private Course course;

  public void selectCourse() {
    this.course = getSelectedCourse();
  }

  public void deleteCourse() {
    Course crs = getSelectedCourse();
    if (crs != null) {
      hibernateSession.delete(crs);
    }

    // the courses model is now stale
    coursesModel = null;
  }

  /**
   * If we don't use an instance variable to store the fetched
   * courses, then the service layer will be hit many times.
   */
  public DataModel getCourses() {
    if (coursesModel == null) {
      System.out.println("Retrieving courses...");
      coursesModel = new ListDataModel(hibernateSession.createCriteria(Course.class).list());
    }
    return coursesModel;
  }

  public String showCourses() {
    return "/sandbox/badCourses.xhtml";
  }

  public void setCourses(DataModel coursesModel) {
    this.coursesModel = coursesModel;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  /**
   * Notice the JSF specific work that needs to be done to access the current
   * row, making the code more verbose and unit testing more difficult.
   */
  private Course getSelectedCourse() {
    FacesContext ctxt = FacesContext.getCurrentInstance();
    return (Course)ctxt.getApplication().getVariableResolver().resolveVariable(ctxt, "_course");
  }
}