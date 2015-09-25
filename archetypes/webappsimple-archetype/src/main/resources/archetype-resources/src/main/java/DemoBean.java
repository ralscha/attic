#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

public class DemoBean {
	private int counter = 0;

	public void inc() {
		this.counter++;
	}

	public int getCounter() {
		return this.counter;
	}
}