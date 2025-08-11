package com.herkat.config;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        // Busca primero en variables de entorno del sistema
        String cloudinaryUrl = System.getenv("CLOUDINARY_URL");

        // Si no encuentra, busca en el archivo .env (modo local)
        if (cloudinaryUrl == null || cloudinaryUrl.isBlank()) {
            Dotenv dotenv = Dotenv.configure()
                    .ignoreIfMissing() // No lanza error si no hay .env
                    .load();
            cloudinaryUrl = dotenv.get("CLOUDINARY_URL");
        }

        // Validación final
        if (cloudinaryUrl == null || cloudinaryUrl.isBlank()) {
            throw new IllegalStateException("No se encontró la variable CLOUDINARY_URL");
        }

        return new Cloudinary(cloudinaryUrl);
    }
}
