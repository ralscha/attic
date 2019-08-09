package ch.rasc.golb.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Validator;

import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.golb.config.MongoDb;
import ch.rasc.golb.config.security.RequireAdminAuthority;
import ch.rasc.golb.dto.View;
import ch.rasc.golb.entity.CPost;
import ch.rasc.golb.entity.CTag;
import ch.rasc.golb.entity.Post;
import ch.rasc.golb.entity.Tag;
import ch.rasc.golb.util.QueryUtil;
import ch.rasc.golb.util.ValidationMessages;
import ch.rasc.golb.util.ValidationMessagesResult;
import ch.rasc.golb.util.ValidationUtil;

@Service
@RequireAdminAuthority
public class TagService {

	private final Validator validator;

	private final MongoDb mongoDb;

	public TagService(MongoDb mongoDb, Validator validator) {
		this.mongoDb = mongoDb;
		this.validator = validator;
	}

	@ExtDirectMethod(value = STORE_READ, jsonView = View.Short.class)
	public List<Tag> readForCombo() {
		FindIterable<Tag> find = this.mongoDb.getCollection(Tag.class).find()
				.sort(Sorts.ascending(CTag.name));
		List<Tag> list = QueryUtil.toList(find);
		return list;
	}

	@ExtDirectMethod(STORE_READ)
	public ExtDirectStoreResult<Tag> read() {
		FindIterable<Tag> find = this.mongoDb.getCollection(Tag.class).find();
		List<Tag> list = QueryUtil.toList(find);

		list.forEach(t -> {
			t.setNoOfPosts(this.mongoDb.getCollection(Post.class)
					.count(Filters.eq(CPost.tags, t.getId())));
		});

		return new ExtDirectStoreResult<>(list.size(), list);
	}

	@ExtDirectMethod(STORE_MODIFY)
	@RequireAdminAuthority
	public ExtDirectStoreResult<Tag> destroy(Tag destroyTag) {
		ExtDirectStoreResult<Tag> result = new ExtDirectStoreResult<>();

		String tagId = destroyTag.getId();

		this.mongoDb.getCollection(Tag.class).deleteOne(Filters.eq(CTag.id, tagId));

		this.mongoDb.getCollection(Post.class).updateMany(Filters.eq(CPost.tags, tagId),
				Updates.pull(CPost.tags, tagId));

		result.setSuccess(Boolean.TRUE);
		return result;
	}

	@ExtDirectMethod(STORE_MODIFY)
	@RequireAdminAuthority
	public ValidationMessagesResult<Tag> update(Tag updatedEntity) {

		List<ValidationMessages> violations = new ArrayList<>();
		violations.addAll(ValidationUtil.validateEntity(this.validator, updatedEntity));

		if (violations.isEmpty()) {

			List<Bson> updates = new ArrayList<>();
			updates.add(Updates.set(CTag.name, updatedEntity.getName()));

			this.mongoDb.getCollection(Tag.class).updateOne(
					Filters.eq(CTag.id, updatedEntity.getId()), Updates.combine(updates),
					new UpdateOptions().upsert(true));

			return new ValidationMessagesResult<>(updatedEntity);
		}

		ValidationMessagesResult<Tag> result = new ValidationMessagesResult<>(
				updatedEntity);
		result.setValidations(violations);
		return result;
	}

}
