package test;

import common.log.*;


public class General extends common.log.handler.Handler {
	private LogEventFormat format = new LogEventFormat();

	public static class ToPrint {
		public int i = 1;
		public double d = 2.222;
		public String s = "sssss";
		public ToPrint self;

		public int getI() {
			return i;
		}
		public double getD() {
			return d;
		}
		public String getS() {
			return s;
		}

		public ToPrint() {
			self = this;
		}
	}

	public static void main(String[] args) {
		System.getProperties().put("log.handler.dump.class", "grace.log.test.performance.Variety");

		{
			Timer timer = new Timer();
			timer.start();
			int numMessages = 1000;
			for (int i = 0; i < numMessages; ++i) {
				Log.error("a resonable error message");
			}
			timer.stop();

			System.out.println("performed " + numMessages +
                   			" Log.error(message) calls in " + timer.duration() + " seconds or " +
                   			(int)(numMessages / timer.duration()) + " calls/sec");
		}
		{
  		ToPrint toPrint = new ToPrint();
  		Timer timer = new Timer();
  		timer.start();
  		int numMessages = 1000;
		for (int i = 0; i < numMessages; ++i) {
  			Log.notice("object", toPrint);
  		}
  		timer.stop();

  		System.out.println("performed " + numMessages +
                     		" Log.notice(message, object) calls in " + timer.duration() +
                     		" seconds or " + (int)(numMessages / timer.duration()) + " calls/sec");
		}
		{
  		Exception exception = new Exception("an exception to test");
  		Timer timer = new Timer();
  		timer.start();
  		int numMessages = 1000;
		for (int i = 0; i < numMessages; ++i) {
  			Log.error("exception", exception);
  		}
  		timer.stop();

  		System.out.println("performed " + numMessages +
                     		" Log.exception(message, exception) calls in " + timer.duration() +
                     		" seconds or " + (int)(numMessages / timer.duration()) + " calls/sec");
		}
	}

	public General(String prefix, String name) {
		System.out.println("installed dump handler");
	}

	public void handle(LogEvent event) {
		String formatted = format.format(event);
	}
}