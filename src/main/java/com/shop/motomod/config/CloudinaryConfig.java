package com.shop.motomod.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {

	@Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dtuw5u1wp",
            "api_key", "578152319551827",
            "api_secret", "JxsXIoBImLLLc8llIFghQMD3ufk"
        ));
    }
}
