package com.example.practice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // 將Meal交由Spring Boot託管成實體類(Entity)，跟資料庫做對應(mapping)
@Table(name = "meal") // 告訴Java這一個類別是對應到哪一張表
public class Meal {

	@Id // 告訴Java這個是PK，因為此欄位在表中是P Key
	@Column(name = "name") // 第一個name是語法，告訴Java欄位名字，第二個字串中的name是DB table中了欄位名稱(要完全一樣)
	private String name; // 這裡的變數名稱name是屬性名稱

	@Column(name = "price")
	private int price;

	public Meal() {
		super();
	}

	public Meal(String name, int price) {
		super();
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
