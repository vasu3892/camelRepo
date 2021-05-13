package com;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class XMLToCSVCamel {

	public static void main(String args[]) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			public void configure() {
				from("file:input?fileName=input.xml&noop=true")
				.to("xslt:file:input/transform.xslt")
				.to("file:output?fileName=output.csv");
			}
		});
		context.start();
		Thread.sleep(10000);
		context.stop();
		System.out.println("done");
	}

}
