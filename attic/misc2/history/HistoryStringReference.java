
public class HistoryStringReference extends HistoryReference implements java.io.Serializable {

	public HistoryStringReference() {
	}

	public HistoryStringReference(Object id) {
		super(id);
	}

	public HistoryStringReference(Object id, int minSize, int maxSize) {
		super(id, minSize, maxSize);
	}

	protected History createConcreteHistory(Object id) {
		return new HistoryString(id);
	}
}