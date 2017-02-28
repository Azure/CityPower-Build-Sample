package devCamp.WebApp.configurations;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
//@EnableWebMvc
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
	}


	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		final ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
		final List<MediaType> list = new ArrayList<>();
		list.add(MediaType.IMAGE_JPEG);
		list.add(MediaType.APPLICATION_OCTET_STREAM);
		arrayHttpMessageConverter.setSupportedMediaTypes(list);
		converters.add(arrayHttpMessageConverter);

		super.configureMessageConverters(converters);
	}
}
