package com;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.xstream.XStreamDataFormat;
import org.apache.camel.impl.DefaultCamelContext;

import com.bean.Employee;
import com.processor.CustomProcessorXStream;

public class CSVToXMLCamel {

	public static void main(String args[]) throws Exception {

		CamelContext camelContext = new DefaultCamelContext();
		try {
			camelContext.addRoutes(new RouteBuilder() {
				public void configure() {
					
					XStreamDataFormat xstreamDefinition = new XStreamDataFormat();
			        Map<String, String> aliases = new HashMap<String, String>();
			        aliases.put("Employee", Employee.class.getName());
			        xstreamDefinition.setAliases(aliases);
					
					from("direct:myRoute")
						.to("language:constant:resource:file:input/xml_input.csv")
						
						.log("###################################")
						.log("${body}")
						.log("###################################")
						
						.process(new CustomProcessorXStream())
						
						.log("###################################")
						.log("${body}")
						.log("###################################")
						
						.marshal(xstreamDefinition)
						
						.log("###################################")
						.log("${body}")
						.log("###################################")
						
						.to("file:output?fileName=csv_output.xml")
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
