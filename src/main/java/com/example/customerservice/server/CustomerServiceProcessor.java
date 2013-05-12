/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.example.customerservice.server;

import com.example.customerservice.Customer;
import com.example.customerservice.CustomerService;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.cxf.common.message.CxfConstants;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceProcessor implements Processor {
    CustomerService customerService;

    @Override
    public void process(Exchange exchange) throws Exception {
        Message inMessage = exchange.getIn();

        String operationName = inMessage.getHeader(CxfConstants.OPERATION_NAME, String.class);

        if ("getCustomersByName".equals(operationName)) {
            String name = inMessage.getBody(String.class);
            List<Customer> customers = customerService.getCustomersByName(name);

            // return values in camel-cxf are in a List
            List c = new ArrayList();
            c.add(customers);
            exchange.getOut().setBody(c);

        } else if ("updateCustomer".equals(operationName)) {
            System.out.println("Update customer");
            exchange.getOut().setBody(null);
        }

    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
