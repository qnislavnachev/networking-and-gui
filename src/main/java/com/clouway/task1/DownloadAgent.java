package com.clouway.task1;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class DownloadAgent {

    public void downloadFile(String fileUrl, String targetUrl) throws Exception {
        int marker = fileUrl.lastIndexOf(".");
        String format = fileUrl.substring(marker);

        URL url = new URL(fileUrl);
        URLConnection urlCon = url.openConnection();
        InputStream in = new BufferedInputStream(urlCon.getInputStream());
        OutputStream out = new BufferedOutputStream( new FileOutputStream(targetUrl + format));

        int transfer = 0;
        while((transfer = in.read()) != -1){
            out.write(transfer);
        }

        in.close();
        out.close();
    }

}
