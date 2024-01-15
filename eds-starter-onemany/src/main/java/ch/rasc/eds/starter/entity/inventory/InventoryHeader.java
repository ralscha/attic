package ch.rasc.eds.starter.entity.inventory;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 26/08/2015.
 */

import ch.rasc.eds.starter.entity.setup.Department;
import ch.rasc.eds.starter.entity.setup.Location;
import ch.rasc.eds.starter.entity.setup.Section;
import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.AllDataOptions;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.PartialDataOptions;

@Entity
@Table(name = "InventoryHeader")
@Model(extend = "Starter.model.inventory.Base",value = "Starter.model.inventory.InventoryHeader",readMethod = "headerService.read",
        createMethod = "headerService.create",updateMethod = "headerService.update",
        destroyMethod = "headerService.destroy",paging = true,identifier = "negative", allDataOptions=@AllDataOptions(associated=true),
        partialDataOptions=@PartialDataOptions(changes=false, persist=true, associated=true, critical=true))
public class InventoryHeader extends  AbstractPersistable{

    @NotBlank(message = "{fieldrequired}")
    @Size(max = 255)
    private String userName;

    @Size(max = 255)
    private String enrollNo;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    @JsonIgnore
    private Department department;

    @Transient
    @ModelField(useNull = true)
    private Long departmentId;

    @ManyToOne
    @JoinColumn(name = "sectionId")
    @JsonIgnore
    private Section section;

    @Transient
    @ModelField(useNull = true)
    private Long sectionId;

    @ManyToOne
    @JoinColumn(name = "locationId")
    @JsonIgnore
    private Location location;

    @Transient
    @ModelField(useNull = true)
    private Long locationId;

    @Size(max = 255)
    @ModelField(useNull = true)
    private String remark;

    @ModelField(dateFormat = "time", persist = false)
    private ZonedDateTime lastUpdate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "inventoryHeader", orphanRemoval = true)
    @JsonIgnore
    private Set<InventoryDetail> inventoryDetails = new HashSet<>();

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEnrollNo() {
        return this.enrollNo;
    }

    public void setEnrollNo(String enrollNo) {
        this.enrollNo = enrollNo;
    }

    public Department getDepartment() {
        return this.department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Long getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Section getSection() {
        return this.section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Long getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Long getLocationId() {
        return this.locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @JsonIgnore
    public Set<InventoryDetail> getInventoryDetails() {
        return this.inventoryDetails;
    }

    @JsonProperty
    public void setInventoryDetails(Set<InventoryDetail> inventoryDetails) {
        this.inventoryDetails = inventoryDetails;
    }

    public ZonedDateTime getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
