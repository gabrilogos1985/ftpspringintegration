package gps.sp.ftp.synchronous;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:ftp.input.output.xml")
public class FtpIncomeDispatcherTest {

	@Autowired
	@Qualifier("publish-subscribe-file")
	PublishSubscribeChannel inFtpChannel;

	List<String> mensajes = new ArrayList<String>();
	ConcurrentLinkedQueue<String> fileNameQueue = new ConcurrentLinkedQueue<String>();
	{
		fileNameQueue.add("example3.txt");
		fileNameQueue.add("example1.txt");
		fileNameQueue.add("example2.txt");
	}


	@Test
	public void test() throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int counter = 0; counter < 3; counter++)
			exec.execute(() -> {
				inFtpChannel.subscribe(new MessageHandler() {

					@Override
					public void handleMessage(Message<?> message)
							throws MessagingException {
						String name = ((File) message.getPayload()).getName();
						System.err.println(message + " > recibido: " + name);
					}
				});
				while (true)
					;
			});
		exec.shutdown();
		exec.awaitTermination(30, TimeUnit.SECONDS);
		System.out.println(mensajes);
	}
}
