package ch.rasc.eds.starter.entity.inventory;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.rasc.edsutil.entity.AbstractPersistable;

/**
 * Created by kmkywar on 17/02/2015.
 */
@Entity
//@Model(value = "Starter.model.inventory.SopHeader", readMethod = "sophService.sopheaderRead",
//createMethod = "sophService.sopheaderCreate", updateMethod = "sophService.sopheaderUpdate",
//destroyMethod = "sophService.sopheaderDestroy",
//identifier = "negative")
public class SopHeader extends AbstractPersistable{

    @Size(max = 50)
    private String name;


    @Size(max = 50)
    private String phone;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sopheader", orphanRemoval = true)
    @JsonIgnore
    private Set<SopDetail> sopDetails = new HashSet<>();

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<SopDetail> getSopDetails() {
        return this.sopDetails;
    }

    public void setSopDetails(Set<SopDetail> sopDetails) {
        this.sopDetails = sopDetails;
    }
}
