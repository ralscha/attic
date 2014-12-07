package ch.rasc.packt.service;

import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_MODIFY;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.STORE_READ;
import static ch.ralscha.extdirectspring.annotation.ExtDirectMethodType.TREE_LOAD;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.rasc.packt.dto.MailMessage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;

@Service
@PreAuthorize("isAuthenticated()")
public class MailService {

	private final Map<Integer, MailMessage> messageDB = new ConcurrentHashMap<>();

	@PostConstruct
	public void init() throws IOException {
		MailMessage message = new MailMessage();
		message.setAttachment(true);
		message.setContent(Resources.toString(Resources.getResource("mail/1"),
				StandardCharsets.UTF_8));
		message.setFlag(true);
		message.setFolder("inbox");
		message.setFrom("senchainsights@swarmonline.com");
		message.setIcon("unread"); // read, reply, replyall, fwd
		message.setId(1);
		message.setImportance(false);
		message.setReceived(new Date());
		message.setSubject("Sencha Insights - Issue #21 - Merry Christmas!");
		messageDB.put(1, message);

		message = new MailMessage();
		message.setAttachment(false);
		message.setContent(Resources.toString(Resources.getResource("mail/2"),
				StandardCharsets.UTF_8));
		message.setFlag(false);
		message.setFolder("inbox");
		message.setFrom("noreply@github.com");
		message.setIcon("read"); // read, reply, replyall, fwd
		message.setId(2);
		message.setImportance(true);
		message.setReceived(new Date());
		message.setSubject("GitHub explore today Dec 26");
		messageDB.put(2, message);

		message = new MailMessage();
		message.setAttachment(false);
		message.setContent(Resources.toString(Resources.getResource("mail/3"),
				StandardCharsets.UTF_8));
		message.setFlag(true);
		message.setFolder("inbox");
		message.setFrom("news@sencha.com");
		message.setIcon("fwd"); // read, reply, replyall, fwd
		message.setId(3);
		message.setImportance(false);
		message.setReceived(new Date());
		message.setSubject("Architect 3 Theming and Extensions, Ext JS Tips, PhoneGap with Sencha Touch, and more");
		messageDB.put(3, message);

		message = new MailMessage();
		message.setAttachment(false);
		message.setContent(Resources.toString(Resources.getResource("mail/4"),
				StandardCharsets.UTF_8));
		message.setFlag(false);
		message.setFolder("inbox");
		message.setFrom("senchainsights@swarmonline.com");
		message.setIcon("replyall"); // read, reply, replyall, fwd
		message.setId(4);
		message.setImportance(false);
		message.setReceived(new Date());
		message.setSubject("Sencha Insights - Issue #20");
		messageDB.put(4, message);

	}

	@ExtDirectMethod(TREE_LOAD)
	public List<Map<String, Object>> readMenu() {

		ImmutableList.Builder<Map<String, Object>> builder = ImmutableList.builder();

		builder.add(ImmutableMap.<String, Object> of("text", "Inbox", "iconCls",
				"folder-inbox", "expanded", Boolean.TRUE, "leaf", Boolean.TRUE));

		builder.add(ImmutableMap.<String, Object> of("text", "Sent", "iconCls",
				"folder-sent", "leaf", Boolean.TRUE));

		builder.add(ImmutableMap.<String, Object> of("text", "Draft", "iconCls",
				"folder-drafts", "leaf", Boolean.TRUE));

		builder.add(ImmutableMap.<String, Object> of("text", "Trash", "iconCls",
				"folder-trash", "leaf", Boolean.TRUE));
		return builder.build();
	}

	@ExtDirectMethod(STORE_READ)
	public Collection<MailMessage> read() {
		return messageDB.values();
	}

	@ExtDirectMethod(STORE_MODIFY)
	public void update(MailMessage message) {
		messageDB.get(message.getId()).setFolder(message.getFolder());
	}

	@ExtDirectMethod
	public String loadContent(int id) {
		return messageDB.get(id).getContent();
	}

}
