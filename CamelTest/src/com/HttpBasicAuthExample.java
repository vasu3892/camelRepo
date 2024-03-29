package com;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class HttpBasicAuthExample {

	public static void main(String args[]) throws Exception {

		CamelContext camelContext = new DefaultCamelContext();
		try {
			camelContext.addRoutes(new RouteBuilder() {
				public void configure() {
					
					from("direct:myRoute")
					.streamCaching()
					
						.to("https://jigsaw.w3.org/HTTP/Basic/?authMethod=Basic&authUsername=guest&authPassword=guest")
						
						.log("###################################")
						.log("body ::: ${body}")
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
