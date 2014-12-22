import gate.MathServiceGateway;

import java.io.File;


public class MathService implements MathServiceGateway {

	public MathService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public File getFile(String message) {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return new File(message);
	}

}
