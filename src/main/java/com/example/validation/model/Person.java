package com.example.validation.model;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class Person {

	@NotEmpty
	private String name;

	@Valid
	@NotNull
	private Address address;
}
