package com.ssafy.a401.artwalk_backend.global.error;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;

public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

	@Override
	public void customize(ConfigurableWebServerFactory factory) {
		ErrorPage errorPage = new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/4xx");
		factory.addErrorPages(errorPage);
	}
}
