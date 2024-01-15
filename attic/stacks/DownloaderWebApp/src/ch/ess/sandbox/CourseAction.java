package ch.ess.sandbox;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import org.hibernate.Session;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.annotations.remoting.WebRemote;
import org.jboss.seam.faces.FacesMessages;

@Name("courseAction")
@Scope(ScopeType.CONVERSATION)
public class CourseAction implements Serializable {

  private static final long serialVersionUID = 1L;

  @In
  private Session hibernateSession;

  @DataModel
  private List<Course> courses;

  @DataModelSelection
  @In(value = "course", required = false)
  @Out(value = "course", required = false)
  private Course selectedCourse;

  @WebRemote
  public List<Course> getCourses() {
    return courses;
  }

  @WebRemote
  public void setCoursePoint(Long id, Point point) {
    System.out.println("Saving new point: " + point + " for course id: " + id);
    Course course = (Course)hibernateSession.load(Course.class, id); 
    course.setPoint(point);
    hibernateSession.persist(selectedCourse);
  }

  @SuppressWarnings("unchecked")
  @Begin(join = true)
  @Factory("courses")
  public void findCourses() {
    
    @SuppressWarnings("unused")
    List cats = hibernateSession.createQuery(
    "from Product")
    .list();
    
    System.out.println("Retrieving courses...");
    courses = hibernateSession.createCriteria(Course.class).list();
  }

  public String selectCourse() {
    selectCourseNoNav();
    System.out.println("Redirecting to /courses.xhtml");
    return "/sandbox3/courses.xhtml";
  }

  public void selectCourseNoNav() {
    System.out.println("Selected course: " + selectedCourse.getName());
  }

  public String showCourses() {
    selectedCourse = null;
    return "/sandbox3/courses.xhtml";
  }

  public String deleteCourse() {
    hibernateSession.delete(selectedCourse);
    courses.remove(selectedCourse);
    FacesMessages.instance().add(selectedCourse.getName() + " has been removed.");
    // clear selected course so that it is not displayed in the detail pane
    selectedCourse = null;
    return "/sandbox3/courses.xhtml";
  }

  public String addCourse() {
    selectedCourse = new Course();
    selectedCourse.setAddress(new Address());
    return "/sandbox3/courseEditor.xhtml";
  }

  public String editCourse() {
    selectedCourse = (Course)hibernateSession.load(Course.class, selectedCourse.getId());
    return "/sandbox3/courseEditor.xhtml";
  }

  public String saveCourse() {
    // remove from cached list
    if (selectedCourse.getId() != 0) {
      courses.remove(selectedCourse);
    }
    hibernateSession.persist(selectedCourse);
    // add to cached list
    courses.add(selectedCourse);
    Collections.sort(courses);
    FacesMessages.instance().add("#{course.name} has been saved.");
    return "/sandbox3/courses.xhtml";
  }

  public String clearSelection() {
    clearSelectionNoNav();
    return "/sandbox3/courses.xhtml";
  }

  public void clearSelectionNoNav() {
    System.out.println("Clearing course selection");
    selectedCourse = null;
  }
}
