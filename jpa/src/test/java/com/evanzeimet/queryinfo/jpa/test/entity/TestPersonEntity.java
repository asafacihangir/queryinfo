package com.evanzeimet.queryinfo.jpa.test.entity;

/*
 * #%L
 * queryinfo-jpa
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2015 - 2016 Evan Zeimet
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import javax.persistence.Column;
import javax.persistence.OneToOne;

import com.evanzeimet.queryinfo.jpa.field.QueryInfoField;
import com.evanzeimet.queryinfo.jpa.join.QueryInfoJoin;

public class TestPersonEntity {

	private String firstName;
	private Long id;
	private String lastName;
	private TestPersonEntity spouse;

	public TestPersonEntity() {

	}

	@QueryInfoField
	@Column
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@QueryInfoField
	@Column
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@QueryInfoField
	@Column
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@QueryInfoJoin
	@OneToOne
	public TestPersonEntity getSpouse() {
		return spouse;
	}

	public void setSpouse(TestPersonEntity spouse) {
		this.spouse = spouse;
	}
}
