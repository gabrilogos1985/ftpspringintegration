package gps.sp.ftp;

import static org.junit.Assert.*;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import gate.MathServiceGateway;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:gateway-bidrectional.xml")
public class GatewayTest {

	@Autowired
	@Qualifier("mathService")
	MathServiceGateway gateway;

	@Test
	public void test() throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int counter = 0; counter < 130; counter++)
			exec.execute(new Runnable() {

				@Override
				public void run() {
					String expected=UUID.randomUUID().toString();
					File file = gateway.getFile(expected);
					assertEquals(expected, file.getName());
					System.out.println(file.getAbsolutePath());
				}
			});
		exec.shutdown();
		exec.awaitTermination(10, TimeUnit.SECONDS);
	}
}
