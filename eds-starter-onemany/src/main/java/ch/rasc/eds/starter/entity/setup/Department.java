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
@Table(name = "Department")
@Model(value = "Starter.model.setup.Department", readMethod = "departmentService.read",
        createMethod = "departmentService.create", updateMethod = "departmentService.update",
        destroyMethod = "departmentService.destroy", paging = true, identifier = "negative")
public class Department extends AbstractPersistable {

    @NotBlank(message = "{fieldrequired}")
    @Size(max = 255)
    private String departmentName;


    @Size(max = 255)
    @ModelField(allowNull = true)
    private String notes;

    @ModelField(dateFormat = "time", persist = false)
    private ZonedDateTime lastUpdate;

    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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
