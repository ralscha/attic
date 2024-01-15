

package ch.sr.pwgen;
public class PasswordGenRequest {
	
	private int nopassword;
	private int passwordlen;
	private String language;
	private boolean withNumber;
	private boolean withSpecial;
	
	private boolean nopwset = false;
	private boolean pwlenset = false;
	private boolean languageset = false;


	public void setWithNumberStr(String[] flag) {
		if (flag.length > 0) {
			withNumber = Boolean.valueOf(flag[0]).booleanValue();
		} else {
			withNumber = false;
		}
	}

	public void setWithSpecialStr(String[] flag) {
		if (flag.length > 0) {
			withSpecial = Boolean.valueOf(flag[0]).booleanValue();
		} else {
			withSpecial = false;
		}
	}

	public boolean isWithNumber() {
		return withNumber;
	}
	
	public boolean isWithSpecial() {
		return withSpecial;
	}

	public String getMode() {
		if (withNumber && withSpecial)
			return PasswordGen.MIX_MODE;
		else if (withNumber)
			return PasswordGen.NUMBER_MODE;
		else if (withSpecial)
			return PasswordGen.SPECIAL_MODE;
		else
			return PasswordGen.NORMAL_MODE;
	}

	public void setNopw(int nopw) {
		this.nopassword = nopw;
		nopwset = true;
	}

	public int getNopw() {
		return this.nopassword;
	}

	public void setPwlen(int pwlen) {
		this.passwordlen = pwlen;
		pwlenset = true;
	}
	
	public int getPwlen() {
		return this.passwordlen;
	} 
	
	public void setLanguage(String lang) {
		this.language = lang;
		languageset = true;
	}
	
	public String getLanguage() {
		return this.language;
	}
	
	public boolean isRequestedLanguage(String askLang) {
		if (language != null) {
			return (language.equals(askLang));
		}		
		return false;
	}
	
	public boolean isValuesSet() {
		return (pwlenset && nopwset && languageset);
	}

}
