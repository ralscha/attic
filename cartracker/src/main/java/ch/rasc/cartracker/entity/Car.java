package ch.rasc.cartracker.entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import ch.rasc.cartracker.Views;
import ch.rasc.cartracker.entity.option.Category;
import ch.rasc.cartracker.entity.option.Color;
import ch.rasc.cartracker.entity.option.DriveTrain;
import ch.rasc.cartracker.entity.option.Make;
import ch.rasc.cartracker.entity.option.Status;
import ch.rasc.edsutil.SortProperty;
import ch.rasc.extclassgenerator.Model;
import ch.rasc.extclassgenerator.ModelField;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.collect.Collections2;

@Entity
@Model(value = "CarTracker.model.Car", readMethod = "carService.readCars",
		createMethod = "carService.create", updateMethod = "carService.update",
		destroyMethod = "carService.destroy", paging = true)
public class Car extends BaseEntity {

	@JsonView(Views.Detail.class)
	private String description;

	@NotNull(message = "Please enter a Year")
	@ModelField(useNull = true)
	@JsonView(Views.Summary.class)
	private Integer year;

	@NotNull(message = "Please enter a List Price")
	@ModelField(useNull = true)
	@JsonView(Views.Summary.class)
	private Integer listPrice;

	@ModelField(useNull = true)
	@JsonView(Views.Summary.class)
	private Integer salePrice;

	@NotNull(message = "Please enter an Acquisition Date")
	@ModelField(dateFormat = "c")
	@JsonView(Views.Summary.class)
	private Date acquisitionDate;

	@ModelField(dateFormat = "c")
	@JsonView(Views.Summary.class)
	private Date saleDate;

	@Size(max = 50)
	@JsonView(Views.Detail.class)
	private String stockNumber;

	@Size(max = 50)
	@JsonView(Views.Detail.class)
	private String vin;

	@Size(max = 50)
	@JsonView(Views.Detail.class)
	private String fuel;

	@Size(max = 50)
	@JsonView(Views.Detail.class)
	private String engine;

	@Size(max = 50)
	@JsonView(Views.Detail.class)
	private String transmission;

	@ModelField(useNull = true)
	@JsonView(Views.Detail.class)
	private Integer mileage;

	@ModelField(defaultValue = "false")
	@JsonView(Views.Summary.class)
	private Boolean sold;

	@ManyToOne
	@JoinColumn(name = "statusId")
	@JsonIgnore
	private Status status;

	@Transient
	@ModelField(useNull = true)
	@JsonView(Views.Detail.class)
	private Long statusId;

	@Transient
	@ModelField(persist = false)
	@SortProperty("status.longName")
	@JsonView(Views.Summary.class)
	private String statusName;

	@ManyToOne
	@JoinColumn(name = "makeId")
	@JsonIgnore
	private Make make;

	@Transient
	@ModelField(useNull = true)
	@JsonView(Views.Detail.class)
	private Long makeId;

	@Transient
	@ModelField(persist = false)
	@SortProperty("make.longName")
	@JsonView(Views.Summary.class)
	private String makeName;

	@ManyToOne
	@JoinColumn(name = "modelId")
	@JsonIgnore
	private ch.rasc.cartracker.entity.option.Model model;

	@Transient
	@ModelField(useNull = true)
	@JsonView(Views.Detail.class)
	private Long modelId;

	@Transient
	@ModelField(persist = false)
	@SortProperty("model.longName")
	@JsonView(Views.Summary.class)
	private String modelName;

	@ManyToOne
	@JoinColumn(name = "categoryId")
	@JsonIgnore
	private Category category;

	@Transient
	@ModelField(useNull = true)
	@JsonView(Views.Detail.class)
	private Long categoryId;

	@Transient
	@ModelField(persist = false)
	@SortProperty("category.longName")
	@JsonView(Views.Summary.class)
	private String categoryName;

	@ManyToOne
	@JoinColumn(name = "colorId")
	@JsonIgnore
	private Color color;

	@Transient
	@ModelField(useNull = true)
	@JsonView(Views.Detail.class)
	private Long colorId;

	@Transient
	@ModelField(persist = false)
	@SortProperty("color.longName")
	@JsonView(Views.Summary.class)
	private String colorName;

	@ManyToOne
	@JoinColumn(name = "driveTrainId")
	@JsonIgnore
	private DriveTrain driveTrain;

	@Transient
	@ModelField(useNull = true)
	@JsonView(Views.Detail.class)
	private Long driveTrainId;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "car", orphanRemoval = true)
	@JsonIgnore
	private Set<CarFeature> carFeatures = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "car", orphanRemoval = true)
	@JsonIgnore
	private Set<CarStaff> carStaffs = new HashSet<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "car", orphanRemoval = true)
	@JsonIgnore
	private Set<CarImage> carImages = new HashSet<>();

	@ModelField
	@Transient
	@JsonView(Views.Detail.class)
	private Collection<Long> featureIds;

	@ModelField
	@Transient
	@JsonView(Views.Detail.class)
	private Collection<Long> salesPeopleIds;

	@ModelField
	@Transient
	@JsonView(Views.Detail.class)
	private Collection<Long> carImageIds;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getListPrice() {
		return listPrice;
	}

	public void setListPrice(Integer listPrice) {
		this.listPrice = listPrice;
	}

	public Integer getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}

	public Date getAcquisitionDate() {
		return acquisitionDate;
	}

	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = acquisitionDate;
	}

	public Date getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public String getStockNumber() {
		return stockNumber;
	}

	public void setStockNumber(String stockNumber) {
		this.stockNumber = stockNumber;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getFuel() {
		return fuel;
	}

	public void setFuel(String fuel) {
		this.fuel = fuel;
	}

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getTransmission() {
		return transmission;
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission;
	}

	public Integer getMileage() {
		return mileage;
	}

	public void setMileage(Integer mileage) {
		this.mileage = mileage;
	}

	public Boolean getSold() {
		return sold;
	}

	public void setSold(Boolean sold) {
		this.sold = sold;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Make getMake() {
		return make;
	}

	public void setMake(Make make) {
		this.make = make;
	}

	public Long getMakeId() {
		return makeId;
	}

	public void setMakeId(Long makeId) {
		this.makeId = makeId;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	public ch.rasc.cartracker.entity.option.Model getModel() {
		return model;
	}

	public void setModel(ch.rasc.cartracker.entity.option.Model model) {
		this.model = model;
	}

	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Long getColorId() {
		return colorId;
	}

	public void setColorId(Long colorId) {
		this.colorId = colorId;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public Set<CarFeature> getCarFeatures() {
		return carFeatures;
	}

	public void setCarFeatures(Set<CarFeature> carFeatures) {
		this.carFeatures = carFeatures;
	}

	public Set<CarStaff> getCarStaffs() {
		return carStaffs;
	}

	public void setCarStaffs(Set<CarStaff> carStaffs) {
		this.carStaffs = carStaffs;
	}

	public Collection<Long> getFeatureIds() {
		return featureIds;
	}

	public void setFeatureIds(Collection<Long> featureIds) {
		this.featureIds = featureIds;
	}

	public Collection<Long> getSalesPeopleIds() {
		return salesPeopleIds;
	}

	public void setSalesPeopleIds(Collection<Long> salesPeopleIds) {
		this.salesPeopleIds = salesPeopleIds;
	}

	public DriveTrain getDriveTrain() {
		return driveTrain;
	}

	public void setDriveTrain(DriveTrain driveTrain) {
		this.driveTrain = driveTrain;
	}

	public Long getDriveTrainId() {
		return driveTrainId;
	}

	public void setDriveTrainId(Long driveTrainId) {
		this.driveTrainId = driveTrainId;
	}

	public Set<CarImage> getCarImages() {
		return carImages;
	}

	public void setCarImages(Set<CarImage> carImages) {
		this.carImages = carImages;
	}

	public Collection<Long> getCarImageIds() {
		return carImageIds;
	}

	public void setCarImageIds(Collection<Long> carImageIds) {
		this.carImageIds = carImageIds;
	}

	public void populateDetail() {
		featureIds = Collections2.transform(carFeatures, input -> input.getFeature()
				.getId());

		salesPeopleIds = Collections2.transform(carStaffs, input -> input.getStaff()
				.getId());

		carImageIds = Collections2.transform(carImages, input -> input.getId());

		if (driveTrain != null) {
			driveTrainId = driveTrain.getId();
		}
		else {
			driveTrainId = null;
		}
	}

	@PostLoad
	@PostPersist
	@PostUpdate
	private void populate() {

		if (make != null) {
			makeName = make.getLongName();
			makeId = make.getId();
		}
		else {
			makeName = null;
			makeId = null;
		}

		if (model != null) {
			modelName = model.getLongName();
			modelId = model.getId();
		}
		else {
			modelName = null;
			modelId = null;
		}

		if (category != null) {
			categoryName = category.getLongName();
			categoryId = category.getId();
		}
		else {
			categoryName = null;
			categoryId = null;
		}

		if (status != null) {
			statusName = status.getLongName();
			statusId = status.getId();
		}
		else {
			statusName = null;
			statusId = null;
		}

		if (color != null) {
			colorName = color.getLongName();
			colorId = color.getId();
		}
		else {
			colorName = null;
			colorId = null;
		}

	}

}
