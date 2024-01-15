package ch.rasc.eds.starter.service.setup;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.eds.starter.entity.setup.Item;
import ch.rasc.eds.starter.entity.setup.QItem;
import ch.rasc.eds.starter.entity.setup.Uom;
import ch.rasc.edsutil.JPAQueryFactory;
import ch.rasc.edsutil.QuerydslUtil;

/**
 * Created by Administrator on 26/08/2015.
 */

@Service
public class ItemService {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public ItemService(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @ExtDirectMethod(STORE_READ)
    @Transactional(readOnly=true)
    public ExtDirectStoreResult<Item> read(ExtDirectStoreReadRequest request){

        JPQLQuery<Item> query = this.jpaQueryFactory.selectFrom(QItem.item);
        if(!request.getFilters().isEmpty()){
            StringFilter filter= (StringFilter) request.getFilters().iterator().next();
            BooleanBuilder bb= new BooleanBuilder();
            bb.or(QItem.item.code.contains(filter.getValue()));
            bb.or(QItem.item.name.contains(filter.getValue()));
            bb.or(QItem.item.uom.uomName.contains(filter.getValue()));
            query.where(bb);
        }

        QuerydslUtil.addPagingAndSorting(query, request, Item.class, QItem.item);
        QueryResults<Item> searchResult = query.fetchResults();

        for (Item i: searchResult.getResults()) {
            i.setUomId(i.getUom().getId());
        }

        return new ExtDirectStoreResult<>(searchResult.getTotal(),searchResult.getResults());
    }

    @ExtDirectMethod(STORE_READ)
    @Transactional(readOnly=true)
    public Collection<Item> readItem(){
        return this.jpaQueryFactory.selectFrom(QItem.item).fetch();
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public List<Item> create(List<Item> newEntities){

        for(Item newEntity : newEntities){
            newEntity.setId(null);
            newEntity.setUom(this.jpaQueryFactory.getEntityManager().getReference(Uom.class, newEntity.getUomId()));
            newEntity.setLastUpdate(ZonedDateTime.now());
            this.jpaQueryFactory.getEntityManager().persist(newEntity);
            System.out.println(newEntity.getUomId());
        }
        return newEntities;
    }


    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public List<Item> update(List<Item> modifies){
        List<Item> request = new ArrayList<>();

        for(Item modify : modifies){
            Item itemmodify = this.jpaQueryFactory.getEntityManager().find(Item.class,modify.getId());

            if(modify.getCode() != null){
                itemmodify.setCode(modify.getCode());
            }
            if(modify.getName() != null){
                itemmodify.setName(modify.getName());
            }
            if(modify.getUomId() != null){
                itemmodify.setUom(this.jpaQueryFactory.getEntityManager().getReference(Uom.class, modify.getUomId()));
            }

            itemmodify.setLastUpdate(ZonedDateTime.now());

            request.add(itemmodify);
        }
        return request;
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public void destroy(List<Item> destroy){

        for(Item destroyItem : destroy){
            Item itemdestroy = this.jpaQueryFactory.getEntityManager().find(Item.class,destroyItem.getId());
            this.jpaQueryFactory.getEntityManager().remove(itemdestroy);

        }
    }
}
