package com.clouway;

import java.io.*;
import java.net.MalformedURLException;
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

  public File downloadFile(String url, String destinationFile) throws IOException {
    URL urlForFile = new URL(url);
    URLConnection urlConnection = new URL(url).openConnection();
    Integer fileSize = urlConnection.getContentLength();
    InputStream inputStream = new BufferedInputStream(urlForFile.openStream());
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    Integer bufferSize = fileSize / 10;
    byte[] buffer = new byte[bufferSize];
    Integer n = 0;
    Integer progress = 10;
    while (-1 != (n = inputStream.read(buffer))) {
      outputStream.write(buffer, 0, n);
      progressBar.downloadProgress(progress);
      progress += 10;

    }
    outputStream.close();
    inputStream.close();
    byte[] response = outputStream.toByteArray();
    return writeFileTo(destinationFile, response);
  }

  private File writeFileTo(String name, byte[] bytes) throws IOException {
    File file = new File(name);
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    fileOutputStream.write(bytes);
    fileOutputStream.close();
    return file;
  }
}
