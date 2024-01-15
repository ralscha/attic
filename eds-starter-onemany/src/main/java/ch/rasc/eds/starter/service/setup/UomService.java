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
import org.springframework.util.StringUtils;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.JPQLQuery;

/**
 * Created by Administrator on 25/08/2015.
 */

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.ralscha.extdirectspring.filter.StringFilter;
import ch.rasc.eds.starter.entity.setup.QUom;
import ch.rasc.eds.starter.entity.setup.Uom;
import ch.rasc.edsutil.JPAQueryFactory;
import ch.rasc.edsutil.QuerydslUtil;

@Service
public class UomService {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public UomService(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @ExtDirectMethod(STORE_READ)
    @Transactional(readOnly=true)
    public ExtDirectStoreResult<Uom> read(ExtDirectStoreReadRequest request){

        JPQLQuery<Uom> query = this.jpaQueryFactory.selectFrom(QUom.uom);
        if(!request.getFilters().isEmpty()){
            StringFilter filter= (StringFilter) request.getFilters().iterator().next();
            BooleanBuilder bb= new BooleanBuilder();
            bb.or(QUom.uom.uomName.contains(filter.getValue()));
            bb.or(QUom.uom.notes.contains(filter.getValue()));
            query.where(bb);
        }

        QuerydslUtil.addPagingAndSorting(query,request,Uom.class,QUom.uom);
        QueryResults<Uom> searchResult = query.fetchResults();

        return new ExtDirectStoreResult<>(searchResult.getTotal(),searchResult.getResults());
    }


    @ExtDirectMethod(STORE_READ)
    @Transactional(readOnly=true)
    public Collection<Uom> readUom(){
        return this.jpaQueryFactory.selectFrom(QUom.uom).fetch();
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public List<Uom> create(List<Uom> newEntities){

        for(Uom newEntity : newEntities){
            newEntity.setId(null);
            newEntity.setLastUpdate(ZonedDateTime.now());
            if (!StringUtils.hasText(newEntity.getNotes())) {
                newEntity.setNotes(null);
            }
            this.jpaQueryFactory.getEntityManager().persist(newEntity);
        }
        return newEntities;
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public List<Uom> update(List<Uom> modifies){
        List<Uom> request = new ArrayList<>();

        for(Uom modify : modifies){
            Uom uommodify = this.jpaQueryFactory.getEntityManager().find(Uom.class,modify.getId());

            if(modify.getUomName() != null){
                uommodify.setUomName(modify.getUomName());
            }
            if(modify.getNotes() != null){
                uommodify.setNotes(modify.getNotes());
            }
            else
            {
                uommodify.setNotes(null);
            }

            uommodify.setLastUpdate(ZonedDateTime.now());

            request.add(uommodify);
        }
        return request;
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public void destroy(List<Uom> destroy){

        for(Uom destroyUom : destroy){
            Uom uomdestroy = this.jpaQueryFactory.getEntityManager().find(Uom.class,destroyUom.getId());
            this.jpaQueryFactory.getEntityManager().remove(uomdestroy);

        }
    }
}
