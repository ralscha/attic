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
import ch.rasc.eds.starter.entity.setup.Department;
import ch.rasc.eds.starter.entity.setup.QDepartment;
import ch.rasc.edsutil.JPAQueryFactory;
import ch.rasc.edsutil.QuerydslUtil;
@Service
public class DepartmentService {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public DepartmentService(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @ExtDirectMethod(STORE_READ)
    @Transactional(readOnly=true)
    public ExtDirectStoreResult<Department> read(ExtDirectStoreReadRequest request){

        JPQLQuery<Department> query = this.jpaQueryFactory.selectFrom(QDepartment.department);
        if(!request.getFilters().isEmpty()){
            StringFilter filter= (StringFilter) request.getFilters().iterator().next();
            BooleanBuilder bb= new BooleanBuilder();
            bb.or(QDepartment.department.departmentName.contains(filter.getValue()));
            bb.or(QDepartment.department.notes.contains(filter.getValue()));
            query.where(bb);
        }

        QuerydslUtil.addPagingAndSorting(query,request,Department.class,QDepartment.department);
        QueryResults<Department> searchResult = query.fetchResults();

        return new ExtDirectStoreResult<>(searchResult.getTotal(),searchResult.getResults());
    }


    @ExtDirectMethod(STORE_READ)
    @Transactional(readOnly=true)
    public Collection<Department> readDept(){
        return this.jpaQueryFactory.selectFrom(QDepartment.department).fetch();
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public List<Department> create(List<Department> newEntities){

        for(Department newEntity : newEntities){
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
    public List<Department> update(List<Department> modifies){
        List<Department> request = new ArrayList<>();

        for(Department modify : modifies){
            Department departmentmodify = this.jpaQueryFactory.getEntityManager().find(Department.class,modify.getId());

            if(modify.getDepartmentName() != null){
                departmentmodify.setDepartmentName(modify.getDepartmentName());
            }
            if(modify.getNotes() != null){
                departmentmodify.setNotes(modify.getNotes());
            }
            else
            {
                departmentmodify.setNotes(null);
            }

            departmentmodify.setLastUpdate(ZonedDateTime.now());

            request.add(departmentmodify);
        }
        return request;
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public void destroy(List<Department> destroy){

        for(Department destroyDepartment : destroy){
            Department departmentdestroy = this.jpaQueryFactory.getEntityManager().find(Department.class,destroyDepartment.getId());
            this.jpaQueryFactory.getEntityManager().remove(departmentdestroy);

        }
    }
}
