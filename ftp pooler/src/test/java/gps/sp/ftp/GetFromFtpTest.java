package gps.sp.ftp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.PollableChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test/find-on.ftp.xml")
public class GetFromFtpTest implements ApplicationContextAware {
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	private static final int FILES = 5;
	volatile List<File> mensajes = new ArrayList<File>();
	ConcurrentLinkedQueue<String> fileNameQueue = new ConcurrentLinkedQueue<String>();
	private ApplicationContext appContext;
	{
		for (int counter = 0; counter < FILES; counter++) {
			fileNameQueue
					.add(String.format("/facturas/example%s.txt", counter));
		}
	}

	@Test
	public void test() throws Exception {
		System.out.println(folder.getRoot());
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int counter = 0; counter < FILES; counter++)
			exec.execute(new Runnable() {

				PollableChannel outputchannel;
				@Autowired
				@Qualifier("ftpFailDeleteChannel")
				PollableChannel failDeletechannel;

				@Override
				public void run() {
					String payloadThread = fileNameQueue.poll();
					doJob(payloadThread);

				}

				private void doJob(final String payload) {
					Message<String> message = MessageBuilder
							.withPayload(payload)
							.setHeader("path", payload)
							.setHeader("pathTestDirectory",
									folder.getRoot().getAbsolutePath()).build();
					MessageChannel inputChannel = (MessageChannel) appContext
							.getBean("inbound1");
					outputchannel = (PollableChannel) appContext
							.getBean("downloadedChannel");
					failDeletechannel = (PollableChannel) appContext
							.getBean("ftpFailDeleteChannel");
					System.out.println("Sent... " + payload + " "
							+ inputChannel.send(message));
					File fileReceived = (File) outputchannel.receive(25000)
							.getPayload();
					System.out.println("DOWNLOAD: " + fileReceived);
					Object errorDeleteFile = failDeletechannel.receive(25000)
							.getPayload();
					System.out.println("ERRORS: " + errorDeleteFile);
//					Assert.assertEquals(new File(payload).getName(),
//							fileReceived.getName());
					mensajes.add(fileReceived);
				}
			});
		exec.shutdown();
		exec.awaitTermination(3000, TimeUnit.SECONDS);
		Assert.assertEquals(FILES, mensajes.size());
	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		this.appContext = arg0;

	}

}
