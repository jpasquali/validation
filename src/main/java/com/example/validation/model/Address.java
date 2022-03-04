package com.example.validation.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Address {

	@NotEmpty
	private String street;

	@Size(min = 5, max = 6)
	private String zip;
}
