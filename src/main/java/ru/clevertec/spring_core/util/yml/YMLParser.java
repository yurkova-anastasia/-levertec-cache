package ru.clevertec.spring_core.util.yml;

import lombok.Getter;
import lombok.SneakyThrows;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;

@Getter
public class YMLParser {

    private final Properties yaml;

    @SneakyThrows
    public YMLParser() {
        Yaml yaml = new Yaml(new Constructor(Properties.class, new LoaderOptions()));
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("application.yml");
        this.yaml = yaml.load(inputStream);
        if (inputStream != null) {
            inputStream.close();
        }
    }
}