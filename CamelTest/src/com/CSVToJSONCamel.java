package com;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.impl.DefaultCamelContext;

import com.bean.Employee;

public class CSVToJSONCamel {

	public static void main(String args[]) throws Exception {

		CamelContext camelContext = new DefaultCamelContext();
		try {
			camelContext.addRoutes(new RouteBuilder() {
				public void configure() {
					
					BindyCsvDataFormat bindy = new BindyCsvDataFormat(Employee.class);
					
					from("direct:myRoute")
						.to("language:constant:resource:file:input/xml_input.csv")
						
						.log("###################################")
						.log("${body}")
						
						.unmarshal(bindy)
						
						.log("###################################")
						.log("${body}")
						
						.marshal().json(true)
						
						.log("###################################")
						.log("${body}")
						.log("###################################")
						
						.to("file:output?fileName=csv_output.json")
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
