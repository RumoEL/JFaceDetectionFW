package com.github.kvac.jfacedetection.services.face;

import lombok.Getter;
import lombok.Setter;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.opencv.opencv_java;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jdcs_dev
 */
public class FaceService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Getter
    @Setter
    CascadeClassifier csc;
    String cscXmlFile = "/usr/local/share/opencv4/haarcascades/haarcascade_frontalface_alt2.xml";

    public void init() {
        String faceInit = "faceInit";
        String faceInited = "faceInited";

        logger.info(faceInit);

        initLib();
        initClassifiers();

        register();
        logger.info(faceInited);
    }

    private void initLib() {
        String libInit = "initLib";
        logger.info(libInit);

        long first = System.currentTimeMillis();

        Loader.load(opencv_java.class);

        long last = System.currentTimeMillis();
        String libInited = "initLib on " + ((last - first) / 1000) + " seconds";

        logger.info(libInited);
    }
    //

    /**
     *
     * String xmlFile =
     * "/usr/local/share/opencv4/haarcascades/haarcascade_frontalcatface.xml";
     * String xmlFile =
     * "/usr/local/share/opencv4/haarcascades/haarcascade_frontalcatface_extended.xml";
     * String xmlFile =
     * "/usr/local/share/opencv4/haarcascades/haarcascade_frontalface_alt.xml";
     * String xmlFile =
     * "/usr/local/share/opencv4/haarcascades/haarcascade_frontalface_alt_tree.xml";
     * String xmlFile =
     * "/usr/local/share/opencv4/haarcascades/haarcascade_frontalface_default.xml";//?
     * String xmlFile =
     * "/usr/local/share/opencv4/haarcascades/haarcascade_profileface.xml";
     */
    public void initClassifiers() {
        setCsc(new CascadeClassifier(cscXmlFile));
    }

    private void register() {
        //TODO
    }
}
