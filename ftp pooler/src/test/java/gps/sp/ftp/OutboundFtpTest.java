package gps.sp.ftp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:ftp.input.output.xml")
public class OutboundFtpTest {
	@Autowired
	@Qualifier("ftpChannel")
	MessageChannel outFtpChannel;

	@Test
	public void test() throws Exception {

		ExecutorService exec = Executors.newCachedThreadPool();
		for (int counter = 0; counter < 30; counter++)
			exec.execute(new Runnable() {

				@Override
				public void run() {
					Message<String> message = MessageBuilder.withPayload(
							"Hello").build();
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					System.out.println("E N VI A D O: "
							+ outFtpChannel.send(message));
				}
			});
		exec.shutdown();
		exec.awaitTermination(160, TimeUnit.SECONDS);
	}

}
