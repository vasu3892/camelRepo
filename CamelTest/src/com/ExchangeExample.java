package com;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ExchangeExample {

	public static void main(String args[]) throws Exception {

		CamelContext camelContext = new DefaultCamelContext();
		try {
			camelContext.addRoutes(new RouteBuilder() {
				public void configure() {

					from("direct:myRoute")

							.log("###################################")
							.log("${property.p1}")
							.log("${header.p1}")
							.log("###################################")

							.setProperty("p1").simple("v1")
							.setHeader("p1").simple("v1")

							.log("###################################")
							.log("${property.p1}")
							.log("${header.p1}")
							.log("###################################")
					;
				}
			});
			ProducerTemplate template = camelContext.createProducerTemplate();
			camelContext.start();
			template.sendBody("direct:myRoute", null);
		} finally {
			camelContext.stop();
		}
	}

}
