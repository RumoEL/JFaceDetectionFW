package com.github.kvac.jfacedetection.configs.main.services.face;

import lombok.Getter;
import lombok.Setter;

public class ResizeConfig {

    @Getter
    @Setter
    private boolean resizeCopy = false;
    @Getter
    @Setter
    private int resize_width = 200;
    @Getter
    @Setter
    private int resize_height = 200;

}
