package com.clouway.test.task1;

import com.clouway.task1.DownloadAgent;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class DownloadAgentTest {
    DownloadAgent download = new DownloadAgent();

    @Test
    public void downloadJPG() throws Exception {
        download.downloadFile("file:/home/clouway/workspaces/idea/networking-and-gui/1.jpg","copy1");

        InputStream in = new BufferedInputStream(new FileInputStream("copy1.jpg"));
        byte[] imageOne = IOUtils.toByteArray(in);
        in = new BufferedInputStream(new FileInputStream("/home/clouway/workspaces/idea/networking-and-gui/1.jpg"));
        byte[] imageTwo = IOUtils.toByteArray(in);
        in.close();

        assertEquals(imageOne.length, imageTwo.length);
    }

}
