package com.assignment.su22;

import com.assignment.su22.game.impl.HummingbirdGameImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

@SpringBootApplication
@Slf4j
public class HummingbirdApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(HummingbirdApplication.class, args);

        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:hummingbird_map.txt");
        HummingbirdGameImpl hummingbirdGame = new HummingbirdGameImpl();
        hummingbirdGame.hummingbirdGame(resource);

    }
}
