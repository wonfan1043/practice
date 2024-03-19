package com.example.practice.vo;

public class JoinVo {

	private String id;

	private String name;

	private int age;

	private String city;

	private int amonut;

	public JoinVo() {
		super();
	}

	public JoinVo(String id, String name, int age, String city, int amonut) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.city = city;
		this.amonut = amonut;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getAmonut() {
		return amonut;
	}

	public void setAmonut(int amonut) {
		this.amonut = amonut;
	}

}
