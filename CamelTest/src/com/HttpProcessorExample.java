package com;

import java.util.UUID;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

import com.processor.MyProcessor;

public class HttpProcessorExample {

	public static void main(String args[]) throws Exception {

		CamelContext camelContext = new DefaultCamelContext();
		try {
			camelContext.addRoutes(new RouteBuilder() {
				public void configure() {
					
					from("direct:myRoute")
					.streamCaching()
					
						.log("###################################")
						.log("body ::: ${body}")
						.log("header.ETag ::: ${header.ETag}")
						.log("header.Connection ::: ${header.Connection}")
						.log("###################################")
					
						.to("https://postman-echo.com/get?name=ABC&phoneNum=122143213")
						
						.log("###################################")
						.log("body ::: ${body}")
						.log("header.ETag ::: ${header.ETag}")
						.log("header.Connection ::: ${header.Connection}")
						.log("###################################")
						
						.process(new Processor() {
					        public void process(Exchange exchange) throws Exception {
					           String payload = (String) exchange.getIn().getBody(String.class);
					           
					           payload = payload.toUpperCase() + " - " + UUID.randomUUID();
					           
					           exchange.getIn().setBody(payload);
					           exchange.getIn().setHeader("randomUUID", UUID.randomUUID());
					           exchange.setProperty("randomNum", Math.random());
					       }
					    })
						
						.log("###################################")
						.log("body ::: ${body}")
						.log("header.randomUUID ::: ${header.randomUUID}")
						.log("property.randomNum ::: ${property.randomNum}")
						.log("###################################")
						
						.process(new MyProcessor())
						
						.log("###################################")
						.log("body ::: ${body}")
						.log("header.randomUUID ::: ${header.randomUUID}")
						.log("property.randomNum ::: ${property.randomNum}")
						.log("###################################")
						
						.process(ex -> ex.getIn().setBody("body changed by lambda"))
						
						.log("###################################")
						.log("body ::: ${body}")
						.log("header.randomUUID ::: ${header.randomUUID}")
						.log("property.randomNum ::: ${property.randomNum}")
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
