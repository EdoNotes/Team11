package test;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Entities.Customer;
import Gui.CancelOrderController;
import jdk.nashorn.internal.objects.annotations.Setter;
import junit.framework.TestCase;

public class MoreThan3Hours extends TestCase 
{
	@BeforeClass
	public void SetCustomer()
	{
		Customer cus1=new Customer();
		cus1.setCustomerID(2468);
	}

	@Test
	public void  checkId()
	{

	}
	
	

}
