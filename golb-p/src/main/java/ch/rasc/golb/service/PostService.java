package ch.rasc.golb.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.validation.Validator;

import org.bson.conversions.Bson;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.Updates;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreResult;
import ch.rasc.golb.config.MongoDb;
import ch.rasc.golb.config.security.RequireAdminAuthority;
import ch.rasc.golb.entity.CPost;
import ch.rasc.golb.entity.CTag;
import ch.rasc.golb.entity.Post;
import ch.rasc.golb.entity.Tag;
import ch.rasc.golb.pub.SitemapController;
import ch.rasc.golb.util.QueryUtil;
import ch.rasc.golb.util.Util;
import ch.rasc.golb.util.ValidationMessages;
import ch.rasc.golb.util.ValidationMessagesResult;
import ch.rasc.golb.util.ValidationUtil;

@Service
@RequireAdminAuthority
public class PostService {

	private final Validator validator;

	private final MongoDb mongoDb;

	private final SitemapController sitemapController;

	public PostService(MongoDb mongoDb, Validator validator,
			SitemapController sitemapController) {
		this.mongoDb = mongoDb;
		this.validator = validator;
		this.sitemapController = sitemapController;
	}

	@ExtDirectMethod
	public boolean isSlugUnique(String postId, String slug) {
		return this.mongoDb.getCollection(Post.class).count(Filters
				.and(Filters.ne(CPost.id, postId), Filters.eq(CPost.slug, slug))) == 0;
	}

	@ExtDirectMethod(STORE_READ)
	public ExtDirectStoreResult<Post> read() {
		List<Post> result = QueryUtil
				.toList(this.mongoDb.getCollection(Post.class).find());
		return new ExtDirectStoreResult<>(result.size(), result);
	}

	@ExtDirectMethod(STORE_MODIFY)
	public ExtDirectStoreResult<Post> destroy(Post destroyPost) {
		ExtDirectStoreResult<Post> result = new ExtDirectStoreResult<>();

		this.mongoDb.getCollection(Post.class)
				.deleteOne(Filters.eq(CPost.id, destroyPost.getId()));
		result.setSuccess(Boolean.TRUE);
		return result;
	}

	@ExtDirectMethod
	public Long publish(String postId) {
		Post updatedPost = this.mongoDb.getCollection(Post.class).findOneAndUpdate(
				Filters.eq(CPost.id, postId), Updates.set(CPost.published, new Date()),
				new FindOneAndUpdateOptions().upsert(false)
						.returnDocument(ReturnDocument.AFTER));

		if (updatedPost != null) {
			this.sitemapController.pingSearchEngines();
			return updatedPost.getPublished().getTime();
		}

		return null;
	}

	@ExtDirectMethod
	public boolean unpublish(String postId) {
		Post updatedPost = this.mongoDb.getCollection(Post.class).findOneAndUpdate(
				Filters.eq(CPost.id, postId), Updates.unset(CPost.published),
				new FindOneAndUpdateOptions().upsert(false)
						.returnDocument(ReturnDocument.AFTER));

		return updatedPost != null;
	}

	@ExtDirectMethod(STORE_MODIFY)
	public ValidationMessagesResult<Post> update(Post updatedEntity) {

		List<ValidationMessages> violations = new ArrayList<>();
		violations.addAll(ValidationUtil.validateEntity(this.validator, updatedEntity));

		if (!isSlugUnique(updatedEntity.getId(), updatedEntity.getSlug())) {
			ValidationMessages error = new ValidationMessages();
			error.setField("slug");
			error.setMessages(new String[] { "Slug is not unique" });
			violations.add(error);
		}

		if (violations.isEmpty()) {

			if (Util.isNotEmpty(updatedEntity.getTags())) {
				MongoCollection<Tag> tagColl = this.mongoDb.getCollection(Tag.class);
				List<String> newTagIds = new ArrayList<>(updatedEntity.getTags().size());
				for (String tagId : updatedEntity.getTags()) {
					if (tagColl.count(Filters.eq(CTag.id, tagId)) == 0) {
						Bson updates = Updates.combine(Updates.setOnInsert(CTag.id,
								UUID.randomUUID().toString()));
						Tag tag = tagColl.findOneAndUpdate(Filters.eq(CTag.name, tagId),
								updates, new FindOneAndUpdateOptions().upsert(true)
										.returnDocument(ReturnDocument.AFTER));
						newTagIds.add(tag.getId());
					}
					else {
						newTagIds.add(tagId);
					}
				}
				updatedEntity.setTags(newTagIds);
			}

			List<Bson> updates = new ArrayList<>();
			updates.add(Updates.set(CPost.title, updatedEntity.getTitle()));
			updates.add(Updates.set(CPost.summary, updatedEntity.getSummary()));
			updates.add(Updates.set(CPost.slug, updatedEntity.getSlug()));
			updates.add(Updates.set(CPost.body, updatedEntity.getBody()));
			updates.add(Updates.set(CPost.bodyHtml, updatedEntity.getBodyHtml()));

			if (Util.isNotEmpty(updatedEntity.getTags())) {
				updates.add(Updates.set(CPost.tags, updatedEntity.getTags()));
			}
			else {
				updates.add(Updates.unset(CPost.tags));
			}
			updates.add(Updates.setOnInsert(CPost.created, new Date()));

			updatedEntity.setUpdated(new Date());
			updates.add(Updates.set(CPost.updated, updatedEntity.getUpdated()));

			this.mongoDb.getCollection(Post.class).updateOne(
					Filters.eq(CPost.id, updatedEntity.getId()), Updates.combine(updates),
					new UpdateOptions().upsert(true));

			return new ValidationMessagesResult<>(updatedEntity);
		}

		ValidationMessagesResult<Post> result = new ValidationMessagesResult<>(
				updatedEntity);
		result.setValidations(violations);
		return result;
	}

}
