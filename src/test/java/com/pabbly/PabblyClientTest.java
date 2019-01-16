package com.pabbly;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.Assert.assertEquals;
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

}
