package ch.rasc.eds.starter.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.FORM_POST;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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

	@InitBinder
	protected void initBinder(ServletRequestDataBinder binder) throws Exception {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		CustomDateEditor editor = new CustomDateEditor(df, true);
		binder.registerCustomEditor(Date.class, editor);
	}

}
