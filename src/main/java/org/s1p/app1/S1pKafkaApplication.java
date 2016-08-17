package org.s1p.app1;

import org.s1p.CommonConfiguration;
import org.s1p.ConfigProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;

/**
 *
 */
@SpringBootApplication
@Import({ CommonConfiguration.class, ConfigProperties.class })
public class S1pKafkaApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(S1pKafkaApplication.class)
			.web(false)
			.run(args);
		TestBean testBean = context.getBean(TestBean.class);
		testBean.send("foo");
	}

	@Bean
	public TestBean test() {
		return new TestBean();
	}

	public static class TestBean {

		@Autowired
		private ConfigProperties configProperties;

		@Autowired
		private KafkaTemplate<String, String> template;

		public void send(String foo) {
			this.template.send(this.configProperties.getTopic(), foo);
		}

	}

}
