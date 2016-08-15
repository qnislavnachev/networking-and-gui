package com.clouway;

import java.io.IOException;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class Demo {
  public static void main(String[] args) throws IOException {


    ProgressBar progressBar = new ProgressBar() {
      @Override
      public Integer downloadProgress(Integer progress) {
        return null;
      }
    };
    DownloadAgent downloadAgent = new DownloadAgent(progressBar);
    downloadAgent.downloadFile("https://youtu.be/b1RKaRgVFKk", "test");
  }
}

