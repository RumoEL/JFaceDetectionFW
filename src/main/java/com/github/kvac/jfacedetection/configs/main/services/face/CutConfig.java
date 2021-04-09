package com.github.kvac.jfacedetection.configs.main.services.face;

import lombok.Getter;
import lombok.Setter;

public class CutConfig {

    @Getter
    @Setter
    private boolean cut = false;
    @Getter
    @Setter
    private ResizeConfig resizeConfig = new ResizeConfig();
}
