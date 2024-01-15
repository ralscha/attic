package common.log;

import java.util.EventObject;
import common.log.handler.Handler;

public class LogMulticaster extends Handler {
	protected final Handler a, b;

	protected LogMulticaster(Handler a, Handler b) {
		this.a = a;
		this.b = b;
	}

	public void handle(LogEvent event) {
		a.handle(event);
		b.handle(event);
	}

	public static Handler add(Handler a, Handler b) {
		return (a == null) ? b : (b == null) ? a : new LogMulticaster(a, b);
	}

	public static Handler remove(Handler list, Handler remove_me) {
		if (list == remove_me || list == null)
			return null;
		else if (!(list instanceof LogMulticaster))
			return list;
		else
			return ((LogMulticaster) list).remove(remove_me);
	}

	private Handler remove(Handler remove_me) {
		if (remove_me == a)
			return b;
		if (remove_me == b)
			return a;

		Handler a2 = remove(a, remove_me);
		Handler b2 = remove(b, remove_me);

		return (a2 == a && b2 == b)// it's not here
       		? this : add(a2, b2);
	}
}