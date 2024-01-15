

public class Key {
	private String keyName;
	private boolean autoInc;

	public Key() {
		keyName = null;
		autoInc = false;
	}

	public Key(String name, boolean auto) {
		keyName = name;
		autoInc = auto;
	}

	public void setKeyName(String name) {
		keyName = name;
	}

	public void setAutoInc(boolean auto) {
		autoInc = auto;
	}

	public String getKeyName() {
		return keyName;
	}

	public boolean isAutoInc() {
		return autoInc;
	}
}