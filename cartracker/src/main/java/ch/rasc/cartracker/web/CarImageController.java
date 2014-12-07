package ch.rasc.cartracker.web;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.ralscha.extdirectspring.util.ExtDirectSpringUtil;
import ch.rasc.cartracker.entity.CarImage;
import ch.rasc.cartracker.entity.QCarImage;

import com.mysema.query.jpa.impl.JPAQuery;

@Controller
public class CarImageController {

	@PersistenceContext
	protected EntityManager entityManager;

	@Transactional(readOnly = true)
	@RequestMapping(method = RequestMethod.GET, value = "/carImage/{id}")
	public void serveCarImage(
			@RequestHeader(value = "If-None-Match", required = false) String ifNoneMatch,
			@PathVariable(value = "id") Long id, HttpServletResponse response)
			throws IOException {

		if (StringUtils.hasText(ifNoneMatch)) {
			String etagOnDb = new JPAQuery(entityManager).from(QCarImage.carImage)
					.where(QCarImage.carImage.id.eq(id))
					.singleResult(QCarImage.carImage.etag);
			if (etagOnDb.equals(ifNoneMatch)) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				return;
			}
		}

		CarImage carImage = entityManager.find(CarImage.class, id);
		response.setContentType(carImage.getContentType());
		ExtDirectSpringUtil.addCacheHeaders(response, carImage.getEtag(), 6);

		@SuppressWarnings("resource")
		ServletOutputStream out = response.getOutputStream();
		out.write(carImage.getImagedata());
		out.flush();

	}

}
