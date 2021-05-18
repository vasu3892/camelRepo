package com.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyProcessor implements Processor {
	public void process(Exchange exchange) throws Exception {

		String payload = exchange.getIn().getBody(String.class);
		payload = payload.toLowerCase().split("-")[0].trim();
		exchange.getIn().setBody(payload);

		exchange.getIn().setHeader("randomUUID", "XXXXXXXXXXXXXXXXX");
		exchange.setProperty("randomNum", "XXXXXXXXXXXXXXXXX");

	}
}
