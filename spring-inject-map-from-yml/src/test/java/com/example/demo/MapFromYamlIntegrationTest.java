package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MapFromYamlIntegrationTest {

    @Autowired
    private ServerProperties serverProperties;

    @Test
    public void whenYamlFileProvidedThenInjectSimpleMap() {
        assertThat(serverProperties.getApplication())
                .containsOnlyKeys("name", "url", "description");

        assertThat(serverProperties.getApplication()
                .get("name")).isEqualTo("InjectMapFromYAML");
    }

    @Test
    public void whenYamlFileProvidedThenInjectComplexMap() {
        assertThat(serverProperties.getConfig()).hasSize(2);

        assertThat(serverProperties.getConfig()
                .get("ips")
                .get(0)).isEqualTo("10.10.10.10");

        assertThat(serverProperties.getUsers()
                .get("root")
                .getUsername()).isEqualTo("root");
    }

}