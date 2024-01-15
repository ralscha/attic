package ch.ralscha.controller;

import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class User {
	private String name;

	@DateTimeFormat(iso = ISO.DATE)
	private Calendar birthDay;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//@JsonSerialize(using=MyDateSerializer.class)  
	@DateTimeFormat(iso = ISO.DATE)
	public Calendar getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay) {
		this.birthDay = birthDay;
	}

}
