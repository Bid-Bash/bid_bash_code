package com.csuf.bidbash.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.csuf.bidbash.service.AuctionSaleService;
import com.csuf.bidbash.utils.ProductUtils;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionCreateParams.LineItem.PriceData;
import com.stripe.param.checkout.SessionCreateParams.Mode;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/payment")
public class PaymentController {

	@Value("${spring.stripe.secret-key}")
	private String stripeApiKey;

	@Value("${spring.stripe.webhook}")
	private String webHookSecret;

	@Autowired
	private AuctionSaleService auctionSaleService;

	@PostMapping("/charge")
	private String processPayment(@RequestBody ProductUtils obj) throws StripeException {

		Stripe.apiKey = stripeApiKey;

		CustomerCreateParams cparams = CustomerCreateParams.builder().setName(obj.userName).setEmail(obj.emailId)
				.build();

		Customer customer = Customer.create(cparams);

		SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder().setMode(Mode.PAYMENT)
				.setCustomer(customer.getId()).setSuccessUrl("http://localhost:3000/success")
				.setCancelUrl("http://localhost:3000/failure");

		Map<String, String> productMetaData = new HashMap<>();
		productMetaData.put("saleId", String.valueOf(obj.saleId));

		paramsBuilder.putAllMetadata(productMetaData);

		paramsBuilder.addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L)
				.setPriceData(PriceData.builder()
						.setProductData(PriceData.ProductData.builder().setName(obj.productName).build())
						.setCurrency("usd").setUnitAmount((long)(obj.price*100L)).build())
				.build());

		Session session = Session.create(paramsBuilder.build());

		return session.getUrl();
	}

	@PostMapping("/stripe/webhook")
	public void handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {

		try {
			Event event = Webhook.constructEvent(payload, sigHeader, webHookSecret);

			if ("checkout.session.completed".equals(event.getType())) {
				Session session = (Session) event.getDataObjectDeserializer().getObject().get();

				Map<String, String> metadata = session.getMetadata();
				String auctionRequestId = metadata.get("saleId");

				String paymentIntentId = session.getPaymentIntent();

				auctionSaleService.updateTransactionId(Integer.parseInt(auctionRequestId), paymentIntentId);

				auctionSaleService.sendSimpleEmail(Integer.parseInt(auctionRequestId));
			}
		} catch (Exception e) {
			System.out.print(e);
		}

	}

}
