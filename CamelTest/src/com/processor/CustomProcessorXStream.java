package com.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;

import com.bean.Employee;

public class CustomProcessorXStream implements org.apache.camel.Processor {
	public void process(Exchange exchange) throws Exception {

		List<Employee> emplList = new ArrayList<Employee>();

		for (int i = 0; i <= 10; i++) {
			Employee employee = new Employee();
			employee.setEmployeeID("101");
			employee.setName("rahul");
			employee.setSalary("10000");

			emplList.add(employee);
		}

		exchange.getIn().setBody(emplList);
	}
}
