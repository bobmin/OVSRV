package bob.spring.bobobjs.store;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

@SpringBootApplication
public class ObjectValueStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObjectValueStoreApplication.class, args);
    }
	
	@ExceptionHandler
	void handleIllegalArgumentException(
			IllegalArgumentException e, HttpServletResponse response) 
					throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

}
