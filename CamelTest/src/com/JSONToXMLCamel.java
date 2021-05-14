package com;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class JSONToXMLCamel {

	public static void main(String args[]) throws Exception {

		CamelContext camelContext = new DefaultCamelContext();
		try {
			camelContext.addRoutes(new RouteBuilder() {
				public void configure() {
					
					from("direct:myRoute")
						.to("language:constant:resource:file:input/input.json")
						
						.log("###################################")
						.log("${body}")
						.log("###################################")
						
						.unmarshal().xmljson()
						
						.log("###################################")
						.log("${body}")
						.log("###################################")
						
						.to("file:output?fileName=output.xml")
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
