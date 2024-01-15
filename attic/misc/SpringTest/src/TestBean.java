import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratorType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="Test")
public class TestBean {

  private Integer id;
  private int version;
  private String name;

  public void setVersion(int version) {
    this.version = version;
  }

  @Id(generate = GeneratorType.AUTO)
  @Column(nullable = false, primaryKey = true)
  public Integer getId() {
    return id;
  }

  protected void setId(final Integer id) {
    this.id = id;
  }

  @Version
  @Column(nullable = false)
  public int getVersion() {
    return version;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  
  
}
