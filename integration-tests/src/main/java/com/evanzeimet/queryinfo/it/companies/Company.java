package com.evanzeimet.queryinfo.it.companies;

import java.util.List;

import com.evanzeimet.queryinfo.it.people.Person;

public interface Company {

	String getAddress1();

	void setAddress1(String address1);

	String getAddress2();

	void setAddress2(String address2);

	String getCity();

	void setCity(String city);

	List<Person> getEmployees();

	void setEmployees(List<Person> employees);

	Long getId();

	void setId(Long id);

	String getName();

	void setName(String name);

	String getState();

	void setState(String state);

	String getZip();

	void setZip(String zip);

}
