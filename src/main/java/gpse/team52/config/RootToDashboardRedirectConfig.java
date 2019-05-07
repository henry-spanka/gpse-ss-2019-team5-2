package gpse.team52.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Redirect the Root URL (/) to /dashboard.
 */
@Configuration
public class RootToDashboardRedirectConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(final ViewControllerRegistry registry) {
        //registry.addRedirectViewController("/", "/dashboard");
        registry.addRedirectViewController("/", "/startpage");
    }
}
