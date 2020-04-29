package z.coffee.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.CorsFilter;
import z.coffee.blog.comm.spring.SpringContextHolder;

@SpringBootApplication
@ComponentScan("z.coffee.blog.*")
public class BlogApplication {
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public SpringContextHolder springContextHolder() {
		return new SpringContextHolder();
	}
}
