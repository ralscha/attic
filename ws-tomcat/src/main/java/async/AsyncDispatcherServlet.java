package async;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@WebServlet(urlPatterns = { "/async/*" }, asyncSupported = true, name = "async")
public class AsyncDispatcherServlet extends DispatcherServlet {

	private static final long serialVersionUID = 1L;

	private ExecutorService exececutor;

	private static final int NUM_ASYNC_TASKS = 15;

	private static final long TIME_OUT = 10 * 1000;

	final Logger log = LoggerFactory.getLogger(AsyncDispatcherServlet.class);

	public AsyncDispatcherServlet() {
		super(new GenericWebApplicationContext());
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		exececutor = Executors.newFixedThreadPool(NUM_ASYNC_TASKS);
	}

	@Override
	public void destroy() {
		exececutor.shutdownNow();
		super.destroy();
	}

	@Override
	protected void doDispatch(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		final AsyncContext ac = request.startAsync(request, response);
		ac.setTimeout(TIME_OUT);
		FutureTask<Void> task = new FutureTask<>(() -> {
			try {
				log.debug("Dispatching request " + request);
				AsyncDispatcherServlet.super.doDispatch(request, response);
				log.debug("doDispatch returned from processing request " + request);
				ac.complete();
			}
			catch (Exception ex) {
				log.error("Error in async request", ex);
			}
			return null;
		});

		ac.addListener(new AsyncDispatcherServletListener(task));
		exececutor.execute(task);
	}

	private class AsyncDispatcherServletListener implements AsyncListener {

		private final FutureTask<?> future;

		public AsyncDispatcherServletListener(FutureTask<?> future) {
			this.future = future;
		}

		@Override
		public void onTimeout(AsyncEvent event) throws IOException {
			log.warn("Async request did not complete timeout occured");
			handleTimeoutOrError(event, "Request timed out");
		}

		@Override
		public void onComplete(AsyncEvent event) throws IOException {
			log.debug("Completed async request");
		}

		@Override
		public void onError(AsyncEvent event) throws IOException {
			String error = event.getThrowable() == null ? "UNKNOWN ERROR" : event
					.getThrowable().getMessage();
			log.error("Error in async request " + error);
			handleTimeoutOrError(event, "Error processing " + error);
		}

		@Override
		public void onStartAsync(AsyncEvent event) throws IOException {
			log.debug("Async Event started..");
		}

		@SuppressWarnings("resource")
		private void handleTimeoutOrError(AsyncEvent event, String message) {
			PrintWriter writer = null;
			try {
				future.cancel(true);
				HttpServletResponse response = (HttpServletResponse) event
						.getAsyncContext().getResponse();
				// HttpServletRequest request = (HttpServletRequest)
				// event.getAsyncContext().getRequest();
				// request.getRequestDispatcher("/app/error.htm").forward(request,
				// response);
				writer = response.getWriter();
				writer.print(message);
				writer.flush();
			}
			catch (Exception ex) {
				log.error("handleTimeoutOrError", ex);
			}
			finally {
				event.getAsyncContext().complete();
				if (writer != null) {
					writer.close();
				}
			}
		}
	}
}
