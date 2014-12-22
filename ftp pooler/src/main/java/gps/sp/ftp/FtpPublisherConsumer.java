package gps.sp.ftp;

import java.io.File;

import org.springframework.messaging.MessageChannel;

public class FtpPublisherConsumer implements FtpBidireccionalService {

	private MessageChannel ftpUploadChannel;

	@Override
	public File getBidirectionalFile(String message) {
		return null;
	}

	public void setFtpUploader(MessageChannel inputMessageChannel) {
		this.ftpUploadChannel = inputMessageChannel;
	}

}
