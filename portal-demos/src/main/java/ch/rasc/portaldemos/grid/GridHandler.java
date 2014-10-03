package ch.rasc.portaldemos.grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.github.flowersinthesand.portal.Bean;
import com.github.flowersinthesand.portal.Data;
import com.github.flowersinthesand.portal.On;
import com.github.flowersinthesand.portal.Reply;
import com.github.flowersinthesand.portal.Room;
import com.github.flowersinthesand.portal.Socket;
import com.github.flowersinthesand.portal.Wire;
import com.google.common.collect.Ordering;

@Bean
public class GridHandler {

	@Wire
	Room hall;

	@On
	@Reply
	public Collection<Book> bookRead(@Data StoreReadRequest readRequest) throws Throwable {
		Collection<Book> list = BookDb.list();
		Ordering<Book> ordering = PropertyOrderingFactory
				.createOrderingFromSorters(readRequest.getSorters());

		return ordering != null ? ordering.sortedCopy(list) : list;
	}

	@On
	@Reply
	public List<Book> bookCreate(Socket socket, @Data Book[] books) throws Throwable {
		List<Book> result = new ArrayList<>();
		for (Book book : books) {
			BookDb.create(book);
			result.add(book);
		}

		hall.out(socket).send("bookCreated", result);
		return result;
	}

	@On
	@Reply
	public List<Book> bookUpdate(Socket socket, @Data Book[] books) throws Throwable {
		List<Book> result = new ArrayList<>();
		for (Book book : books) {
			BookDb.update(book);
			result.add(book);
		}

		hall.out(socket).send("bookUpdated", result);
		return result;
	}

	@On
	@Reply
	public boolean bookDestroy(Socket socket, @Data Integer[] bookIds) throws Throwable {
		BookDb.delete(Arrays.asList(bookIds));
		hall.out(socket).send("bookDestroyed", bookIds);
		return true;
	}

}
