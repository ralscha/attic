package ch.sr.regextest;

public class Constants {

  public static final String EMAIL_REGEX =
    "^([A-Za-z0-9_]|\\-|\\.)+@([A-Za-z0-9_.]|\\-)+\\.\\w{2,4}(;([A-Za-z0-9_.]|\\-|\\.)+@([A-Za-z0-9_]|\\-)+\\.\\w{2,4})*$";

  public static final String EMAIL_REGEX1 = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
}
