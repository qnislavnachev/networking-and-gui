package com.clouway.downloadagent;

import java.io.BufferedInputStream;
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
  private Integer stepSize;

  public DownloadAgent(ProgressBar progressBar, Integer stepSize) {
    this.progressBar = progressBar;
    this.stepSize = stepSize;
  }

  public File downloadFile(String path, String dist) throws IOException {
    URL url = new URL(path);
    URLConnection urlConnection = new URL(path).openConnection();

    Integer fileSize = urlConnection.getContentLength();
    InputStream input = new BufferedInputStream(url.openStream());

    File file = new File(dist);
    FileOutputStream output=new FileOutputStream(file);

    byte[] buffer = new byte[stepSize];
    Integer n;
    Integer progress = 0;
    while ((n = input.read(buffer)) != -1) {
      output.write(buffer, 0, n);
      progress += n;
      Integer percentage = (progress * 100) / fileSize;
      if (percentage % 10 == 0) {
        progressBar.update(percentage);
      }
    }
    output.close();
    input.close();
    return file;
  }
}
