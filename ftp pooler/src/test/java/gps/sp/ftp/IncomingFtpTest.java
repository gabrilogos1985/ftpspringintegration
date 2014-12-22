package gps.sp.ftp;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:ftp.input.output.xml")
public class IncomingFtpTest {
	@Autowired
	@Qualifier("facturasIncome")
	PollableChannel inFtpChannel;

	@Test
	public void test() {
		for (int messageCounter = 0; messageCounter < 2; messageCounter++) {
			Message<File> fileMessage = (Message<File>) inFtpChannel.receive();
			assertTrue(fileMessage.getPayload().exists());
		}
	}
}
