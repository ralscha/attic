package ch.rasc.eds.starter.entity.inventory;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Administrator on 26/08/2015.
 */

import ch.rasc.eds.starter.entity.setup.Item;
import ch.rasc.edsutil.entity.AbstractPersistable;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;
import ch.rasc.extclassgenerator.ReferenceConfig;

@Entity
@Table(name = "InventoryDetail")
@Model(extend = "Starter.model.inventory.Base",value = "Starter.model.inventory.InventoryDetail",readMethod = "detailService.read",
        createMethod = "detailService.create",updateMethod = "detailService.update",
        destroyMethod = "detailService.destroy",identifier = "negative")
public class InventoryDetail extends  AbstractPersistable{

    @ManyToOne
    @JoinColumn(name = "itemId")
    @JsonIgnore
    private Item item;


    @Transient
    @ModelField(useNull = true)
    private Long itemId;

    @NotBlank(message = "{fieldrequired}")
    @Size(max = 255)
    private String quantity;

    @ManyToOne
    @JoinColumn(name = "inventoryHeaderId")
    @JsonIgnore
    private InventoryHeader inventoryHeader;
    @Transient
    private boolean useItem;

    @Transient
    @ModelField(reference = @ReferenceConfig(parent = "InventoryHeader"),useNull = true)
    private Long inventoryHeaderId;

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Long getItemId() {
        return this.itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public InventoryHeader getInventoryHeader() {
        return this.inventoryHeader;
    }

    public void setInventoryHeader(InventoryHeader inventoryHeader) {
        this.inventoryHeader = inventoryHeader;
    }

    public Long getInventoryHeaderId() {
        return this.inventoryHeaderId;
    }

    public void setInventoryHeaderId(Long inventoryHeaderId) {
        this.inventoryHeaderId = inventoryHeaderId;
    }

	public boolean isUseItem() {
		return this.useItem;
	}

	public void setUseItem(boolean useItem) {
		this.useItem = useItem;
	}

}
