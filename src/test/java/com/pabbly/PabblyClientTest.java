package com.pabbly;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.pabbly.model.PabblyResponse;
import com.pabbly.model.Plan;
import com.pabbly.model.Subscription;

public class PabblyClientTest {

	private static final int WIREMOCK_PORT = 8089;

	private PabblyClient pabblyClient;

	@Mock
	private Client client;

	@Mock
	private WebTarget webTarget;

	@Mock
	private Invocation.Builder builder;

	@Mock
	private Response response;

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(WIREMOCK_PORT);

	@Before
	public void setUp() {
		pabblyClient = new PabblyClient.Builder().client(client).apiUrl("http://localhost:" + WIREMOCK_PORT)
				.apiKey("API_KEY").secretKey("SECRET_KEY").build();
	}

	@Test
	public void givenSomePabblyClientWithValidAPIKeyAndSecretKeyWhenCreatingPlanThenCreatePlan() throws Exception {
		final String expectedResponseBody = "{\r\n" + "    \"status\": \"success\",\r\n"
				+ "    \"message\": \"Plan Created\",\r\n" + "    \"data\": {\r\n"
				+ "        \"plan_active\": \"true\",\r\n" + "        \"redirect_link\": null,\r\n"
				+ "        \"createdAt\": \"2018-11-02T10:31:14.308Z\",\r\n"
				+ "        \"updatedAt\": \"2018-11-02T10:31:14.308Z\",\r\n"
				+ "        \"id\": \"12341234234218ccd949b3\",\r\n"
				+ "        \"product_id\": \"978afsd91ca98d7fad942c2841\",\r\n"
				+ "        \"plan_name\": \"plan1\",\r\n" + "        \"plan_code\": \"plan1\",\r\n"
				+ "        \"price\": 10,\r\n" + "        \"billing_period\": \"m\",\r\n"
				+ "        \"billing_period_num\": \"1\",\r\n" + "        \"billing_cycle\": \"lifetime\",\r\n"
				+ "        \"billing_cycle_num\": null,\r\n" + "        \"trial_period\": 2,\r\n"
				+ "        \"setup_fee\": 2,\r\n" + "        \"plan_description\": \"\"\r\n" + "    }\r\n" + "}";
		stubFor(post(urlEqualTo("/" + PabblyClient.PLAN_CREATE_PATH))
				.withHeader(HttpHeaders.ACCEPT, equalTo(MediaType.APPLICATION_JSON))
				.withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON))
				.willReturn(aResponse().withStatus(201).withBody(expectedResponseBody)
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)));
		final Plan plan = new Plan();
		final PabblyResponse<Plan> createdPlanResponse = pabblyClient.createPlan(plan);
		assertNotNull(createdPlanResponse);
		assertEquals("success", createdPlanResponse.getStatus());
		assertEquals("Plan Created", createdPlanResponse.getMessage());

		assertEquals("lifetime", createdPlanResponse.getData().getBillingCycle());
		assertNull(createdPlanResponse.getData().getBillingCycleNum());
		assertEquals("m", createdPlanResponse.getData().getBillingPeriod());
		assertEquals("1", createdPlanResponse.getData().getBillingPeriodNum());
		assertEquals(true, createdPlanResponse.getData().getPlanActive());
		assertEquals("plan1", createdPlanResponse.getData().getPlanCode());
		assertEquals("", createdPlanResponse.getData().getPlanDescription());
		assertEquals("plan1", createdPlanResponse.getData().getPlanName());
		assertEquals(new Double(10), createdPlanResponse.getData().getPrice());
		assertEquals("978afsd91ca98d7fad942c2841", createdPlanResponse.getData().getProductId());
		assertNull(createdPlanResponse.getData().getRedirectLink());
		assertEquals(new Double(2), createdPlanResponse.getData().getSetupFee());
		assertEquals("2", createdPlanResponse.getData().getTrialPeriod());
	}

	@Test
	public void givenSomePabblyClientWithValidAPIKeyAndSecretKeyAndHostedPageWhenVerifyingHostedPageThenVerifyAndReturnSubscription()
			throws Exception {
		final String expectedResponseBody = "{\r\n" + "    \"status\": \"success\",\r\n"
				+ "    \"message\": \"Valid hosted page data\",\r\n" + "    \"data\": {\r\n"
				+ "        \"customer_id\": \"5a509776081e5316d84a21e0\",\r\n"
				+ "        \"email_id\": \"LanceSCrews@teleworm.us\",\r\n"
				+ "        \"product_id\": \"5a4dc33d8f40f61da0091c1b\",\r\n"
				+ "        \"plan_id\": \"5a4dc3548f40f61da0091c1c\",\r\n"
				+ "        \"user_id\": \"5a4b60497cfab6872a7feafb\",\r\n" + "        \"status\": \"live\",\r\n"
				+ "        \"quantity\": \"1\",\r\n" + "        \"amount\": 50,\r\n"
				+ "        \"starts_at\": \"2018-01-09T07:40:27.272Z\",\r\n"
				+ "        \"activation_date\": \"2018-01-09T07:40:27.272Z\",\r\n"
				+ "        \"expiry_date\": \"\",\r\n" + "        \"trial_days\": 0,\r\n"
				+ "        \"trial_expiry_date\": \"\",\r\n"
				+ "        \"next_billing_date\": \"2018-02-09T07:40:27.272Z\",\r\n"
				+ "        \"last_billing_date\": \"2018-01-09T07:40:27.857Z\",\r\n" + "        \"plan\": {\r\n"
				+ "            \"product_id\": \"5a4dc33d8f40f61da0091c1b\",\r\n"
				+ "            \"user_id\": \"5a4b60497cfab6872a7feafb\",\r\n"
				+ "            \"plan_name\": \"10,000 Subscribers Plan\",\r\n"
				+ "            \"plan_code\": \"10k-subscribers\",\r\n" + "            \"price\": \"50\",\r\n"
				+ "            \"billing_period\": \"m\",\r\n" + "            \"billing_period_num\": \"1\",\r\n"
				+ "            \"billing_cycle\": \"lifetime\",\r\n" + "            \"billing_cycle_num\": null,\r\n"
				+ "            \"trial_period\": null,\r\n" + "            \"setup_fee\": null,\r\n"
				+ "            \"plan_description\": null,\r\n"
				+ "            \"createdAt\": \"2018-01-04T06:01:56.743Z\",\r\n"
				+ "            \"updatedAt\": \"2018-01-06T05:56:27.865Z\",\r\n"
				+ "            \"id\": \"5a4dc3548f40f61da0091c1c\"\r\n" + "        },\r\n"
				+ "        \"setup_fee\": null,\r\n" + "        \"payment_terms\": \"\",\r\n"
				+ "        \"createdAt\": \"2018-01-09T07:40:27.852Z\",\r\n"
				+ "        \"updatedAt\": \"2018-01-09T07:40:27.862Z\",\r\n"
				+ "        \"payment_method\": \"5a5471eaa872a21608b06104\",\r\n"
				+ "        \"id\": \"5a5471eba872a21608b06106\"\r\n" + "    }\r\n" + "}";
		stubFor(post(urlEqualTo("/" + PabblyClient.VERIFY_HOSTED_PATH))
				.withHeader(HttpHeaders.ACCEPT, equalTo(MediaType.APPLICATION_JSON))
				.withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON))
				.willReturn(aResponse().withStatus(200).withBody(expectedResponseBody)
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)));
		final String hostPage = "02348273847298374";
		final PabblyResponse<Subscription> verifiedSubscription = pabblyClient.verifyHostedPage(hostPage);
		assertNotNull(verifiedSubscription);
		assertEquals("success", verifiedSubscription.getStatus());
		assertEquals("Valid hosted page data", verifiedSubscription.getMessage());
		assertEquals("5a509776081e5316d84a21e0", verifiedSubscription.getData().getCustomerId());
		assertEquals("LanceSCrews@teleworm.us", verifiedSubscription.getData().getEmailId());
		assertEquals("5a4dc3548f40f61da0091c1c", verifiedSubscription.getData().getPlanId());
		assertEquals("5a4b60497cfab6872a7feafb", verifiedSubscription.getData().getUserId());
		assertEquals(new Double(50), verifiedSubscription.getData().getAmount());
		assertEquals(Long.valueOf(1), verifiedSubscription.getData().getQuantity());
		assertEquals("5a5471eba872a21608b06106", verifiedSubscription.getData().getId());

		assertEquals("lifetime", verifiedSubscription.getData().getPlan().getBillingCycle());
		assertNull(verifiedSubscription.getData().getPlan().getBillingCycleNum());
		assertEquals("m", verifiedSubscription.getData().getPlan().getBillingPeriod());
		assertFalse(verifiedSubscription.getData().getPlan().getPlanActive());
		assertEquals("10k-subscribers", verifiedSubscription.getData().getPlan().getPlanCode());
		assertNull(verifiedSubscription.getData().getPlan().getPlanDescription());
		assertEquals("10,000 Subscribers Plan", verifiedSubscription.getData().getPlan().getPlanName());
		assertEquals(new Double(50), verifiedSubscription.getData().getPlan().getPrice());
		assertEquals("5a4dc33d8f40f61da0091c1b", verifiedSubscription.getData().getPlan().getProductId());
	}

	@Test
	public void givenSomePabblyClientWithValidAPIKeyAndSecretKeyAndSubscriptionIdWhenGettingSubscriptionThenReturnSubscription()
			throws Exception {
		final String subscriptionId = "02348273847298374";

		final String expectedResponseBody = "{\r\n" + "\"status\": \"success\",\r\n"
				+ "\"message\": \"Subscription data\",\r\n" + "\"data\": {\r\n"
				+ "         \"customer_id\": \"5a4b78053152df337d841348\",\r\n"
				+ "         \"email_id\": \"LanceSCrews@teleworm.us\",\r\n"
				+ "         \"product_id\": \"5a4b5e6ecb9bc82fd2b4bfef\",\r\n"
				+ "         \"plan_id\": \"5a4b5e7fcb9bc82fd2b4bff0\",\r\n"
				+ "         \"user_id\": \"5a4b5db47cfab6872a7feafa\",\r\n" + "         \"status\": \"live\",\r\n"
				+ "         \"quantity\": \"1\",\r\n" + "         \"amount\": 99,\r\n"
				+ "         \"starts_at\": \"2018-01-02T12:16:05.055Z\",\r\n"
				+ "         \"activation_date\": \"2018-01-02T12:16:05.055Z\",\r\n"
				+ "         \"expiry_date\": \"\",\r\n" + "         \"trial_days\": 0,\r\n"
				+ "         \"trial_expiry_date\": \"\",\r\n" + "         \"next_billing_date\": \"\",\r\n"
				+ "         \"last_billing_date\": \"2018-01-02T12:16:05.508Z\",\r\n" + "         \"plan\": {\r\n"
				+ "                \"product_id\": \"5a4b5e6ecb9bc82fd2b4bfef\",\r\n"
				+ "                \"user_id\": \"5a4b5db47cfab6872a7feafa\",\r\n"
				+ "                \"plan_name\": \"Life Time Plan\",\r\n"
				+ "                \"plan_code\": \"lifetime\",\r\n" + "                \"price\": \"99\",\r\n"
				+ "                \"billing_period\": \"y\",\r\n"
				+ "                \"billing_period_num\": \"1\",\r\n"
				+ "                \"billing_cycle\": \"lifetime\",\r\n"
				+ "                \"billing_cycle_num\": null,\r\n" + "                \"trial_period\": null,\r\n"
				+ "                \"setup_fee\": null,\r\n" + "                \"plan_description\": null,\r\n"
				+ "                \"createdAt\": \"2018-01-02T10:27:11.365Z\",\r\n"
				+ "                \"updatedAt\": \"2018-01-02T10:27:11.365Z\",\r\n"
				+ "                \"id\": \"5a4b5e7fcb9bc82fd2b4bff0\"\r\n" + "                 },\r\n"
				+ "        \"setup_fee\": null,\r\n" + "        \"payment_terms\": \"\",\r\n"
				+ "        \"pcustomer_id\": \"5a4b776f7cfab6872a7feb06\",\r\n"
				+ "        \"createdAt\": \"2018-01-02T12:16:05.503Z\",\r\n"
				+ "        \"updatedAt\": \"2018-01-02T12:16:05.535Z\",\r\n"
				+ "        \"payment_method\": \"5a4b78053152df337d841346\",\r\n"
				+ "        \"id\": \"5a4b78053152df337d841349\"\r\n" + "       }\r\n" + "}";
		stubFor(get(urlEqualTo("/" + PabblyClient.SUBSCRIPTION_PATH + "/" + subscriptionId))
				.withHeader(HttpHeaders.ACCEPT, equalTo(MediaType.APPLICATION_JSON))
				.willReturn(aResponse().withStatus(200).withBody(expectedResponseBody)
						.withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)));
		final PabblyResponse<Subscription> subscription = pabblyClient.getSubscription(subscriptionId);
		assertNotNull(subscription);
		assertEquals("success", subscription.getStatus());
		assertEquals("Subscription data", subscription.getMessage());
		assertEquals("5a4b78053152df337d841348", subscription.getData().getCustomerId());
		assertEquals("LanceSCrews@teleworm.us", subscription.getData().getEmailId());
		assertEquals("5a4b5e7fcb9bc82fd2b4bff0", subscription.getData().getPlanId());
		assertEquals("5a4b5db47cfab6872a7feafa", subscription.getData().getUserId());
		assertEquals(new Double(99.0), subscription.getData().getAmount());
		assertEquals(Long.valueOf(1), subscription.getData().getQuantity());
		assertEquals("5a4b78053152df337d841349", subscription.getData().getId());

		assertEquals("lifetime", subscription.getData().getPlan().getBillingCycle());
		assertNull(subscription.getData().getPlan().getBillingCycleNum());
		assertEquals("y", subscription.getData().getPlan().getBillingPeriod());
		assertFalse(subscription.getData().getPlan().getPlanActive());
		assertEquals("lifetime", subscription.getData().getPlan().getPlanCode());
		assertNull(subscription.getData().getPlan().getPlanDescription());
		assertEquals("Life Time Plan", subscription.getData().getPlan().getPlanName());
		assertEquals(new Double(99.0), subscription.getData().getPlan().getPrice());
		assertEquals("5a4b5e6ecb9bc82fd2b4bfef", subscription.getData().getPlan().getProductId());
	}
}
