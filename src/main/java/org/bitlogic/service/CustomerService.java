package org.bitlogic.service;

import java.util.List;

import org.bitlogic.model.Customer;
import org.bitlogic.model.CustomerVerification;

public interface CustomerService 
{

	public Customer saveCustomer(Customer c);

	public List<Customer> getCustomers();

	public List<Customer> getCreatedCustomers(String customerStatus);

	public Customer getCustomer(int cid);

	public Customer customerDocVerification(CustomerVerification cv, int cid);

}
