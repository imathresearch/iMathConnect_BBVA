package com.imath.connect.util;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;

public class PhotoTest {
    
    @Test
    public void testGetPhotoByte() throws IOException {
        
        Photo photo = new Photo();
        byte[] photoByte = photo.getPhotoByte(Constants.RECOVER_IMAGE_NAME);
        assertNotNull(photoByte);
        
    }

}
