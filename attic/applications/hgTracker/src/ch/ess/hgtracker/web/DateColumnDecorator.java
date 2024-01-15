package ch.ess.hgtracker.web;

import java.text.SimpleDateFormat;
import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.exception.DecoratorException;

public class DateColumnDecorator implements ColumnDecorator{
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public String decorate(Object columnValue) throws DecoratorException
    {
        return sdf.format(columnValue);
    }
}