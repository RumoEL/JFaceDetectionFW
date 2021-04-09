/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.kvac.jfacedetection.configs.main;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author jdcs_dev
 */
public class ColorConfig {

    @Getter
    @Setter
    double red = 255d;
    @Getter
    @Setter
    double green = 0d;
    @Getter
    @Setter
    double blue = 0d;

    @Getter
    @Setter
    int thickness = 3;
}
