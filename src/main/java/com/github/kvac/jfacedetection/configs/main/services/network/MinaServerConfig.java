package com.github.kvac.jfacedetection.configs.main.services.network;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jdcs_dev
 */
public class MinaServerConfig {

    @Getter
    @Setter
    private boolean minaServerMode = false;
    @Getter
    @Setter
    private int serverPort = 23000;

}
