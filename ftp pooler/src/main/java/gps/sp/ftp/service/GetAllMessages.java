package gps.sp.ftp.service;

import java.io.File;

import org.springframework.messaging.Message;

public class GetAllMessages {

	public GetAllMessages() {
	}
	
	public void printallMessages(Message<?> message) {
		String name = ((File) message.getPayload()).getName();
		System.err.println(message + " > ALLrecibido: " + name);
	}

}
