package com.clouway;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class DownloadAgent {

  public File downloadFile(String url,String destinationFile) throws IOException {
    URL urlForFile=new URL(url);
    InputStream inputStream=new BufferedInputStream(urlForFile.openStream());
    ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
    byte[] buffer=new byte[1024];
    int n=0;
    while (-1!=(n=inputStream.read(buffer)))
    {
      outputStream.write(buffer, 0, n);
    }
    outputStream.close();
    inputStream.close();
    byte[] response = outputStream.toByteArray();
    return writeFileTo(destinationFile,response);
  }

  private File writeFileTo(String name,byte[] bytes) throws IOException {
    File file=new File(name);
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    fileOutputStream.write(bytes);
    fileOutputStream.close();
    return file;
  }
}
