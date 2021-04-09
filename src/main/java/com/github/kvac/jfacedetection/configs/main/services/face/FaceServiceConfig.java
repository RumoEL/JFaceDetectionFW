package com.github.kvac.jfacedetection.configs.main.services.face;

import java.io.File;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jdcs_dev
 */
public class FaceServiceConfig {

    @Getter
    private File inputDir = new File("inputDir");

    @Getter
    private File outputDir = new File("outputDir");

    @Getter
    @Setter
    private boolean orig = false;

    @Getter
    private CutConfig cutConfig = new CutConfig();
    @Getter
    private RectanglesConfig rectanglesConfig = new RectanglesConfig();

    @Getter
    @Setter
    private boolean delete = false;

}
