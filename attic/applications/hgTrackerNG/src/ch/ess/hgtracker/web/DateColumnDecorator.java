package ch.ess.hgtracker.web;

import java.text.SimpleDateFormat;
import org.displaytag.decorator.ColumnDecorator;

public class DateColumnDecorator implements ColumnDecorator {

  SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");

  public String decorate(Object columnValue) {
    return sdf.format(columnValue);
  }
}