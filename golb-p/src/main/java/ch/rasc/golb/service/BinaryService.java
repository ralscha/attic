package ch.rasc.golb.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.validation.Validator;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.golb.Application;
import ch.rasc.golb.config.MongoDb;
import ch.rasc.golb.config.security.RequireAdminAuthority;
import ch.rasc.golb.entity.Binary;
import ch.rasc.golb.entity.CBinary;
import ch.rasc.golb.util.QueryUtil;
import ch.rasc.golb.util.ValidationMessages;
import ch.rasc.golb.util.ValidationMessagesResult;
import ch.rasc.golb.util.ValidationUtil;

@Service
@RequireAdminAuthority
public class BinaryService {

	private final Validator validator;

	private final MongoDb mongoDb;

	public BinaryService(MongoDb mongoDb, Validator validator) {
		this.mongoDb = mongoDb;
		this.validator = validator;
	}

	@ExtDirectMethod(STORE_READ)
	public ExtDirectStoreResult<Binary> read() {
		List<Binary> result = QueryUtil
				.toList(this.mongoDb.getCollection(Binary.class).find());
		return new ExtDirectStoreResult<>(result.size(), result);
	}

	@ExtDirectMethod(STORE_MODIFY)
	public ExtDirectStoreResult<Binary> destroy(Binary destroyBinary) {
		ExtDirectStoreResult<Binary> result = new ExtDirectStoreResult<>();

		Binary doc = this.mongoDb.getCollection(Binary.class)
				.findOneAndDelete(Filters.eq(CBinary.id, destroyBinary.getId()));

		if (doc != null && doc.getFileId() != null) {
			GridFSBucket bucket = this.mongoDb.createBucket("binary");
			bucket.delete(doc.getFileId());
		}

		result.setSuccess(Boolean.TRUE);
		return result;
	}

	@ExtDirectMethod(STORE_MODIFY)
	public ValidationMessagesResult<Binary> update(Binary updatedEntity) {

		List<ValidationMessages> violations = new ArrayList<>();
		violations.addAll(ValidationUtil.validateEntity(this.validator, updatedEntity));

		if (violations.isEmpty()) {
			List<Bson> updates = new ArrayList<>();
			updates.add(Updates.set(CBinary.name, updatedEntity.getName()));
			updates.add(Updates.set(CBinary.size, updatedEntity.getSize()));
			updates.add(Updates.set(CBinary.type, updatedEntity.getType()));
			updates.add(Updates.set(CBinary.lastModifiedDate,
					updatedEntity.getLastModifiedDate()));

			Binary doc = this.mongoDb.getCollection(Binary.class).findOneAndUpdate(
					Filters.eq(CBinary.id, updatedEntity.getId()),
					Updates.combine(updates), new FindOneAndUpdateOptions()
							.returnDocument(ReturnDocument.BEFORE).upsert(true));

			String data = updatedEntity.getData();
			if (StringUtils.hasText(data) && data.startsWith("data:")) {

				int pos = data.indexOf("base64,");
				if (pos != -1) {

					GridFSBucket bucket = this.mongoDb.createBucket("binary");
					if (doc != null && doc.getFileId() != null) {
						bucket.delete(doc.getFileId());
					}
					String extract = data.substring(pos + 7);
					byte[] bytes = Base64.getDecoder().decode(extract);

					try (ByteArrayInputStream source = new ByteArrayInputStream(bytes)) {
						ObjectId fileId = bucket.uploadFromStream(updatedEntity.getId(),
								source);

						this.mongoDb.getCollection(Binary.class).updateOne(
								Filters.eq(CBinary.id, updatedEntity.getId()),
								Updates.set(CBinary.fileId, fileId));
					}
					catch (IOException e) {
						Application.logger.error("upload document", e);
					}
				}
			}

			return new ValidationMessagesResult<>(updatedEntity);
		}

		ValidationMessagesResult<Binary> result = new ValidationMessagesResult<>(
				updatedEntity);
		result.setValidations(violations);
		return result;
	}

}
