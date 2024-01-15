package ch.rasc.eds.starter.entity.inventory;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.edsutil.jackson.ISO8601LocalDateDeserializer;
import ch.rasc.edsutil.jackson.ISO8601LocalDateSerializer;

/**
 * Created by kmkywar on 17/02/2015.
 */
@Entity
//@Model(value = "Starter.model.inventory.SopDetail", readMethod = "sopdService.sopdetailRead",
//createMethod = "sopdService.sopdetailCreate", updateMethod = "sopdService.sopdetailUpdate",
//destroyMethod = "sopdService.sopdetailDestroy", identifier = "negative")
public class SopDetail extends AbstractPersistable{



    @JsonSerialize(using = ISO8601LocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    private LocalDate orderdate;

    private Boolean shipped;

    @Transient
    private Long sopHeaderId;

    @ManyToOne
    @JoinColumn(name = "sopHeaderId")
    @JsonIgnore
    private SopHeader sopheader;

    public LocalDate getOrderdate() {
        return this.orderdate;
    }

    public void setOrderdate(LocalDate orderdate) {
        this.orderdate = orderdate;
    }

    public Boolean getShipped() {
        return this.shipped;
    }

    public void setShipped(Boolean shipped) {
        this.shipped = shipped;
    }

    public Long getSopHeaderId() {
        return this.sopHeaderId;
    }

    public void setSopHeaderId(Long sopHeaderId) {
        this.sopHeaderId = sopHeaderId;
    }

    public SopHeader getSopheader() {
        return this.sopheader;
    }

    public void setSopheader(SopHeader sopheader) {
        this.sopheader = sopheader;
    }
}
