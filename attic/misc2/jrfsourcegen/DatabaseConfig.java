
public class DatabaseConfig {

	private String driver;
	private String url;
	private String user;
	private String password;
	private String outputDir;
	private String packageName;
  private String policy;

	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public String getPackageName() {
		return packageName;
	}

  public String getPolicy() {
    return policy;
  }

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

  public void setPolicy(String policy) {
    this.policy = policy;
  }
}