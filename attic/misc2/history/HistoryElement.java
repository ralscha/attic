

public class HistoryElement implements java.io.Serializable {
	private Object obj;
	private Long lastDate;

	HistoryElement(Object obj, Long last) {
		this.obj = obj;
		this.lastDate = last;
	}

	HistoryElement(Object obj) {
		this(obj, new Long(System.currentTimeMillis()));
	}

	public Object getObject() {
		return obj;
	}

	public Long getLastDate() {
		return lastDate;
	}

	public boolean equals(Object obj) {

		try {
			HistoryElement element = (HistoryElement) obj;

			if (this.getObject().equals(element.getObject())) {
				return true;
			}
		} catch (ClassCastException cce) {
			return false;
		}

		return false;
	}

	public int hashCode() {
		return obj.hashCode();
	}
}