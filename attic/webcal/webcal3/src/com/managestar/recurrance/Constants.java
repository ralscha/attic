package com.managestar.recurrance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Constants {

  public static Set<String> freqValues;
  public static Set<String> weekDayValues;

  public static List<Integer> allMonths = new ArrayList<Integer>();
  public static List<Integer> allYearDays = new ArrayList<Integer>();
  public static List<Integer> allMonthDays = new ArrayList<Integer>();
  public static List<Integer> allWeekDays = new ArrayList<Integer>();
  public static List<Integer> allYearWeeks = new ArrayList<Integer>();
  public static List<Integer> allHours = new ArrayList<Integer>();
  public static List<Integer> allMinutes = new ArrayList<Integer>();
  public static List<Integer> allSeconds = new ArrayList<Integer>();

  static {
    //--- valid frequencies
    freqValues = new HashSet<String>();
    freqValues.add("SECONDLY");
    freqValues.add("MINUTELY");
    freqValues.add("HOURLY");
    freqValues.add("DAILY");
    freqValues.add("WEEKLY");
    freqValues.add("MONTHLY");
    freqValues.add("YEARLY");

    //--- valid days of the week
    weekDayValues = new HashSet<String>();
    weekDayValues.add("SU");
    weekDayValues.add("MO");
    weekDayValues.add("TU");
    weekDayValues.add("WE");
    weekDayValues.add("TH");
    weekDayValues.add("FR");
    weekDayValues.add("SA");

    int ctr;

    for (ctr = 0; ctr < 12; ctr++) {
      allMonths.add(ctr); // months are irritating
    }

    for (ctr = 1; ctr < 367; ctr++) {
      allYearDays.add(ctr); // 366 days possible!
    }

    for (ctr = 1; ctr < 32; ctr++) {
      allMonthDays.add(ctr);
    }

    for (ctr = 1; ctr < 8; ctr++) {
      allWeekDays.add(ctr);
    }

    for (ctr = 1; ctr < 54; ctr++) {
      allYearWeeks.add(ctr); // week 53 possible!
    }

    for (ctr = 0; ctr < 24; ctr++) {
      allHours.add(ctr);
    }

    for (ctr = 0; ctr < 60; ctr++) {
      allMinutes.add(ctr);
    }

    for (ctr = 0; ctr < 60; ctr++) {
      allSeconds.add(ctr);
    }

  }

  public static final int FREQ_SECONDLY = 1;
  public static final int FREQ_MINUTELY = 2;
  public static final int FREQ_HOURLY = 3;
  public static final int FREQ_DAILY = 4;
  public static final int FREQ_WEEKLY = 5;
  public static final int FREQ_MONTHLY = 6;
  public static final int FREQ_YEARLY = 7;

  public static int convertFreqNameToFreqNumber(String freq) {
    if ("SECONDLY".equals(freq)) {
      return FREQ_SECONDLY;
    }
    if ("MINUTELY".equals(freq)) {
      return FREQ_MINUTELY;
    }
    if ("HOURLY".equals(freq)) {
      return FREQ_HOURLY;
    }
    if ("DAILY".equals(freq)) {
      return FREQ_DAILY;
    }
    if ("WEEKLY".equals(freq)) {
      return FREQ_WEEKLY;
    }
    if ("MONTHLY".equals(freq)) {
      return FREQ_MONTHLY;
    }
    if ("YEARLY".equals(freq)) {
      return FREQ_YEARLY;
    }
    throw new IllegalArgumentException("  ERROR: " + freq + " is not a valid FREQ type");
  }

  public static int convertFreqNameToCalendarField(String freq) {
    if ("SECONDLY".equals(freq)) {
      return Calendar.SECOND;
    }
    if ("MINUTELY".equals(freq)) {
      return Calendar.MINUTE;
    }
    if ("HOURLY".equals(freq)) {
      return Calendar.HOUR;
    }
    if ("DAILY".equals(freq)) {
      return Calendar.DAY_OF_MONTH;
    }
    if ("WEEKLY".equals(freq)) {
      return Calendar.WEEK_OF_YEAR;
    }
    if ("MONTHLY".equals(freq)) {
      return Calendar.MONTH;
    }
    if ("YEARLY".equals(freq)) {
      return Calendar.YEAR;
    }
    throw new IllegalArgumentException("  ERROR: " + freq + " is not a valid FREQ type");
  }

  public static int convertDayNameToDayNumber(String day) {
    if ("SU".equals(day)) {
      return Calendar.SUNDAY;
    }
    if ("MO".equals(day)) {
      return Calendar.MONDAY;
    }
    if ("TU".equals(day)) {
      return Calendar.TUESDAY;
    }
    if ("WE".equals(day)) {
      return Calendar.WEDNESDAY;
    }
    if ("TH".equals(day)) {
      return Calendar.THURSDAY;
    }
    if ("FR".equals(day)) {
      return Calendar.FRIDAY;
    }
    if ("SA".equals(day)) {
      return Calendar.SATURDAY;
    }
    throw new IllegalArgumentException("  ERROR: " + day + " is not a valid week day");
  }

  public static String convertDayNumberToDayName(int day) {
    if (Calendar.SUNDAY == day) {
      return "SU";
    }
    if (Calendar.MONDAY == day) {
      return "MO";
    }
    if (Calendar.TUESDAY == day) {
      return "TU";
    }
    if (Calendar.WEDNESDAY == day) {
      return "WE";
    }
    if (Calendar.THURSDAY == day) {
      return "TH";
    }
    if (Calendar.FRIDAY == day) {
      return "FR";
    }
    if (Calendar.SATURDAY == day) {
      return "SA";
    }
    throw new IllegalArgumentException("  ERROR: " + day + " is not a valid week day number");
  }

}
