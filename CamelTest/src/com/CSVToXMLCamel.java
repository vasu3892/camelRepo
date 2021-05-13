package com;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;

public class CSVToXMLCamel {

	public static void main(String args[]) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			public void configure() {
				CsvDataFormat csv = new CsvDataFormat();
				
				from("file:input?fileName=input.csv&noop=true")
				.unmarshal(csv)
				.to("bean:myCsvHandler?method=doHandleCsvData")
				//.to("file:output?fileName=output.txt")
				;
			}
		});
		context.start();
		Thread.sleep(10000);
		context.stop();
		System.out.println("done");
	}

}
