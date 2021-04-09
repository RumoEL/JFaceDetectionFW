package com.github.kvac.jfacedetection.services;

import com.github.kvac.jfacedetection.header.JFaceHEADER;
import com.github.kvac.jfacedetection.services.face.FaceService;
import com.github.kvac.jfacedetection.services.network.MinaServerHandler;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jdcs_dev
 */
public class Services {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Getter
    FaceService faceService = new FaceService();
    @Getter
    MinaServerHandler netwirkHandler = new MinaServerHandler();

    public void init() {
        String msgInit = "init";
        String msgInited = "inited";

        logger.info(msgInit);

        initFaces();
        initNetwork();

        logger.info(msgInited);

        //RUNNERS
        //files
        //web-cam
        //RUNNERS
    }

    private void initFaces() {
        faceService.init();
    }

    private void initNetwork() {
        if (JFaceHEADER.getConfig().getServicesConfig().getMinaServerConfig().isMinaServerMode()) {
            String networkInit = "networkInit";
            String networkInited = "networkInited";

            logger.info(networkInit);
            //TODO  START SERVER
            logger.info(networkInited);
        }
    }

}
