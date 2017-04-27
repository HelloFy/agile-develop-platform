package cn.edu.xidian.platform.gen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

/**
 * Created by 费玥 on 2017-4-26.
 */
@org.springframework.context.annotation.Configuration
public class FreeMarkerConfig {

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() throws IOException {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        Resource resource = new ClassPathResource("/templates/gen/uml");
        freeMarkerConfigurer.setTemplateLoaderPaths(resource.getURI().toString(), "classpath:/templates/");
        return freeMarkerConfigurer;
    }
}
