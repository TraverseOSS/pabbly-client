package com.pabbly;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.pabbly.model.PabblyResponse;
import com.pabbly.model.Plan;
import com.pabbly.model.Subscription;
import com.pabbly.model.VerifyHostedPageRequest;

public class PabblyClient {
	static final String SUBSCRIPTION_PATH = "subscription";
	static final String VERIFY_HOSTED_PATH = "verifyhosted";
	private static final String CLIENT_WAS_PROVIDED_MESSAGE = "Client was provided, so using provided one. Omit the client if you want the pabbly-client to build it.";
	private static final Logger LOG = Logger.getLogger(PabblyClient.class.getName());
	private final String apiKey;
	private final String secretKey;
	static final String DEFAULT_PABBLY_API_URL = "https://payments.pabbly.com/api/v1";
	private String apiUrl = DEFAULT_PABBLY_API_URL;
	static final String PLAN_CREATE_PATH = "plan/create";

	private Client client;

	private PabblyClient(final Builder builder) {
		if (builder.apiUrl != null) {
			this.apiUrl = builder.apiUrl;
		}
		if (client == null) {

			this.apiKey = builder.apiKey;
			this.secretKey = builder.secretKey;

			final HttpAuthenticationFeature basicAuthenticationFeature = HttpAuthenticationFeature.basic(apiKey,
					secretKey);
			final JacksonJsonProvider jacksonJsonProvider = new JacksonJaxbJsonProvider()
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			client = ClientBuilder.newClient().register(basicAuthenticationFeature).register(jacksonJsonProvider);
		} else {
			LOG.warning(CLIENT_WAS_PROVIDED_MESSAGE);
			this.apiKey = builder.apiKey;
			this.secretKey = builder.secretKey;
			client = builder.client;
		}

	}

	public static class Builder {
		private String apiKey;
		private String secretKey;
		private Client client;
		private String apiUrl;

		public Builder apiKey(final String apiKey) {
			this.apiKey = apiKey;
			return this;
		}

		public Builder secretKey(final String secretKey) {
			this.secretKey = secretKey;
			return this;
		}

		public Builder client(final Client client) {
			this.client = client;
			return this;
		}

		public Builder apiUrl(final String apiUrl) {
			this.apiUrl = apiUrl;
			return this;
		}

		public PabblyClient build() {
			return new PabblyClient(this);
		}
	}

	public PabblyResponse<Plan> createPlan(final Plan plan) {
		final Entity<Plan> entity = Entity.entity(plan, MediaType.APPLICATION_JSON);
		final Response response = client.target(this.apiUrl).path(PLAN_CREATE_PATH).request(MediaType.APPLICATION_JSON)
				.post(entity);
		return response.readEntity(new GenericType<PabblyResponse<Plan>>() {
		});
	}

	public PabblyResponse<Subscription> verifyHostedPage(final String hostedPage) {
		final VerifyHostedPageRequest verifyHostedPageRequest = new VerifyHostedPageRequest();
		verifyHostedPageRequest.setHostedpage(hostedPage);
		final Entity<VerifyHostedPageRequest> entity = Entity.entity(verifyHostedPageRequest,
				MediaType.APPLICATION_JSON);
		final Response response = client.target(this.apiUrl).path(VERIFY_HOSTED_PATH)
				.request(MediaType.APPLICATION_JSON).post(entity);
		return response.readEntity(new GenericType<PabblyResponse<Subscription>>() {
		});
	}

	public PabblyResponse<Subscription> getSubscription(final String subscriptionId) {
		final Response response = client.target(this.apiUrl).path(SUBSCRIPTION_PATH).path(subscriptionId)
				.request(MediaType.APPLICATION_JSON).get();
		return response.readEntity(new GenericType<PabblyResponse<Subscription>>() {
		});
	}

}
