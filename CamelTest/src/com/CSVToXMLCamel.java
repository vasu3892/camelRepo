package com;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.dataformat.xstream.XStreamDataFormat;
import org.apache.camel.impl.DefaultCamelContext;

import com.bean.Employee;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class CSVToXMLCamel {

	public static void main(String args[]) throws Exception {

		CamelContext camelContext = new DefaultCamelContext();
		try {
			camelContext.addRoutes(new RouteBuilder() {
				public void configure() {
					
					BindyCsvDataFormat bindy = new BindyCsvDataFormat(Employee.class);
					
					XStream xStream = new XStream(new StaxDriver());
					xStream.alias("root", List.class);
					xStream.alias("employee", Employee.class);
					
					from("direct:myRoute")
						.to("language:constant:resource:file:input/xml_input.csv")
						
						.log("###################################")
						.log("${body}")
						
						.unmarshal(bindy)
						
						.log("###################################")
						.log("${body}")
						
						.marshal(new XStreamDataFormat(xStream))
						
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
