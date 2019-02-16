# Java Pabbly Client
Client for use with [https://www.pabbly.com/](https://www.pabbly.com/)


## Usage

Create the client via the builder.
The only required fields are apiKey and secretKey:

```
	final PabblyClient pabblyClient = new PabblyClient.Builder()
				.apiKey("API_KEY").secretKey("SECRET_KEY").build();
```

## Supported Operations
Create new Plan for a product

```
	pabblyClient.createPlan(plan);
```

Verify Hosted Page

```
	final String hostedPage = "5a4dc3548f40f61da0091c1c";
	pabblyClient.verifHostedPage(hostedPage);
```

Get Subscription

```
	final String subscriptionId = "5a4dc33d8f40f61da0091c1b"
	pabblyClient.getSubscription(subscriptionId);
```


