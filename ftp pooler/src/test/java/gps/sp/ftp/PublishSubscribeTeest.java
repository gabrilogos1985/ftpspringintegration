package gps.sp.ftp;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import examples.components.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:publish-subscribe.xml")
public class PublishSubscribeTeest {

	@Autowired
	Customer customer;

	@Test
	public void test() throws Exception {
		while (true) {
			customer.placeOrder();
			Thread.sleep(523);
		}
	}

}
