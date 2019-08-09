package ch.rasc.golb.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mongodb.client.model.Filters;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.golb.config.MongoDb;
import ch.rasc.golb.config.security.RequireAdminAuthority;
import ch.rasc.golb.entity.CFeedback;
import ch.rasc.golb.entity.CPost;
import ch.rasc.golb.entity.Feedback;
import ch.rasc.golb.entity.Post;
import ch.rasc.golb.util.QueryUtil;

@Service
@RequireAdminAuthority
public class FeedbackService {

	private final MongoDb mongoDb;

	public FeedbackService(MongoDb mongoDb) {
		this.mongoDb = mongoDb;
	}

	@ExtDirectMethod(STORE_READ)
	public List<Feedback> read() {
		List<Feedback> feedbacks = QueryUtil
				.toList(this.mongoDb.getCollection(Feedback.class).find());

		feedbacks.forEach(f -> {
			Post post = this.mongoDb.findFirst(Post.class, CPost.id, f.getPostId());
			if (post != null) {
				f.setPostTitle(post.getTitle());
			}
		});

		return feedbacks;
	}

	@ExtDirectMethod(STORE_MODIFY)
	public void destroy(String id) {
		this.mongoDb.getCollection(Feedback.class)
				.deleteOne(Filters.eq(CFeedback.id, id));
	}

}
