
import gnu.regexp.*;

public class Capability {

	private String useragent;
	private String reuseragent;
	private RE regex;
	
	private String browser;
	private String version;
	private String majorver;
	private String minorver;
	private boolean frames;
	private boolean tables;
	private boolean cookies;
	private boolean backgroundsounds;
	private boolean vbscript;
	private boolean javascript;
	private boolean javaapplets;
	private String platform;
	private boolean beta;
	private boolean aol;
	private boolean ak;
	private boolean sk;
	private boolean cdf;
	private boolean win16;
	private boolean crawler;
	private boolean activexcontrols;
	private String authenticodeupdate;
	private String brand;
	private String msn;
	private boolean kit;
	private boolean fp;
	private String parent;
	private int height;
	private int width;
	
	public Capability(String reuseragent) throws REException {
		this.useragent = null;
		this.reuseragent = reuseragent;
		this.regex = new RE(reuseragent);
					
		this.browser = null;
		this.version = null;
		this.majorver = null;
		this.minorver = null;
		this.frames = false;
		this.tables = true;
		this.cookies = false; 
		this.backgroundsounds = false;
		this.vbscript = false;
		this.javascript = false;
		this.javaapplets = false;
		this.platform = null;
		this.activexcontrols = false;
		this.ak = false;
		this.sk = false;
		this.aol = false;
		this.beta = false;
		this.win16 = false;
		this.crawler = false;
		this.cdf = false;
		this.authenticodeupdate = null;
		this.brand = null;
		this.msn = null;
		this.kit = false;
		this.fp = false;
		this.parent = null;
		this.height = -1;
		this.width = -1;
	}
	
	public String toString() {
		return(browser + " " + version + " " + majorver + " " + minorver);
	}
	
	/*
	public Capability(String useragent, Capability parent) throws REException {
		this.useragent = useragent;
		this.regex = new RE(useragent);
		setParent(parent);		
	}
	*/
	
	public void setParent(Capability parent) {
		if (this.browser == null)
			this.browser = parent.getBrowser();
		
		if (this.version == null)	
			this.version = parent.getVersion();
		
		if (this.majorver == null)	
			this.majorver = parent.getMajorver();
		
		if (this.minorver == null)	
			this.minorver = parent.getMinorver();
		
		if (this.frames == false)	
			this.frames = parent.getFrames();
		
		if (this.tables == true)	
			this.tables = parent.getTables();
		
		if (this.cookies == false)			
			this.cookies = parent.getCookies(); 
		
		if (this.backgroundsounds == false)	
			this.backgroundsounds = parent.getBackgroundsounds();
		
		if (this.vbscript == false)	
			this.vbscript = parent.getVbscript();
		
		if (this.javascript == false)	
			this.javascript = parent.getJavascript();
		
		if (this.javaapplets == false)	
			this.javaapplets = parent.getJavaapplets();
		
		if (this.platform == null)	
			this.platform = parent.getPlatform();
			
		if (this.activexcontrols == false)	
			this.activexcontrols = parent.getActivexcontrols();
		
		if (this.ak == false)	
			this.ak = parent.getAK();
		
		if (this.sk == false)	
			this.sk = parent.getSK();
			
		if (this.aol == false) 	
			this.aol = parent.getAOL();
		
		if (this.beta == false)	
			this.beta = parent.getBeta();
		
		if (this.win16 == false)	
			this.win16 = parent.getWin16();
			
		if (this.crawler == false)	
			this.crawler = parent.getCrawler();
		
		if (this.cdf == false)	
			this.cdf = parent.getCDF();
		
		if (this.authenticodeupdate == null)	
			this.authenticodeupdate = parent.getAuthenticodeupdate();		
			
		if (this.brand == null)	
			this.brand = parent.getBrand();
		
		if (this.msn == null)	
			this.msn = parent.getMSN();
		
		if (this.kit == false)	
			this.kit = parent.getKIT();
		
		if (this.fp == false)	
			this.fp = parent.getFP();
	}
		
	public RE getRegex() {
		return regex;
	}
	
	public String getREUseragent() {
		return reuseragent;
	}
	
	public void setUseragent(String newAgent) {
		this.useragent = newAgent;
	}
	
	public String getUseragent() {
		return useragent;
	}
	
	public void setBrowser(String newBrowser) {
		this.browser = newBrowser;
	}
	
	public String getBrowser() {
		return this.browser;
	}
	
	public void setVersion(String newVersion) {
		this.version = newVersion;
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public void setMajorver(String newMajorver) {
		this.majorver = newMajorver;
	}
	
	public String getMajorver() {
		return this.majorver;
	}

	public void setMinorver(String newMinorver) {
		this.minorver = newMinorver;
	}
	
	public String getMinorver() {
		return this.minorver;
	}
	
	public void setFrames(boolean flag) {
		this.frames = flag;
	}
	
	public boolean getFrames() {
		return this.frames;
	}

	public void setTables(boolean flag) {
		this.tables = flag;
	}
	
	public boolean getTables() {
		return this.tables;
	}
	
	public void setCookies(boolean flag) {
		this.cookies = flag;
	}
	
	public boolean getCookies() {
		return this.cookies;
	}
	
	public void setBackgroundsounds(boolean flag) {
		this.backgroundsounds = flag;
	}	
	
	public boolean getBackgroundsounds() {
		return this.backgroundsounds;
	}

	public void setVbscript(boolean flag) {
		this.vbscript = flag;
	}	
	
	public boolean getVbscript() {
		return this.vbscript;
	}
	
	public void setJavascript(boolean flag) {
		this.javascript = flag;
	}
	
	public boolean getJavascript() {
		return this.javascript;
	}
	
	public void setJavaapplets(boolean flag) {
		this.javaapplets = flag;
	}
	
	public boolean getJavaapplets() {
		return this.javaapplets;
	}
	
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	public String getPlatform() {
		return platform;
	}
	
	public void setActivexcontrols(boolean flag) {
		this.activexcontrols = flag;
	}
	
	public boolean getActivexcontrols() {
		return this.activexcontrols;
	}
	
	public void setAK(boolean flag) {
		this.ak = flag;
	}
	
	public boolean getAK() {
		return this.ak;
	}

	public void setSK(boolean flag) {
		this.sk = flag;
	}
	
	public boolean getSK() {
		return this.sk;
	}

	public void setAOL(boolean flag) {
		this.aol = flag;
	}
	
	public boolean getAOL() {
		return this.aol;
	}
	
	public void setBeta(boolean flag) {
		this.beta = flag;
	}
	
	public boolean getBeta() {
		return this.beta;
	}
	
	public void setWin16(boolean flag) {
		this.win16 = flag;	
	}
	
	public boolean getWin16() {
		return this.win16;
	}
	
	public void setCrawler(boolean flag) {
		this.crawler = flag;
	}
	
	public boolean getCrawler() {
		return this.crawler;
	}

	public void setCDF(boolean flag) {
		this.cdf = flag;
	}
	
	public boolean getCDF() {
		return this.cdf;
	}
	
	public void setAuthenticodeupdate(String flag) {
		this.authenticodeupdate = flag;
	}
	
	public String getAuthenticodeupdate() {
		return this.authenticodeupdate;
	}
	
	public void setKIT(boolean flag) {
		this.kit = flag;
	}
	
	public boolean getKIT() {
		return this.kit;
	}
	
	public void setMSN(String msn) {
		this.msn = msn;
	}
	
	public String getMSN() {
		return msn;
	}
	
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getBrand() {
		return brand;
	}

	public void setFP(boolean flag) {
		this.fp = flag;
	}
	
	public boolean getFP() {
		return fp;
	}

	//special method
	String getParent() {
		return parent;
	}
	
	void setParent(String p) {
		this.parent = p;
	}
		
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
		
}