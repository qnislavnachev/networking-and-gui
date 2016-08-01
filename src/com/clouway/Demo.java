package com.clouway;

import java.io.IOException;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class Demo {
  public static void main(String[] args) throws IOException {
    DownloadAgent downloadAgent=new DownloadAgent();
    downloadAgent.downloadFile("https://d10ou7l0uhgg4f.cloudfront.net/wp-content/uploads/2015/10/06114920/online-url-bibanews.jpg","somefile.jpg");
    downloadAgent.downloadFile("File:/home/clouway/Downloads/online-url-bibanews.jpg","otherfile.jpg");
  }
}
