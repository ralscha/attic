package ch.rasc.eds.starter.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.FORM_POST;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectFormPostResult;
import ch.rasc.eds.starter.bean.FormBean;

@Service
public class FormSubmitService {

	@ExtDirectMethod(FORM_POST)
	public ExtDirectFormPostResult handleFormSubmit(FormBean bean,
			MultipartFile screenshot) {

		String resultString = "Server received: \n" + bean.toString() + "\n";

		if (!screenshot.isEmpty()) {
			resultString += "ContentType: " + screenshot.getContentType() + "\n";
			resultString += "Size: " + screenshot.getSize() + "\n";
			resultString += "Name: " + screenshot.getOriginalFilename();
		}

		ExtDirectFormPostResult result = new ExtDirectFormPostResult();
		result.addResultProperty("response", resultString);
		return result;
	}

}
