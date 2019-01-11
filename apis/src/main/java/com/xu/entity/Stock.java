package com.xu.entity;

import java.io.Serializable;

public class Stock implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 520564085593737085L;
	private Long id;
	private String name;
	private String descs;
	private Integer amount;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
}
