package ch.rasc.eds.starter.entity.setup;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Administrator on 25/08/2015.
 */
import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

@Entity
@Table(name = "Section")
@Model(value = "Starter.model.setup.Section", readMethod = "sectionService.read",
        createMethod = "sectionService.create", updateMethod = "sectionService.update",
        destroyMethod = "sectionService.destroy", paging = true, identifier = "negative")
public class Section extends AbstractPersistable {

    @NotBlank(message = "{fieldrequired}")
    @Size(max = 255)
    private String sectionName;


    @Size(max = 255)
    @ModelField(allowNull = true)
    private String notes;

    @ModelField(dateFormat = "time", persist = false)
    private ZonedDateTime lastUpdate;

    public String getSectionName() {
        return this.sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ZonedDateTime getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}

