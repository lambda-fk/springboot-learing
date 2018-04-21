package miaosha.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;


//@Order(Ordered.LOWEST_PRECEDENCE)
public class DevToolsPropertyDefaultsPostProcessor implements EnvironmentPostProcessor  {

	private static final Map<String, Object> PROPERTIES;
	static {
		Map<String, Object> devToolsProperties = new HashMap<String, Object>();
		devToolsProperties.put("spring.thymeleaf.cache", "false");
		devToolsProperties.put("spring.freemarker.cache", "false");
		devToolsProperties.put("spring.groovy.template.cache", "false");
		devToolsProperties.put("spring.mustache.cache", "false");
		devToolsProperties.put("server.servlet.session.persistent", "true");
		devToolsProperties.put("spring.h2.console.enabled", "true");
		devToolsProperties.put("spring.resources.cache.period", "0");
		devToolsProperties.put("spring.resources.chain.cache", "false");
		devToolsProperties.put("spring.template.provider.cache", "false");
		devToolsProperties.put("spring.mvc.log-resolved-exception", "true");
		devToolsProperties.put("server.servlet.jsp.init-parameters.development", "true");
		devToolsProperties.put("spring.reactor.stacktrace-mode.enabled", "true");
		PROPERTIES = Collections.unmodifiableMap(devToolsProperties);
	}
	
	public void postProcessEnvironment(ConfigurableEnvironment environment,
			SpringApplication application) {
		if (isLocalApplication(environment) && canAddProperties(environment)) {
			PropertySource<?> propertySource = new MapPropertySource("refresh",
					PROPERTIES);
			environment.getPropertySources().addLast(propertySource);
		}
	}

	private boolean isLocalApplication(ConfigurableEnvironment environment) {
		return environment.getPropertySources().get("remoteUrl") == null;
	}

	private boolean canAddProperties(Environment environment) {
		return isRestarterInitialized() || isRemoteRestartEnabled(environment);
	}

	private boolean isRestarterInitialized() {
		try {
			//Restarter restarter = Restarter.getInstance();
			//return (restarter != null && restarter.getInitialUrls() != null);
		}
		catch (Exception ex) {
			return false;
		}
		return true;
	}

	private boolean isRemoteRestartEnabled(Environment environment) {
		return environment.containsProperty("spring.devtools.remote.secret");
	}


}
