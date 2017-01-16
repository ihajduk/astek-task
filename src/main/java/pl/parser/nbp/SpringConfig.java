package pl.parser.nbp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("pl.parser.nbp")
@PropertySource("classpath:config.properties")
public class SpringConfig {
}
