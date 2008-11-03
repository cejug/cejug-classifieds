package net.java.dev.cejug.classifieds.view.jsf;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller(value = "helloClassifiedsBean")
@Scope("request")
public class HelloClassifiedsBean {

	public String getHello() {
		return "Hello Classifieds Richfaces :D";
	}

}
