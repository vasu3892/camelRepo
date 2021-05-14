package com;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class WireTapExample {

	public static void main(String args[]) throws Exception {

		CamelContext camelContext = new DefaultCamelContext();
		try {
			camelContext.addRoutes(new RouteBuilder() {
				public void configure() {

					from("direct:mainRoute")
						.log("started myRoute")
						
						//.to("direct:route1")
						.wireTap("direct:route1")
						
						//.to("direct:route2")
						.wireTap("direct:route2")
						
						.delay(10000)
						.log("ended myRoute")
					;
					
					from("direct:route1")
						.log("started route1")
						.delay(5000)
						.log("ended route1")
					;
					
					from("direct:route2")
						.log("started route2")
						.delay(5000)
						.log("ended route2")
					;
					
				}
			});
			ProducerTemplate template = camelContext.createProducerTemplate();
			camelContext.start();
			template.sendBody("direct:mainRoute", null);
		} finally {
			camelContext.stop();
		}
	}

}
