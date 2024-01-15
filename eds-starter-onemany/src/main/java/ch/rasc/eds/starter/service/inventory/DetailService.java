package ch.rasc.eds.starter.service.inventory;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.filter.NumericFilter;
import ch.rasc.eds.starter.entity.inventory.InventoryDetail;
import ch.rasc.eds.starter.entity.inventory.InventoryHeader;
import ch.rasc.eds.starter.entity.inventory.QInventoryDetail;
import ch.rasc.eds.starter.entity.setup.Item;
import ch.rasc.edsutil.JPAQueryFactory;

/**
 * Created by Administrator on 26/08/2015.
 */

@Service
@PreAuthorize("isAuthenticated()")
public class DetailService {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public DetailService(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @ExtDirectMethod(STORE_READ)
    @Transactional(readOnly=true)
    public Collection<InventoryDetail> read(ExtDirectStoreReadRequest request){

    	 NumericFilter inventoryHeaderIdFilter = request.getFirstFilterForField("inventoryHeaderId");
         Number inventoryHeaderId = inventoryHeaderIdFilter.getValue();
         List<InventoryDetail> inventoryDetails = this.jpaQueryFactory.selectFrom(QInventoryDetail.inventoryDetail)
                 .where(QInventoryDetail.inventoryDetail.inventoryHeader.id.eq(inventoryHeaderId.longValue()))
                 .fetch();

         for (InventoryDetail inventoryDetail : inventoryDetails) {
        	 inventoryDetail.setInventoryHeaderId(inventoryDetail.getInventoryHeader().getId());
        	 inventoryDetail.setItemId(inventoryDetail.getItem().getId());
         }
         return inventoryDetails;
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public List<InventoryDetail> create(List<InventoryDetail> newEntities){

        for(InventoryDetail newEntity : newEntities){
            newEntity.setId(null);
            newEntity.setItem(this.jpaQueryFactory.getEntityManager().getReference(Item.class, newEntity.getItemId()));
            newEntity.setInventoryHeader(this.jpaQueryFactory.getEntityManager().getReference(InventoryHeader.class,newEntity.getInventoryHeaderId()));
            newEntity.setInventoryHeaderId(newEntity.getInventoryHeader().getId());
            this.jpaQueryFactory.getEntityManager().persist(newEntity);

        }
        return newEntities;
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public List<InventoryDetail> update(List<InventoryDetail> modifies){
        List<InventoryDetail> request = new ArrayList<>();

        for(InventoryDetail modify : modifies){
            InventoryDetail inventoryDetailmodify = this.jpaQueryFactory.getEntityManager().find(InventoryDetail.class,modify.getId());


            if(modify.getItemId() != null){
                inventoryDetailmodify.setItem(this.jpaQueryFactory.getEntityManager().getReference(Item.class, modify.getItemId()));
            }

            if(modify.getQuantity() !=null){
                inventoryDetailmodify.setQuantity(modify.getQuantity());
            }

            modify.setInventoryHeader(inventoryDetailmodify.getInventoryHeader());

            inventoryDetailmodify.setInventoryHeaderId(inventoryDetailmodify.getInventoryHeader().getId());


            request.add(inventoryDetailmodify);
        }
        return request;
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public void destroy(List<InventoryDetail> destroy){

        for(InventoryDetail destroyDetail : destroy){
            InventoryDetail detaildestroy = this.jpaQueryFactory.getEntityManager().find(InventoryDetail.class,destroyDetail.getId());
            this.jpaQueryFactory.getEntityManager().remove(detaildestroy);

        }
    }

}
