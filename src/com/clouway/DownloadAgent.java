package com.clouway;

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

  /**
   * Constructor
   * @param progressBar visual feedback for progress.
   */
  public DownloadAgent(ProgressBar progressBar) {
    this.progressBar = progressBar;
  }

  /**
   * Downloads a file.
   * @param url for the file
   * @param destinationFile the place to save the file
   * @return the donwloaded file
   * @throws IOException
   */
  public File downloadFile(String url, String destinationFile) throws IOException {
    URL urlForFile = new URL(url);
    URLConnection urlConnection = new URL(url).openConnection();
    Integer fileSize = urlConnection.getContentLength();
    InputStream inputStream = new BufferedInputStream(urlForFile.openStream());
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[512];
    Integer n;
    Integer progress = 0;
    while (-1 != (n = inputStream.read(buffer))) {
      outputStream.write(buffer, 0, n);
      progress += n;
      Integer percentage = (progress * 100) / fileSize;
      if (percentage % 10 == 0) {
        progressBar.downloadProgress(percentage);
      }

    }
    outputStream.close();
    inputStream.close();
    byte[] result = outputStream.toByteArray();
    return writeFileTo(destinationFile, result);
  }

  /**
   * Write the downloaded file to the destination we desire.
   * @param name name of the file
   * @param bytes the byte buffer where the file is.
   * @return the downloaded file.
   * @throws IOException
   */
  private File writeFileTo(String name, byte[] bytes) throws IOException {
    File file = new File(name);
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    fileOutputStream.write(bytes);
    fileOutputStream.close();
    return file;
  }
}
