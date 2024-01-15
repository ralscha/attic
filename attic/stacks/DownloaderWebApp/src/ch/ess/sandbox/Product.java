package ch.ess.sandbox;

import javax.persistence.Entity;
import ch.ess.downloader.model.BaseObject;

@Entity
public class Product extends BaseObject {

  String title;
  String description;
  float price;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

}