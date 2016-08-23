package com.clouway.downloadagent;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class DownloadAgent {
  private ProgressBar progressBar;


  public DownloadAgent(ProgressBar progressBar) {
    this.progressBar = progressBar;
  }


  public File downloadFile(String urlAsString, String destinationFile) throws IOException {
    URL url = new URL(urlAsString);
    URLConnection urlConnection = new URL(urlAsString).openConnection();
    Integer fileSize = urlConnection.getContentLength();
    InputStream inputStream = new BufferedInputStream(url.openStream());
    File file = new File(destinationFile);
    FileOutputStream outputStream=new FileOutputStream(file);
    byte[] buffer = new byte[512];
    Integer n;
    Integer progress = 0;
    while (-1 != (n = inputStream.read(buffer))) {
      outputStream.write(buffer, 0, n);
      progress += n;
      Integer percentage = (progress * 100) / fileSize;
      if (percentage % 10 == 0) {
        progressBar.update(percentage);
      }

    }
    outputStream.close();
    inputStream.close();
    return file;
  }
}
