package ch.rasc.eds.starter.service.inventory;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.eds.starter.entity.inventory.InventoryDetail;
import ch.rasc.eds.starter.entity.inventory.InventoryHeader;
import ch.rasc.eds.starter.entity.inventory.QInventoryHeader;
import ch.rasc.eds.starter.entity.setup.Department;
import ch.rasc.eds.starter.entity.setup.Item;
import ch.rasc.eds.starter.entity.setup.Location;
import ch.rasc.eds.starter.entity.setup.Section;
import ch.rasc.edsutil.JPAQueryFactory;
import ch.rasc.edsutil.QuerydslUtil;

/**
 * Created by Administrator on 26/08/2015.
 */

@Service
@PreAuthorize("isAuthenticated()")
public class HeaderService {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public HeaderService(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @ExtDirectMethod(STORE_READ)
    @Transactional(readOnly=true)
    public ExtDirectStoreResult<InventoryHeader> read(ExtDirectStoreReadRequest request){

        JPQLQuery<InventoryHeader> query = this.jpaQueryFactory.selectFrom(QInventoryHeader.inventoryHeader);
        if(!request.getFilters().isEmpty()){
            StringFilter filter= (StringFilter) request.getFilters().iterator().next();
            BooleanBuilder bb= new BooleanBuilder();
            bb.or(QInventoryHeader.inventoryHeader.userName.contains(filter.getValue()));
            bb.or(QInventoryHeader.inventoryHeader.enrollNo.contains(filter.getValue()));
            bb.or(QInventoryHeader.inventoryHeader.department.departmentName.contains(filter.getValue()));
            bb.or(QInventoryHeader.inventoryHeader.section.sectionName.contains(filter.getValue()));
            bb.or(QInventoryHeader.inventoryHeader.location.locationName.contains(filter.getValue()));
            query.where(bb);
        }

        QuerydslUtil.addPagingAndSorting(query, request, InventoryHeader.class, QInventoryHeader.inventoryHeader);
        QueryResults<InventoryHeader> searchResult = query.fetchResults();

        for (InventoryHeader i: searchResult.getResults()) {
            i.setDepartmentId(i.getDepartment().getId());
            i.setSectionId(i.getSection().getId());
            i.setLocationId(i.getLocation().getId());
        }

        return new ExtDirectStoreResult<>(searchResult.getTotal(),searchResult.getResults());
    }


    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public List<InventoryHeader> create(List<InventoryHeader> newEntities){

        for(InventoryHeader newEntity : newEntities){
            newEntity.setId(null);
            newEntity.setDepartment(this.jpaQueryFactory.getEntityManager().getReference(Department.class, newEntity.getDepartmentId()));
            newEntity.setSection(this.jpaQueryFactory.getEntityManager().getReference(Section.class, newEntity.getSectionId()));
            newEntity.setLocation(this.jpaQueryFactory.getEntityManager().getReference(Location.class, newEntity.getLocationId()));
            newEntity.setLastUpdate(ZonedDateTime.now());

            for (InventoryDetail detail : newEntity.getInventoryDetails()) {

            	if (detail.getId() < 0) {
            		detail.setId(null);
            	}

            	detail.setInventoryHeader(newEntity);
            	detail.setItem(this.jpaQueryFactory.getEntityManager().getReference(Item.class, detail.getItemId()));
            }

            this.jpaQueryFactory.getEntityManager().persist(newEntity);

            newEntity.setDepartmentId(newEntity.getDepartment().getId());
            newEntity.setSectionId(newEntity.getSection().getId());
            newEntity.setLocationId(newEntity.getLocation().getId());

        }
        return newEntities;
    }


    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public List<InventoryHeader> update(List<InventoryHeader> modifies){
        List<InventoryHeader> response = new ArrayList<>();
        EntityManager em = this.jpaQueryFactory.getEntityManager();

        for(InventoryHeader modify : modifies){
			modify.setDepartment(em.getReference(Department.class, modify.getDepartmentId()));
        	modify.setSection(em.getReference(Section.class, modify.getSectionId()));
        	modify.setLocation(em.getReference(Location.class,modify.getLocationId()));
        	modify.setLastUpdate(ZonedDateTime.now());

            for (InventoryDetail detail : modify.getInventoryDetails()) {
            	detail.setInventoryHeader(modify);
            	detail.setItem(this.jpaQueryFactory.getEntityManager().getReference(Item.class, detail.getItemId()));
            }

        	InventoryHeader inventoryHeadermodify = em.merge(modify);

            inventoryHeadermodify.setDepartmentId(inventoryHeadermodify.getDepartment().getId());
            inventoryHeadermodify.setSectionId(inventoryHeadermodify.getSection().getId());
            inventoryHeadermodify.setLocationId(inventoryHeadermodify.getLocation().getId());

            response.add(inventoryHeadermodify);
        }
        return response;
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public void destroy(List<InventoryHeader> destroy){

        for(InventoryHeader destroyHeader : destroy){
            InventoryHeader headerdestroy = this.jpaQueryFactory.getEntityManager().find(InventoryHeader.class,destroyHeader.getId());
            this.jpaQueryFactory.getEntityManager().remove(headerdestroy);

        }
    }
}
