package com.clouway;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class DownloadAgentTest {
  @Test
  public void downloadingFile() throws Exception {
    DownloadAgent downloadAgent = new DownloadAgent();
    File actual=new File("otherfile.jpg");
    assertThat(downloadAgent.downloadFile("File:/home/clouway/Downloads/online-url-bibanews.jpg","somefilefortest").getTotalSpace(),is(actual.getTotalSpace()));
  }
}
