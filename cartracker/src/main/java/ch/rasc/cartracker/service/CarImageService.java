package ch.rasc.cartracker.service;

import java.io.IOException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.rasc.cartracker.entity.Car;
import ch.rasc.cartracker.entity.CarImage;

@Service
public class CarImageService {

	@PersistenceContext
	private EntityManager entityManager;

	@ExtDirectMethod(value = ExtDirectMethodType.FORM_POST)
	@Transactional
	public ExtDirectFormPostResult uploadCarImage(
			@RequestParam("carImage") MultipartFile file, @RequestParam(value = "carId",
					required = false) Long carId) throws IOException {

		ExtDirectFormPostResult resp = new ExtDirectFormPostResult(true);

		if (file != null && !file.isEmpty()) {

			String lowerCaseFileName = file.getOriginalFilename().toLowerCase();
			if (lowerCaseFileName.endsWith(".gif") || lowerCaseFileName.endsWith(".png")
					|| lowerCaseFileName.endsWith(".jpg")
					|| lowerCaseFileName.endsWith(".jpeg")) {
				CarImage carImage = new CarImage();
				carImage.setActive(true);
				carImage.setCreateDate(new Date());
				carImage.setContentType(file.getContentType());
				carImage.setImagedata(file.getBytes());
				carImage.setEtag(DigestUtils.md5DigestAsHex(file.getBytes()));

				if (carId != null) {
					carImage.setCar(entityManager.getReference(Car.class, carId));
				}

				entityManager.persist(carImage);

				resp.addResultProperty("id", carImage.getId());
			}
			else {
				resp.setSuccess(false);
			}
		}

		return resp;
	}

}
