package com;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class XPathExample {

	public static void main(String args[]) throws Exception {

		CamelContext camelContext = new DefaultCamelContext();
		try {
			camelContext.addRoutes(new RouteBuilder() {
				public void configure() {

					from("direct:myRoute")
					
							.log("###################################")
							.log("${body}")
							.log("###################################")

							.setBody().simple("</root>")
							
							.log("###################################")
							.log("${body}")
							.log("###################################")
							
							.setBody().xpath("//*[local-name()='NumberEaten']/text()")
							
							.log("###################################")
							.log("${body}")
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
