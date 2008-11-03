package net.java.dev.cejug.classifieds.richfaces.view;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller(value = "helloClassifiedsBean")
@Scope("request")
public class HelloClassifiedsBean {

	public String getHello() {
		return "Hello Classifieds Richfaces :D";
	}

}
