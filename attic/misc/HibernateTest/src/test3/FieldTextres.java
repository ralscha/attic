package test3;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class FieldTextres {

  private FieldTextresPK id;
  private TextResource textresId;

  @EmbeddedId
  public FieldTextresPK getId() {
    return id;
  }

  public void setId(FieldTextresPK id) {
    this.id = id;
  }

  @ManyToOne
  @JoinColumn(name = "textres_id")
  public TextResource getTextresId() {
    return textresId;
  }

  public void setTextresId(TextResource textresId) {
    this.textresId = textresId;
  }

}
