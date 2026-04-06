/*
 * Copyright 2010-2012 Luca Garulli (l.garulli--at--orientechnologies.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.rasc.playground.orientdb.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.orientechnologies.orient.core.annotation.OAfterDeserialization;
import com.orientechnologies.orient.core.annotation.OBeforeSerialization;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.record.impl.ORecordBytes;

public class Account {
	@Id
	private Object rid;

	private int id;
	private String name;
	private String surname;
	private Date birthDate;
	private float salary;

	@OneToMany(orphanRemoval = true)
	private List<Address> addresses = new ArrayList<>();

	private byte[] thumbnail;
	private transient byte[] photo;
	private transient boolean initialized = false;

	public Account() {
	}

	public Account(int iId, String iName, String iSurname) {
		this.id = iId;
		this.name = iName;
		this.surname = iSurname;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public List<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public int getId() {
		return this.id;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public float getSalary() {
		return this.salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public boolean isInitialized() {
		return this.initialized;
	}

	public Object getRid() {
		return this.rid;
	}

	public byte[] getThumbnail() {
		return this.thumbnail;
	}

	public void setThumbnail(byte[] iThumbnail) {
		this.thumbnail = iThumbnail;
	}

	public byte[] getPhoto() {
		return this.photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	@OAfterDeserialization
	public void fromStream(final ODocument iDocument) {
		this.initialized = true;
		if (iDocument.containsField("externalPhoto")) {
			// READ THE PHOTO FROM AN EXTERNAL RECORD AS PURE BINARY
			ORecordBytes extRecord = iDocument.field("externalPhoto");
			this.photo = extRecord.toStream();
		}
	}

	@OBeforeSerialization
	public void toStream(final ODocument iDocument) {
		if (this.thumbnail != null) {
			// WRITE THE PHOTO IN AN EXTERNAL RECORD AS PURE BINARY
			ORecordBytes externalPhoto = new ORecordBytes(this.thumbnail);
			iDocument.field("externalPhoto", externalPhoto);
		}
	}
}