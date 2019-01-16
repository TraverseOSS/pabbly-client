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
public class Plan {

	@XmlAttribute(name = "product_id")
	private String productId;
	@XmlAttribute(name = "plan_name")
	private String planName;
	@XmlAttribute(name = "plan_code")
	private String planCode;
	@XmlAttribute(name = "billing_cycle")
	private String billingCycle;
	@XmlAttribute(name = "setup_fee")
	private Double setupFee;
	@XmlAttribute(name = "billing_cycle_num")
	private String billingCycleNum;
	private Double price;
	@XmlAttribute(name = "billing_period")
	private String billingPeriod;
	@XmlAttribute(name = "billing_period_num")
	private String billingPeriodNum;
	@XmlAttribute(name = "plan_active")
	private boolean planActive;
	@XmlAttribute(name = "plan_description")
	private String planDescription;
	@XmlAttribute(name = "trial_period")
	private String trialPeriod;
	@XmlAttribute(name = "redirect_link")
	private String redirectLink;

	public Plan() {
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(final String productId) {
		this.productId = productId;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(final String planName) {
		this.planName = planName;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(final String planCode) {
		this.planCode = planCode;
	}

	public String getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(final String billingCycle) {
		this.billingCycle = billingCycle;
	}

	public Double getSetupFee() {
		return setupFee;
	}

	public void setSetupFee(final Double setupFee) {
		this.setupFee = setupFee;
	}

	public String getBillingCycleNum() {
		return billingCycleNum;
	}

	public void setBillingCycleNum(final String billingCycleNum) {
		this.billingCycleNum = billingCycleNum;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(final Double price) {
		this.price = price;
	}

	public String getBillingPeriod() {
		return billingPeriod;
	}

	public void setBillingPeriod(final String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	public String getBillingPeriodNum() {
		return billingPeriodNum;
	}

	public void setBillingPeriodNum(final String billingPeriodNum) {
		this.billingPeriodNum = billingPeriodNum;
	}

	public boolean getPlanActive() {
		return planActive;
	}

	public void setPlanActive(final boolean planActive) {
		this.planActive = planActive;
	}

	public String getPlanDescription() {
		return planDescription;
	}

	public void setPlanDescription(final String planDescription) {
		this.planDescription = planDescription;
	}

	public String getTrialPeriod() {
		return trialPeriod;
	}

	public void setTrialPeriod(final String trialPeriod) {
		this.trialPeriod = trialPeriod;
	}

	public String getRedirectLink() {
		return redirectLink;
	}

	public void setRedirectLink(final String redirectLink) {
		this.redirectLink = redirectLink;
	}

}
