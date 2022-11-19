package ch.rasc.smninfo.domain;

import com.univocity.parsers.annotations.Convert;
import com.univocity.parsers.annotations.NullString;
import com.univocity.parsers.annotations.Parsed;

import ch.rasc.smninfo.util.EpochSecondsConversion;
import ch.rasc.smninfo.xodus.ExodusManager;
import jetbrains.exodus.entitystore.Entity;

public class DataPoint {

	@Parsed(field = "Station/Location")
	private String code;

	@Parsed(field = "Date")
	@Convert(conversionClass = EpochSecondsConversion.class)
	private long epochSeconds;

	/**
	 * °C: Air temperature 2 m above ground; current value
	 */
	@Parsed(field = "tre200s0")
	@NullString(nulls = "-")
	private Double temperature;

	/**
	 * min: Sunshine duration; ten minutes total
	 */
	@Parsed(field = "sre000z0")
	@NullString(nulls = "-")
	private Double sunshine;

	/**
	 * mm: Precipitation; ten minutes total
	 */
	@Parsed(field = "rre150z0")
	@NullString(nulls = "-")
	private Double precipitation;

	/**
	 * °: Wind direction; ten minutes mean
	 */
	@Parsed(field = "dkl010z0")
	@NullString(nulls = "-")
	private Double windDirection;

	/**
	 * km/h: Wind speed; ten minutes mean
	 */
	@Parsed(field = "fu3010z0")
	@NullString(nulls = "-")
	private Double windSpeed;

	/**
	 * hPa: Pressure reduced to sea level according to standard atmosphere (QNH); current
	 * value
	 */
	@Parsed(field = "pp0qnhs0")
	@NullString(nulls = "-")
	private Double qnhPressure;

	/**
	 * km/h: Gust peak (one second); maximum
	 */
	@Parsed(field = "fu3010z1")
	@NullString(nulls = "-")
	private Double gustPeak;

	/**
	 * %: Relative air humidity 2 m above ground; current value
	 */
	@Parsed(field = "ure200s0")
	@NullString(nulls = "-")
	private Double humidity;

	/**
	 * hPa: Pressure at station level (QFE); current value
	 */
	@Parsed(field = "prestas0")
	@NullString(nulls = "-")
	private Double qfePressure;

	/**
	 * hPa: Pressure reduced to sea level (QFF); current value
	 */
	@Parsed(field = "pp0qffs0")
	@NullString(nulls = "-")
	private Double qffPressure;

	/**
	 * W/m²: Global radiation; ten minutes mean
	 */
	@Parsed(field = "gre000z0")
	@NullString(nulls = "-")
	private Double globalRadiation;

	/**
	 * °C: Dew point 2 m above ground; current value
	 */
	@Parsed(field = "tde200s0")
	@NullString(nulls = "-")
	private Double dewPoint;

	/**
	 * gpm: geopotential height of the 850 hPa-surface; current value
	 */
	@Parsed(field = "ppz850s0")
	@NullString(nulls = "-")
	private Double geoPotentialHeight850;
	/**
	 * gpm: geopotential height of the 700 hPa-surface; current value
	 */
	@Parsed(field = "ppz700s0")
	@NullString(nulls = "-")
	private Double geoPotentialHeight700;

	/**
	 * °: Wind direction vectoriel; ten minutes interval; tool 1
	 */
	@Parsed(field = "dv1towz0")
	@NullString(nulls = "-")
	private Double windDirectionTool;

	/**
	 * km/h: Wind speed tower; ten minutes mean
	 */
	@Parsed(field = "fu3towz0")
	@NullString(nulls = "-")
	private Double windSpeedTower;

	/**
	 * km/h: Gust peak (one second) tower; maximum
	 */
	@Parsed(field = "fu3towz1")
	@NullString(nulls = "-")
	private Double gustPeakTower;

	/**
	 * °C: Air temperature tool 1
	 */
	@Parsed(field = "ta1tows0")
	@NullString(nulls = "-")
	private Double airTemperatureTool;

	/**
	 * %: Relative air humidity tower; current value
	 */
	@Parsed(field = "uretows0")
	@NullString(nulls = "-")
	private Double relAirHumidityTower;

	/**
	 * °C : Dew point tower
	 */
	@Parsed(field = "tdetows0")
	@NullString(nulls = "-")
	private Double dewPointTower;

	public DataPoint() {
		// default constructor
	}

	public DataPoint(Entity e) {
		this.code = getProperty(e, ExodusManager.CODE);
		this.epochSeconds = getProperty(e, ExodusManager.EPOCH_SECONDS);
		this.temperature = getProperty(e, "temperature");
		this.sunshine = getProperty(e, "sunshine");
		this.precipitation = getProperty(e, "precipitation");
		this.windDirection = getProperty(e, "windDirection");
		this.windSpeed = getProperty(e, "windSpeed");
		this.qnhPressure = getProperty(e, "qnhPressure");
		this.gustPeak = getProperty(e, "gustPeak");
		this.humidity = getProperty(e, "humidity");
		this.qfePressure = getProperty(e, "qfePressure");
		this.qffPressure = getProperty(e, "qffPressure");
		this.globalRadiation = getProperty(e, "globalRadiation");
		this.dewPoint = getProperty(e, "dewPoint");
		this.geoPotentialHeight850 = getProperty(e, "geoPotentialHeight850");
		this.geoPotentialHeight700 = getProperty(e, "geoPotentialHeight700");
		this.windDirectionTool = getProperty(e, "windDirectionTool");
		this.windSpeedTower = getProperty(e, "windSpeedTower");
		this.gustPeakTower = getProperty(e, "gustPeakTower");
		this.airTemperatureTool = getProperty(e, "airTemperatureTool");
		this.relAirHumidityTower = getProperty(e, "relAirHumidityTower");
		this.dewPointTower = getProperty(e, "dewPointTower");
	}

	private static <T> T getProperty(Entity e, String propertyName) {
		return (T) e.getProperty(propertyName);
	}

	public void toEntity(Entity e) {
		e.setProperty(ExodusManager.CODE, getCode());
		e.setProperty(ExodusManager.EPOCH_SECONDS, getEpochSeconds());

		if (getTemperature() != null) {
			e.setProperty("temperature", getTemperature());
		}

		if (getSunshine() != null) {
			e.setProperty("sunshine", getSunshine());
		}

		if (getPrecipitation() != null) {
			e.setProperty("precipitation", getPrecipitation());
		}

		if (getWindDirection() != null) {
			e.setProperty("windDirection", getWindDirection());
		}

		if (getWindSpeed() != null) {
			e.setProperty("windSpeed", getWindSpeed());
		}

		if (getQnhPressure() != null) {
			e.setProperty("qnhPressure", getQnhPressure());
		}

		if (getGustPeak() != null) {
			e.setProperty("gustPeak", getGustPeak());
		}

		if (getHumidity() != null) {
			e.setProperty("humidity", getHumidity());
		}

		if (getQfePressure() != null) {
			e.setProperty("qfePressure", getQfePressure());
		}

		if (getQffPressure() != null) {
			e.setProperty("qffPressure", getQffPressure());
		}

		if (getGlobalRadiation() != null) {
			e.setProperty("globalRadiation", getGlobalRadiation());
		}

		if (getDewPoint() != null) {
			e.setProperty("dewPoint", getDewPoint());
		}

		if (getGeoPotentialHeight850() != null) {
			e.setProperty("geoPotentialHeight850", getGeoPotentialHeight850());
		}

		if (getGeoPotentialHeight700() != null) {
			e.setProperty("geoPotentialHeight700", getGeoPotentialHeight700());
		}

		if (getWindDirectionTool() != null) {
			e.setProperty("windDirectionTool", getWindDirectionTool());
		}

		if (getWindSpeedTower() != null) {
			e.setProperty("windSpeedTower", getWindSpeedTower());
		}

		if (getGustPeakTower() != null) {
			e.setProperty("gustPeakTower", getGustPeakTower());
		}

		if (getAirTemperatureTool() != null) {
			e.setProperty("airTemperatureTool", getAirTemperatureTool());
		}

		if (getRelAirHumidityTower() != null) {
			e.setProperty("relAirHumidityTower", getRelAirHumidityTower());
		}

		if (getDewPointTower() != null) {
			e.setProperty("dewPointTower", getDewPointTower());
		}

	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public long getEpochSeconds() {
		return this.epochSeconds;
	}

	public void setEpochSeconds(long epochSeconds) {
		this.epochSeconds = epochSeconds;
	}

	public Double getTemperature() {
		return this.temperature;
	}

	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}

	public Double getSunshine() {
		return this.sunshine;
	}

	public void setSunshine(Double sunshine) {
		this.sunshine = sunshine;
	}

	public Double getPrecipitation() {
		return this.precipitation;
	}

	public void setPrecipitation(Double precipitation) {
		this.precipitation = precipitation;
	}

	public Double getWindDirection() {
		return this.windDirection;
	}

	public void setWindDirection(Double windDirection) {
		this.windDirection = windDirection;
	}

	public Double getWindSpeed() {
		return this.windSpeed;
	}

	public void setWindSpeed(Double windSpeed) {
		this.windSpeed = windSpeed;
	}

	public Double getQnhPressure() {
		return this.qnhPressure;
	}

	public void setQnhPressure(Double qnhPressure) {
		this.qnhPressure = qnhPressure;
	}

	public Double getGustPeak() {
		return this.gustPeak;
	}

	public void setGustPeak(Double gustPeak) {
		this.gustPeak = gustPeak;
	}

	public Double getHumidity() {
		return this.humidity;
	}

	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}

	public Double getQfePressure() {
		return this.qfePressure;
	}

	public void setQfePressure(Double qfePressure) {
		this.qfePressure = qfePressure;
	}

	public Double getQffPressure() {
		return this.qffPressure;
	}

	public void setQffPressure(Double qffPressure) {
		this.qffPressure = qffPressure;
	}

	public Double getGlobalRadiation() {
		return this.globalRadiation;
	}

	public void setGlobalRadiation(Double globalRadiation) {
		this.globalRadiation = globalRadiation;
	}

	public Double getDewPoint() {
		return this.dewPoint;
	}

	public void setDewPoint(Double dewPoint) {
		this.dewPoint = dewPoint;
	}

	public Double getGeoPotentialHeight850() {
		return this.geoPotentialHeight850;
	}

	public void setGeoPotentialHeight850(Double geoPotentialHeight850) {
		this.geoPotentialHeight850 = geoPotentialHeight850;
	}

	public Double getGeoPotentialHeight700() {
		return this.geoPotentialHeight700;
	}

	public void setGeoPotentialHeight700(Double geoPotentialHeight700) {
		this.geoPotentialHeight700 = geoPotentialHeight700;
	}

	public Double getWindDirectionTool() {
		return this.windDirectionTool;
	}

	public void setWindDirectionTool(Double windDirectionTool) {
		this.windDirectionTool = windDirectionTool;
	}

	public Double getWindSpeedTower() {
		return this.windSpeedTower;
	}

	public void setWindSpeedTower(Double windSpeedTower) {
		this.windSpeedTower = windSpeedTower;
	}

	public Double getGustPeakTower() {
		return this.gustPeakTower;
	}

	public void setGustPeakTower(Double gustPeakTower) {
		this.gustPeakTower = gustPeakTower;
	}

	public Double getAirTemperatureTool() {
		return this.airTemperatureTool;
	}

	public void setAirTemperatureTool(Double airTemperatureTool) {
		this.airTemperatureTool = airTemperatureTool;
	}

	public Double getRelAirHumidityTower() {
		return this.relAirHumidityTower;
	}

	public void setRelAirHumidityTower(Double relAirHumidityTower) {
		this.relAirHumidityTower = relAirHumidityTower;
	}

	public Double getDewPointTower() {
		return this.dewPointTower;
	}

	public void setDewPointTower(Double dewPointTower) {
		this.dewPointTower = dewPointTower;
	}

	@Override
	public String toString() {
		return "DataPoint [code=" + this.code + ", epochSeconds=" + this.epochSeconds
				+ ", temperature=" + this.temperature + ", sunshine=" + this.sunshine
				+ ", precipitation=" + this.precipitation + ", windDirection="
				+ this.windDirection + ", windSpeed=" + this.windSpeed + ", qnhPressure="
				+ this.qnhPressure + ", gustPeak=" + this.gustPeak + ", humidity="
				+ this.humidity + ", qfePressure=" + this.qfePressure + ", qffPressure="
				+ this.qffPressure + "]";
	}

}
