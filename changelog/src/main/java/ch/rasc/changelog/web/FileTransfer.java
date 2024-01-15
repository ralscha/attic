package ch.rasc.changelog.web;

import java.io.OutputStream;
import java.util.Date;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ch.rasc.changelog.entity.Customer;
import ch.rasc.changelog.entity.DocumentData;
import ch.rasc.changelog.entity.Documents;

@Controller
public class FileTransfer {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private MessageSource messageSource;

	@Transactional(readOnly = true)
	@RequestMapping(value = "/customerFileDownload", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void customerFileDownload(HttpServletResponse response,
			@RequestParam("fileId") final String fileId) throws Exception {
		if (!StringUtils.isEmpty(fileId)) {
			Documents document = this.entityManager.find(Documents.class,
					Long.valueOf(fileId));
			if (null != document) {
				response.setContentType(document.getContentType());
				try (OutputStream out = response.getOutputStream()) {
					Set<DocumentData> documentData = document.getDocumentData();
					if (!documentData.isEmpty()) {
						out.write(documentData.iterator().next().getData());
					}
				}
			}
		}
	}

	@RequestMapping(value = "/customerFileUpload", method = RequestMethod.POST,
			headers = "Accept=application/json")
	@Transactional
	public HttpEntity<String> customerFileUpload(Long id, MultipartFile file)
			throws Exception {

		boolean success = false;
		if (null != id) {
			Customer customer = this.entityManager.find(Customer.class, id);
			if (null != customer) {
				Documents document = new Documents();
				document.setContentType(file.getContentType());
				document.setFileName(file.getOriginalFilename());
				document.setSize(file.getSize());
				document.setDate(new Date());
				document.setCustomer(customer);

				DocumentData data = new DocumentData();
				data.setData(file.getBytes());
				data.setDocument(document);
				document.getDocumentData().add(data);

				customer.getDocuments().add(document);
				success = true;
			}
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_HTML);
		HttpEntity<String> result = new HttpEntity<>(
				"{\"success\":" + Boolean.toString(success) + "}", headers);
		return result;
	}

}
