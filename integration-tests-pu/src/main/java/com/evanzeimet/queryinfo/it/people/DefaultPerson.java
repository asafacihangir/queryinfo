package com.evanzeimet.queryinfo.it.people;

/*
 * #%L
 * queryinfo-integration-tests-pu
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2015 Evan Zeimet
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

import com.evanzeimet.queryinfo.it.organizations.DefaultOrganization;
import com.evanzeimet.queryinfo.it.organizations.Organization;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultPerson implements Person {

	private Long employerOrganizationId;
	private String firstName;
	private Long id;
	private String lastName;

	@JsonDeserialize(as = DefaultOrganization.class)
	private Organization employer;

	public DefaultPerson() {

	}

	@Override
	public Organization getEmployer() {
		return employer;
	}

	@Override
	public void setEmployer(Organization employer) {
		this.employer = employer;
	}

	@Override
	public Long getEmployerOrganizationId() {
		return employerOrganizationId;
	}

	@Override
	public void setEmployerOrganizationId(Long employerOrganizationId) {
		this.employerOrganizationId = employerOrganizationId;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
