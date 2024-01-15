package ch.rasc.eds.starter.entity.setup;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

/**
 * Created by Administrator on 26/08/2015.
 */

@Entity
@Table(name = "Item")
@Model(value = "Starter.model.setup.Item",readMethod = "itemService.read",
        createMethod = "itemService.create",updateMethod = "itemService.update",
        destroyMethod = "itemService.destroy",paging = true,identifier = "negative")
public class Item extends AbstractPersistable {

    @NotBlank(message = "{fieldrequired}")
    @Size(max = 255)
    private String code;

    @NotBlank(message = "{fieldrequired}")
    @Size(max = 255)
    private String name;

    @Size(max = 255)
    @ModelField(allowNull = true)
    private String remark;

    @ModelField(dateFormat = "time", persist = false)
    private ZonedDateTime lastUpdate;

    @ManyToOne
    @JoinColumn(name = "uomId")
    @JsonIgnore
    private Uom uom;

    @Transient
    @ModelField(useNull = true)
    private Long uomId;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ZonedDateTime getLastUpdate() {
        return this.lastUpdate;
    }

    public void setLastUpdate(ZonedDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Uom getUom() {
        return this.uom;
    }

    public void setUom(Uom uom) {
        this.uom = uom;
    }

    public Long getUomId() {
        return this.uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }
}
