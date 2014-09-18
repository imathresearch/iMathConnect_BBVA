package com.imath.connect.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class Photo {
    
    /*
     * Photo's treatment class. It will have all the photo treatment functions.
     * 
     * */
    
    public byte[] getPhotoByte(String urlPhoto) throws IOException {
        
        /*
         * 
         * Convert the photo to binary to store in the database.
         * 
         * */
        //String urlPhotoComplete = "/home/izubizarreta/git/iMathConnect/src/test/resources/com/iMath/connect/util/" + urlPhoto;
        InputStream in = this.getClass().getResourceAsStream(urlPhoto);
        //File file = new File(urlPhotoComplete);
        BufferedImage originalImage = ImageIO.read(in);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( originalImage, "jpg", baos );
        baos.flush();
        byte[] photoByte = baos.toByteArray();
        baos.close();
        return photoByte;
        
    }
    
    public byte[] getPhotoByte(InputStream inputStream) throws IOException {
        
        /*
         * 
         * Convert the photo to binary to store in the database.
         * 
         * */
        BufferedImage originalImage = ImageIO.read(inputStream);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write( originalImage, "jpg", baos );
        baos.flush();
        byte[] photoByte = baos.toByteArray();
        baos.close();
        return photoByte;
        
    }

}
