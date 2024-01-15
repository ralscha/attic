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
@Table(name = "Location")
@Model(value = "Starter.model.setup.Location", readMethod = "locationService.read",
        createMethod = "locationService.create", updateMethod = "locationService.update",
        destroyMethod = "locationService.destroy", paging = true, identifier = "negative")
public class Location extends AbstractPersistable {

    @NotBlank(message = "{fieldrequired}")
    @Size(max = 255)
    private String locationName;


    @Size(max = 255)
    @ModelField(allowNull = true)
    private String notes;

    @ModelField(dateFormat = "time", persist = false)
    private ZonedDateTime lastUpdate;

    public String getLocationName() {
        return this.locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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

