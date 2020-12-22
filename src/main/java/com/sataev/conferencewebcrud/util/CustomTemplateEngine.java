package com.sataev.conferencewebcrud.util;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Component
public class CustomTemplateEngine extends TemplateEngine {

	private ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
	    SpringTemplateEngine engine = new SpringTemplateEngine();
	    engine.addDialect(new Java8TimeDialect());
	    engine.setTemplateResolver(templateResolver);
	    return engine;
	}
	
}
