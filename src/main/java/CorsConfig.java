import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Zezwalaj na wszystkie endpointy
                .allowedOrigins("http://localhost:5173") // Zezwalaj na ten frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Metody HTTP
                .allowedHeaders("*"); // Zezwalaj na wszystkie nagłówki
    }
}