package com.clouway.task1;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class DownloadAgent {

    private int counter;

    public void downloadFile(String fileUrl, String targetUrl) throws Exception {
        counter = 0;
        int marker = fileUrl.lastIndexOf(".");
        String format = fileUrl.substring(marker);

        URL url = new URL(fileUrl);
        URLConnection urlCon = url.openConnection();
        InputStream in = new BufferedInputStream(urlCon.getInputStream());
        OutputStream out = new BufferedOutputStream( new FileOutputStream(targetUrl + format));

        int transfer = 0;
        while((transfer = in.read()) != -1){
            counter++;
            if(counter >= 1000 && counter % 1000 == 0){
                System.out.println("Progress: " + getDownloadSize() + "kB");
            }
            out.write(transfer);
        }
        System.out.println("Final size: " + getDownloadSize() + "kB");

        in.close();
        out.close();
    }

    public int getDownloadSize(){
        return (counter / 1000);
    }

}
