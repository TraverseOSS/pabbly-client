package com.pabbly;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.pabbly.model.PabblyResponse;
import com.pabbly.model.Plan;

public class PabblyClientDriver {

	@Test
	public void givenSomePlanWhenCreatingPlanThenCreatePlan() {

		final PabblyClient pabblyClient = new PabblyClient.Builder().apiKey("0ee8c64f983627f578ef")
				.secretKey("267040c0673f7099f2d3b8a097ac3bf4").build();
		final Plan plan = new Plan();
		plan.setProductId("5c395faae4986e6203eacb04");
		plan.setPlanName("Some-Group-Id");
		plan.setPlanCode("773ddddd17");
		plan.setBillingPeriod("m");
		plan.setBillingCycle("lifetime");
		final PabblyResponse<Plan> createdPlan = pabblyClient.createPlan(plan);

		assertNotNull(createdPlan.getData());
	}

}
