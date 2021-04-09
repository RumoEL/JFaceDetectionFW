package com.github.kvac.jfacedetection.init;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.github.kvac.jfacedetection.configs.main.AppConfig;
import com.github.kvac.jfacedetection.header.JFaceHEADER;
import com.github.kvac.jfacedetection.services.Services;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.kvac.jfacedetection.utils.ImagesUtils;

public class JFaceDetectionInit {

    Services services = new Services();

    Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        JFaceDetectionInit jFaceDetectionInit = new JFaceDetectionInit();
        try {
            jFaceDetectionInit.initConfig();
            jFaceDetectionInit.services.init();

            jFaceDetectionInit.start();
        } catch (IOException e) {
            jFaceDetectionInit.logger.error("START", e);
        }
    }

    private void initConfig() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        if (!JFaceHEADER.getConfigFile().exists()) {
            mapper.writeValue(JFaceHEADER.getConfigFile(), JFaceHEADER.getConfig());
        }
        JFaceHEADER.setConfig(mapper.readValue(JFaceHEADER.getConfigFile(), AppConfig.class));
    }

    private void start() {
        if (!JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getInputDir().exists()) {
            JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getInputDir().mkdirs();
        }
        if (!JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getOutputDir().exists()) {
            JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getOutputDir().mkdirs();
        }

        ArrayList<File> files = new ArrayList<>();

        File[] filesTmp = JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getInputDir().listFiles();
        for (File file : filesTmp) {
            if (file.isFile()) {
                files.add(file);
            }
        }

        for (File file : files) {
            try {

                //TEST
                //VideoInputFrameGrabber aa;
                //OpenCVFrameGrabber frameGrabber = new OpenCVFrameGrabber(file);
                //TEST
                //
                FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(file.getAbsolutePath());//FFMPEG
                frameGrabber.start();
                File store = new File(JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getOutputDir().getAbsolutePath() + File.separator + file.getName());
                int imageIndex = 0;

                //VARS
                boolean isOrig = JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().isOrig();

                boolean isCut = JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getCutConfig().isCut();
                boolean isResizeCopy = JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getCutConfig().getResizeConfig().isResizeCopy();
                int resizeHeight = JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getCutConfig().getResizeConfig().getResize_height();
                int resizeWidth = JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getCutConfig().getResizeConfig().getResize_width();

                boolean isRectangles = JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getRectanglesConfig().isRectangles();
                boolean isPainted = JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getRectanglesConfig().isPainted();
                double red = JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getRectanglesConfig().getColorConfig().getRed();
                double green = JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getRectanglesConfig().getColorConfig().getGreen();
                double blue = JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getRectanglesConfig().getColorConfig().getBlue();
                int thickness = JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().getRectanglesConfig().getColorConfig().getThickness();
                //VARS
                //
                //SAVE ORIGINAL
                if (isOrig) {
                    try {
                        File storeOrigDir = new File(store, "ORIG");
                        File storeOrig = new File(storeOrigDir, file.getName());
                        if (!storeOrigDir.exists()) {
                            storeOrigDir.mkdirs();
                        }
                        Files.copy(file.toPath(), storeOrig.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        logger.info(storeOrig.getName() + " сохранён");
                    } catch (IOException e) {
                        logger.error("ORIGINAL", e);
                    }
                }//SAVE ORIGINAL

                if (isRectangles && isPainted) {
                    logger.info("red:" + red + " green:" + green + " blue:" + blue + " thickness:" + thickness);
                }
                while (true) {
                    Frame image = frameGrabber.grabImage();
                    //Frame image = frameGrabber.grabImage();//FFMPEG
                    MatOfRect objectsTo = new MatOfRect();
                    Java2DFrameConverter jFrameConverter = new Java2DFrameConverter();
                    BufferedImage bufImage = jFrameConverter.convert(image);

                    Mat src = ImagesUtils.matify(bufImage);
                    if (src == null) {
                        logger.info("пустой");
                        break;
                    }
                    Mat cutSrc = src.clone();
                    Mat recSrc = src.clone();

                    /**
                     *
                     * int absoluteFaceSize = 0; int height = src.rows(); if
                     * (Math.round(height * 0.2f) > 0) { absoluteFaceSize =
                     * Math.round(height * 0.01f); }
                     */
                    this.services.getFaceService().getCsc().detectMultiScale(src, objectsTo);//DETECT

                    if (objectsTo.toArray().length > 0) {

                        logger.info(String.format("Detected faces: %d", objectsTo.toArray().length));

                        Rect[] rects = objectsTo.toArray();
                        for (int i = 0; i < rects.length; i++) {
                            Rect origRect = rects[i];
                            Rect cutRect = origRect.clone();
                            /**
                             * Rect resizeRect = origRect.clone();
                             *
                             */
                            Rect rectanglesRect = origRect.clone();
                            // CUT
                            if (isCut) {
                                Rect roi = new Rect(new Point(cutRect.x, cutRect.y),
                                        new Point(cutRect.x + cutRect.width, cutRect.y + cutRect.height));
                                Mat submat = cutSrc.submat(roi);

                                File cutDir = new File(store.getAbsolutePath() + File.separator + "Cut");
                                File cutFile = new File(
                                        cutDir,
                                        +imageIndex + "_"
                                        + i
                                        + '.'
                                        + "png"
                                );// FIXME
                                // переписать
                                // имя
                                // потомка
                                if (!cutDir.exists()) {
                                    cutDir.mkdirs();
                                }

                                Imgcodecs.imwrite(cutFile.toString(), submat);

                                //HASH NAME
                                if (cutFile.renameTo(
                                        new File(
                                                cutDir,
                                                ImagesUtils.computeFileSHA1(cutFile)
                                                + ".png")
                                )) {
                                    //IGNORE
                                }
                                if (isResizeCopy) {
                                    try {
                                        File resizeDirForFile = new File(cutDir, "reSized");
                                        if (!resizeDirForFile.exists()) {
                                            resizeDirForFile.mkdirs();
                                        }
                                        File reSizedFile = new File(
                                                resizeDirForFile,
                                                imageIndex
                                                + "_"
                                                + i
                                                + '.'
                                                + "png");

                                        BufferedImage resizedImage = ImagesUtils.resize(
                                                ImagesUtils.Mat2BufferedImage(submat),
                                                resizeHeight,
                                                resizeWidth
                                        );
                                        Imgcodecs.imwrite(reSizedFile.toString(), ImagesUtils.matify(resizedImage));

                                        if (reSizedFile.renameTo(
                                                new File(
                                                        resizeDirForFile,
                                                        ImagesUtils.computeFileSHA1(reSizedFile) + ".png")
                                        )) {
                                            //IGNORE
                                        }

                                    } catch (IOException e) {
                                        logger.error("", e);
                                    }
                                }// resize
                            }//CUT

                            //Rectangles
                            if (isRectangles) {
                                if (isPainted) {

                                    Imgproc.rectangle(recSrc, new Point(rectanglesRect.x, rectanglesRect.y),
                                            new Point(rectanglesRect.x + rectanglesRect.width,
                                                    rectanglesRect.y + rectanglesRect.height),
                                            new Scalar(
                                                    red,
                                                    green,
                                                    blue),
                                            thickness);
                                }

                                File rectDir = new File(store.getAbsolutePath(), "Rect");
                                File rectFile = new File(rectDir, +imageIndex + ".png");
                                if (!rectDir.exists()) {
                                    rectDir.mkdirs();
                                }
                                Imgcodecs.imwrite(rectFile.toString(), recSrc);
                                /**
                                 * rectFile.renameTo(new File(rectDir +
                                 * File.separator +
                                 * ImagesUtils.computeFileSHA1(rectFile) +
                                 * ".png"));
                                 */
                            }//Rectangles
                        }
                    }
                    imageIndex++;
                }
                if (JFaceHEADER.getConfig().getServicesConfig().getFaceServiceConfig().isDelete()) {
                    delete(file);
                }

            } catch (IOException eee) {
                logger.error("for", eee);
            }

        }

    }

    private void delete(File file) throws IOException {
        Files.delete(file.toPath());
        logger.info(file + " is deleted");
    }

}
