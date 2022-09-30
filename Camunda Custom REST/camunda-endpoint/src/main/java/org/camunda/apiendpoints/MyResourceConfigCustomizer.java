package org.camunda.apiendpoints;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.autoconfigure.jersey.ResourceConfigCustomizer;
import org.springframework.stereotype.Component;


@Component
public class MyResourceConfigCustomizer extends CustomRestService implements ResourceConfigCustomizer {

	 @Override
	  public void customize(ResourceConfig config) {
	    config.register(CustomRestService.class);
	  }

}
