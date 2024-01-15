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
import ch.rasc.eds.starter.entity.setup.QSection;
import ch.rasc.eds.starter.entity.setup.Section;
import ch.rasc.edsutil.JPAQueryFactory;
import ch.rasc.edsutil.QuerydslUtil;

@Service
public class SectionService {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public SectionService(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @ExtDirectMethod(STORE_READ)
    @Transactional(readOnly=true)
    public ExtDirectStoreResult<Section> read(ExtDirectStoreReadRequest request){

        JPQLQuery<Section> query = this.jpaQueryFactory.selectFrom(QSection.section);
        if(!request.getFilters().isEmpty()){
            StringFilter filter= (StringFilter) request.getFilters().iterator().next();
            BooleanBuilder bb= new BooleanBuilder();
            bb.or(QSection.section.sectionName.contains(filter.getValue()));
            bb.or(QSection.section.notes.contains(filter.getValue()));
            query.where(bb);
        }

        QuerydslUtil.addPagingAndSorting(query,request,Section.class,QSection.section);
        QueryResults<Section> searchResult = query.fetchResults();

        return new ExtDirectStoreResult<>(searchResult.getTotal(),searchResult.getResults());
    }


    @ExtDirectMethod(STORE_READ)
    @Transactional(readOnly=true)
    public Collection<Section> readSection(){
        return this.jpaQueryFactory.selectFrom(QSection.section).fetch();
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public List<Section> create(List<Section> newEntities){

        for(Section newEntity : newEntities){
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
    public List<Section> update(List<Section> modifies){
        List<Section> request = new ArrayList<>();

        for(Section modify : modifies){
            Section sectionmodify = this.jpaQueryFactory.getEntityManager().find(Section.class,modify.getId());

            if(modify.getSectionName() != null){
                sectionmodify.setSectionName(modify.getSectionName());
            }
            if(modify.getNotes() != null){
                sectionmodify.setNotes(modify.getNotes());
            }
            else
            {
                sectionmodify.setNotes(null);
            }

            sectionmodify.setLastUpdate(ZonedDateTime.now());

            request.add(sectionmodify);
        }
        return request;
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public void destroy(List<Section> destroy){

        for(Section destroySection : destroy){
            Section sectiondestroy = this.jpaQueryFactory.getEntityManager().find(Section.class,destroySection.getId());
            this.jpaQueryFactory.getEntityManager().remove(sectiondestroy);

        }
    }
}
