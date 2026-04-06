import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.univocity.parsers.annotations.Convert;
import com.univocity.parsers.annotations.NullString;
import com.univocity.parsers.annotations.Parsed;

public class Station {

	@Parsed(field = "stn")
	private String code;

	@Parsed(field = "time")
	@Convert(conversionClass = ZonedDateTimeConversion.class)
	private ZonedDateTime dateTime;

	/**
	 * °C: Air temperature 2 m above ground; current value
	 */
	@Parsed(field = "tre200s0")
	@NullString(nulls = "-")
	private BigDecimal temperature;

	/**
	 * min: Sunshine duration; ten minutes total
	 */
	@Parsed(field = "sre000z0")
	@NullString(nulls = "-")
	private Integer sunshine;

	/**
	 * mm: Precipitation; ten minutes total
	 */
	@Parsed(field = "rre150z0")
	@NullString(nulls = "-")
	private BigDecimal precipitation;

	/**
	 * °: Wind direction; ten minutes mean
	 */
	@Parsed(field = "dkl010z0")
	@NullString(nulls = "-")
	private Integer windDirection;

	/**
	 * km/h: Wind speed; ten minutes mean
	 */
	@Parsed(field = "fu3010z0")
	@NullString(nulls = "-")
	private BigDecimal windSpeed;

	/**
	 * hPa: Pressure reduced to sea level according to standard atmosphere (QNH); current
	 * value
	 */
	@Parsed(field = "pp0qnhs0")
	@NullString(nulls = "-")
	private BigDecimal qnhPressure;

	/**
	 * km/h: Gust peak (one second); maximum
	 */
	@Parsed(field = "fu3010z1")
	@NullString(nulls = "-")
	private BigDecimal gustPeak;

	/**
	 * %: Relative air humidity 2 m above ground; current value
	 */
	@Parsed(field = "ure200s0")
	@NullString(nulls = "-")
	private Integer humidity;

	/**
	 * hPa: Pressure at station level (QFE); current value
	 */
	@Parsed(field = "prestas0")
	@NullString(nulls = "-")
	private BigDecimal qfePressure;

	/**
	 * hPa: Pressure reduced to sea level (QFF); current value
	 */
	@Parsed(field = "pp0qffs0")
	@NullString(nulls = "-")
	private BigDecimal qffPressure;

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ZonedDateTime getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(ZonedDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public BigDecimal getTemperature() {
		return this.temperature;
	}

	public void setTemperature(BigDecimal temperature) {
		this.temperature = temperature;
	}

	public Integer getSunshine() {
		return this.sunshine;
	}

	public void setSunshine(Integer sunshine) {
		this.sunshine = sunshine;
	}

	public BigDecimal getPrecipitation() {
		return this.precipitation;
	}

	public void setPrecipitation(BigDecimal precipitation) {
		this.precipitation = precipitation;
	}

	public Integer getWindDirection() {
		return this.windDirection;
	}

	public void setWindDirection(Integer windDirection) {
		this.windDirection = windDirection;
	}

	public BigDecimal getWindSpeed() {
		return this.windSpeed;
	}

	public void setWindSpeed(BigDecimal windSpeed) {
		this.windSpeed = windSpeed;
	}

	public BigDecimal getQnhPressure() {
		return this.qnhPressure;
	}

	public void setQnhPressure(BigDecimal qnhPressure) {
		this.qnhPressure = qnhPressure;
	}

	public BigDecimal getGustPeak() {
		return this.gustPeak;
	}

	public void setGustPeak(BigDecimal gustPeak) {
		this.gustPeak = gustPeak;
	}

	public Integer getHumidity() {
		return this.humidity;
	}

	public void setHumidity(Integer humidity) {
		this.humidity = humidity;
	}

	public BigDecimal getQfePressure() {
		return this.qfePressure;
	}

	public void setQfePressure(BigDecimal qfePressure) {
		this.qfePressure = qfePressure;
	}

	public BigDecimal getQffPressure() {
		return this.qffPressure;
	}

	public void setQffPressure(BigDecimal qffPressure) {
		this.qffPressure = qffPressure;
	}

	@Override
	public String toString() {
		return "Station [code=" + this.code + ", dateTime=" + this.dateTime
				+ ", temperature=" + this.temperature + ", sunshine=" + this.sunshine
				+ ", precipitation=" + this.precipitation + ", windDirection="
				+ this.windDirection + ", windSpeed=" + this.windSpeed + ", qnhPressure="
				+ this.qnhPressure + ", gustPeak=" + this.gustPeak + ", humidity="
				+ this.humidity + ", qfePressure=" + this.qfePressure + ", qffPressure="
				+ this.qffPressure + "]";
	}

}
