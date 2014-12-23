package gps.sp.ftp;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test/find-on.ftp.xml")
public class GetFromFtpTest {
	@Autowired
	@Qualifier("inbound1")
	MessageChannel inputChannel;
	@Autowired
	@Qualifier("channel2")
	PollableChannel outputchannel;

	@Test
	public void test() {
		Message<String> message = MessageBuilder.withPayload(
				"/facturas/get1.txt").setHeader("path", "/facturas/get1.txt").build();
			inputChannel.send(message);
			System.out.println("Sent...");
		System.out.println(outputchannel.receive().getPayload());
	}
}
