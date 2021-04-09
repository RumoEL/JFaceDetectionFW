package com.github.kvac.jfacedetection.configs.main.services;

import com.github.kvac.jfacedetection.configs.main.services.face.FaceServiceConfig;
import com.github.kvac.jfacedetection.configs.main.services.network.MinaServerConfig;
import lombok.Getter;

public class ServicesConfig {

    @Getter
    FaceServiceConfig faceServiceConfig = new FaceServiceConfig();
    @Getter
    MinaServerConfig minaServerConfig = new MinaServerConfig();

}
