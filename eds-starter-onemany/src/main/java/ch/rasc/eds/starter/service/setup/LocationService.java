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
import ch.rasc.eds.starter.entity.setup.Location;
import ch.rasc.eds.starter.entity.setup.QLocation;
import ch.rasc.edsutil.JPAQueryFactory;
import ch.rasc.edsutil.QuerydslUtil;

@Service
public class LocationService {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public LocationService(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @ExtDirectMethod(STORE_READ)
    @Transactional(readOnly=true)
    public ExtDirectStoreResult<Location> read(ExtDirectStoreReadRequest request){

        JPQLQuery<Location> query = this.jpaQueryFactory.selectFrom(QLocation.location);
        if(!request.getFilters().isEmpty()){
            StringFilter filter= (StringFilter) request.getFilters().iterator().next();
            BooleanBuilder bb= new BooleanBuilder();
            bb.or(QLocation.location.locationName.contains(filter.getValue()));
            bb.or(QLocation.location.notes.contains(filter.getValue()));
            query.where(bb);
        }

        QuerydslUtil.addPagingAndSorting(query,request,Location.class,QLocation.location);
        QueryResults<Location> searchResult = query.fetchResults();

        return new ExtDirectStoreResult<>(searchResult.getTotal(),searchResult.getResults());
    }


    @ExtDirectMethod(STORE_READ)
    @Transactional(readOnly=true)
    public Collection<Location> readLocation(){
        return this.jpaQueryFactory.selectFrom(QLocation.location).fetch();
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public List<Location> create(List<Location> newEntities){

        for(Location newEntity : newEntities){
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
    public List<Location> update(List<Location> modifies){
        List<Location> request = new ArrayList<>();

        for(Location modify : modifies){
            Location locationmodify = this.jpaQueryFactory.getEntityManager().find(Location.class,modify.getId());

            if(modify.getLocationName() != null){
                locationmodify.setLocationName(modify.getLocationName());
            }
            if(modify.getNotes() != null){
                locationmodify.setNotes(modify.getNotes());
            }
            else
            {
                locationmodify.setNotes(null);
            }

            locationmodify.setLastUpdate(ZonedDateTime.now());

            request.add(locationmodify);
        }
        return request;
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public void destroy(List<Location> destroy){

        for(Location destroyLocation : destroy){
            Location locationdestroy = this.jpaQueryFactory.getEntityManager().find(Location.class,destroyLocation.getId());
            this.jpaQueryFactory.getEntityManager().remove(locationdestroy);

        }
    }
}
