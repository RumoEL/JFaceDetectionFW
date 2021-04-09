/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kvac.jfacedetection.configs.main.services.face;

import com.github.kvac.jfacedetection.configs.main.ColorConfig;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jdcs_dev
 */
public class RectanglesConfig {

    @Getter
    @Setter
    private boolean rectangles = false;
    @Getter
    @Setter
    private boolean painted = false;
    /**
     * AAAA
     */
    @Getter
    @Setter
    private ColorConfig colorConfig = new ColorConfig();
}
