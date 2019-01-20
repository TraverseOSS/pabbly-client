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