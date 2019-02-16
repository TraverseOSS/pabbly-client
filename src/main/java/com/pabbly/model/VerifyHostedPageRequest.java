package com.pabbly.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(Include.NON_NULL)
public class VerifyHostedPageRequest {

	private String hostedpage;

	public String getHostedpage() {
		return hostedpage;
	}

	public void setHostedpage(final String hostedpage) {
		this.hostedpage = hostedpage;
	}

}
