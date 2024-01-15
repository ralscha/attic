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
import ch.rasc.eds.starter.entity.inventory.QSopHeader;
import ch.rasc.eds.starter.entity.inventory.SopHeader;
import ch.rasc.edsutil.JPAQueryFactory;

/**
 * Created by kmkywar on 17/02/2015.
 */
@Service
@PreAuthorize("isAuthenticated()")
public class SophService {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public SophService(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @ExtDirectMethod(STORE_READ)
    @Transactional(readOnly = true)
    public Collection<SopHeader> sopheaderRead() {
        return this.jpaQueryFactory.selectFrom(QSopHeader.sopHeader).fetch();
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public List<SopHeader> sopheaderCreate(List<SopHeader> newSopHeaders) {

        for (SopHeader newSopHeader : newSopHeaders) {
            System.out.println("SOPHeader name: "+ newSopHeader.getName());
            newSopHeader.setId(null);
            this.jpaQueryFactory.getEntityManager().persist(newSopHeader);
        }
        return newSopHeaders;
    }

//    @ExtDirectMethod(STORE_MODIFY)
//    @Transactional
//    public List<SopHeader> sopheaderUpdate(List<SopHeader> modifiedSopHeaders)
//    {
//        for (SopHeader modifiedSopheader : modifiedSopHeaders){
//            entityManager.merge(modifiedSopheader);
//        }
//        return modifiedSopHeaders;
//    }
    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public List<SopHeader> sopheaderUpdate(List<SopHeader> modifiedSopHeaders) {

        List<SopHeader> result = new ArrayList<>();
        for (SopHeader modifiedSopHeader : modifiedSopHeaders) {

            SopHeader dbSopHeader = this.jpaQueryFactory.getEntityManager().find(SopHeader.class, modifiedSopHeader.getId());

            if (modifiedSopHeader.getName() != null) {
                dbSopHeader.setName(modifiedSopHeader.getName());
            }
            if (modifiedSopHeader.getPhone() != null) {
                dbSopHeader.setPhone(modifiedSopHeader.getPhone());
            }

            result.add(dbSopHeader);
        }

        return result;
    }

    @ExtDirectMethod(STORE_MODIFY)
    @Transactional
    public void sopheaderDestroy(List<SopHeader> destroySopHeaders) {

        for (SopHeader destroySopHeader : destroySopHeaders) {
            SopHeader c = this.jpaQueryFactory.getEntityManager().find(SopHeader.class, destroySopHeader.getId());
            this.jpaQueryFactory.getEntityManager().remove(c);
        }
    }
}
