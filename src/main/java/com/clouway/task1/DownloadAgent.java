package com.clouway.task1;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class DownloadAgent {
    private ProgressBar progressBar;
    private int counter;

    public DownloadAgent(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void downloadFile(String url, String dist) throws Exception {
        int progress;
        counter = 0;
        int marker = url.lastIndexOf(".");
        String format = url.substring(marker);

        URL urlConnection = new URL(url);
        URLConnection urlCon = urlConnection.openConnection();
        InputStream in = new BufferedInputStream(urlCon.getInputStream());
        OutputStream out = new BufferedOutputStream( new FileOutputStream(dist + format));

        int transfer = 0;
        while((transfer = in.read()) != -1){
            counter++;
            if(counter >= 5000 && counter % 5000 == 0){
                progress = progressBar.getProgress();
                System.out.println(progress + "% downloaded!");
            }
            out.write(transfer);
        }
        progress = progressBar.getProgress();
        System.out.println(progress + "% downloaded!");

        in.close();
        out.close();
    }

}
