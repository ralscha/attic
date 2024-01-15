package ch.ralscha.model;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import ch.ralscha.controller.CalendarSerializer;
import ch.ralscha.controller.DateSerializer;
import ch.ralscha.controller.LocalDateSerializer;

public class User {

	private static DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendMonthOfYear(2).appendLiteral('/')
			.appendDayOfMonth(2).appendLiteral('/').appendYear(4, 4).toFormatter();

	private int id;
	private String firstName;
	private String lastName;
	private Date birthDayDate;
	private Calendar birthDayCalendar;
	private LocalDate birthDayLocalDate;

	public User(String[] line) throws ParseException {
		this.id = Integer.valueOf(line[0]);
		this.firstName = line[1];
		this.lastName = line[2];

		this.birthDayLocalDate = formatter.parseDateTime(line[3]).toLocalDate();
		this.birthDayCalendar = birthDayLocalDate.toDateMidnight().toGregorianCalendar();
		this.birthDayDate = birthDayLocalDate.toDateMidnight().toDate();
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	@JsonSerialize(using = DateSerializer.class)
	public Date getBirthDayDate() {
		return birthDayDate;
	}

	@JsonSerialize(using = CalendarSerializer.class)
	public Calendar getBirthDayCalendar() {
		return birthDayCalendar;
	}

	@JsonSerialize(using = LocalDateSerializer.class)
	public LocalDate getBirthDayLocalDate() {
		return birthDayLocalDate;
	}

}
