package com.pabbly.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(Include.NON_NULL)
public class Subscription {

	@XmlAttribute(name = "customer_id")
	private String customerId;
	@XmlAttribute(name = "email_id")
	private String emailId;
	@XmlAttribute(name = "product_id")
	private String productId;
	@XmlAttribute(name = "plan_id")
	private String planId;
	@XmlAttribute(name = "user_id")
	private String userId;
	private String status;
	private Long quantity;
	private Double amount;
	private Plan plan;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(final String customerId) {
		this.customerId = customerId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(final String emailId) {
		this.emailId = emailId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(final String productId) {
		this.productId = productId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(final String planId) {
		this.planId = planId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(final Long quantity) {
		this.quantity = quantity;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(final Plan plan) {
		this.plan = plan;
	}

}
