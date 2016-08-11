package com.clouway;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class DownloadAgentTest {
  JUnitRuleMockery context = new JUnitRuleMockery();
  ProgressBar progressBar = context.mock(ProgressBar.class);

  @Test
  public void downloadingFile() throws Exception {
    DownloadAgent downloadAgent = new DownloadAgent(progressBar);
    context.checking(new Expectations() {{
      allowing(progressBar).downloadProgress(10);
      allowing(progressBar).downloadProgress(20);
      allowing(progressBar).downloadProgress(30);
      allowing(progressBar).downloadProgress(40);
      allowing(progressBar).downloadProgress(50);
      allowing(progressBar).downloadProgress(60);
      allowing(progressBar).downloadProgress(70);
      allowing(progressBar).downloadProgress(80);
      allowing(progressBar).downloadProgress(90);
      allowing(progressBar).downloadProgress(10);
    }});
    File actual = new File("otherfile.jpg");
    assertThat(downloadAgent.downloadFile("File:/home/clouway/Downloads/online-url-bibanews.jpg", "somefilefortest").getTotalSpace(), is(actual.getTotalSpace()));
  }
}
