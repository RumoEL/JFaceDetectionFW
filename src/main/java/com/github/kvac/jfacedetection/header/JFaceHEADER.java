package com.github.kvac.jfacedetection.header;

import com.github.kvac.jfacedetection.configs.main.AppConfig;
import java.io.File;
import lombok.Getter;
import lombok.Setter;

public class JFaceHEADER {

    private JFaceHEADER() {
    }
    @Getter
    private static File configFile = new File("config.yml");

    @Getter
    @Setter
    private static AppConfig config = new AppConfig();

}
