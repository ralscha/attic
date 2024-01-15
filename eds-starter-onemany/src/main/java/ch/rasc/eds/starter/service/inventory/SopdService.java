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
import ch.rasc.eds.starter.entity.inventory.QSopDetail;
import ch.rasc.eds.starter.entity.inventory.SopDetail;
import ch.rasc.eds.starter.entity.inventory.SopHeader;
import ch.rasc.edsutil.JPAQueryFactory;

/**
 * Created by kmkywar on 17/02/2015.
 */
@Service
@PreAuthorize("isAuthenticated()")
public class SopdService {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public SopdService(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @ExtDirectMethod(STORE_READ)
    @Transactional(readOnly=true)
    public Collection<SopDetail> sopdetailRead(ExtDirectStoreReadRequest request) {

        NumericFilter customeridFilter = request.getFirstFilterForField("sopHeaderId");
        Number customerId = customeridFilter.getValue();
        List<SopDetail> sopDetails = this.jpaQueryFactory.selectFrom(QSopDetail.sopDetail)
                .where(QSopDetail.sopDetail.sopheader.id.eq(customerId.longValue()))
                .fetch();

        for (SopDetail sopDetail : sopDetails) {
            sopDetail.setSopHeaderId(sopDetail.getSopheader().getId());
        }
        return sopDetails;
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public List<SopDetail> sopdetailCreate(List<SopDetail> newSopdetails) {


        for (SopDetail newSopDetail : newSopdetails) {
            System.out.println("create Sopdetail id : "+ newSopDetail.getId() +" |date: "+newSopDetail.getOrderdate());
            newSopDetail.setId(null);
            newSopDetail.setSopheader(this.jpaQueryFactory.getEntityManager().getReference(SopHeader.class,
                    newSopDetail.getSopHeaderId()));
            newSopDetail.setSopHeaderId(newSopDetail.getSopheader().getId());
            this.jpaQueryFactory.getEntityManager().persist(newSopDetail);
        }
        return newSopdetails;
    }

//    @ExtDirectMethod(STORE_MODIFY)
//    @Transactional
//    public List<SopDetail> sopdetailUpdate(List<SopDetail> modifiedSopdetails) {
//
//        for (SopDetail modifiedsopdetail : modifiedSopdetails){
//            modifiedSopdetail.setSopHeader(entityManager.getReference(SopHeader.class,modifiedSopdetail.getCustomerId()));
//            entityManager.merge(modifiedSopdetail);
//        }
//        return modifiedSopdetails;
//    }


    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public List<SopDetail> sopdetailUpdate(List<SopDetail> modifiedSopdetails) {

        List<SopDetail> result = new ArrayList<>();

        for (SopDetail modefiedSopdetail : modifiedSopdetails) {

            System.out.println("update SOPDetail hd_Id: " + modefiedSopdetail.getSopHeaderId() +" detaiId: " +modefiedSopdetail.getId());

            SopDetail dbsopDetail = this.jpaQueryFactory.getEntityManager().find(SopDetail.class, modefiedSopdetail.getId());

            modefiedSopdetail.setSopheader(dbsopDetail.getSopheader());

            if (modefiedSopdetail.getShipped() != null) {
                dbsopDetail.setShipped(modefiedSopdetail.getShipped());
            }
            if (modefiedSopdetail.getOrderdate() != null) {
                dbsopDetail.setOrderdate(modefiedSopdetail.getOrderdate());
            }

            dbsopDetail.setSopHeaderId(dbsopDetail.getSopheader().getId());
            result.add(dbsopDetail);
        }
        return result;
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public void sopdetailDestroy(List<SopDetail> destroySopdetails) {

        for (SopDetail destroySopdetail : destroySopdetails) {
            SopDetail sop = this.jpaQueryFactory.getEntityManager().find(SopDetail.class, destroySopdetail.getId());
            this.jpaQueryFactory.getEntityManager().remove(sop);
        }
    }
}
