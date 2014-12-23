package gps.sp.ftp;

import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryContext;

public class MyHandlerAdvice implements RecoveryCallback<Object> {

	public MyHandlerAdvice() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object recover(RetryContext context) throws Exception {
		System.out.println("Error: "+context);
		context.setAttribute("count", 0);
		return false;
	}

}
