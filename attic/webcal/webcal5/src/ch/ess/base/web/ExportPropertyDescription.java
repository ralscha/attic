package ch.ess.base.web;

import java.io.Serializable;

import com.cc.framework.convert.Converter;
import com.cc.framework.ui.AlignmentType;

public class ExportPropertyDescription implements Serializable {
  private String title;
  private String propertyName;
  private int colWidth;
  private AlignmentType alignment;
  private Converter converter;

  public ExportPropertyDescription(String title, String propertyName) {
    this(title, propertyName, 3700, AlignmentType.LEFT);
  }

  public ExportPropertyDescription(String title, String propertyName, int colWidth) {
    this(title, propertyName, colWidth, AlignmentType.LEFT);
  }

  public ExportPropertyDescription(String title, String propertyName, int colWidth, AlignmentType alignment) {
    this.title = title;
    this.propertyName = propertyName;
    this.alignment = alignment;
    this.colWidth = colWidth;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public AlignmentType getAlignment() {
    return alignment;
  }

  public void setAlignment(AlignmentType alignment) {
    this.alignment = alignment;
  }

  public int getColWidth() {
    return colWidth;
  }

  public void setColWidth(int colWidth) {
    this.colWidth = colWidth;
  }

  public Converter getConverter() {
    return converter;
  }

  public void setConverter(Converter converter) {
    this.converter = converter;
  }
}