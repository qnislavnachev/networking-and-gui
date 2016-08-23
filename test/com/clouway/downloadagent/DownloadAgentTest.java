package com.clouway.downloadagent;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Vasil Mitov <v.mitov.clouway@gmail.com>
 */
public class DownloadAgentTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private ProgressBar progressBar = context.mock(ProgressBar.class);

  @Test
  public void happyPath() throws Exception {
    DownloadAgent downloadAgent = new DownloadAgent(progressBar, 5);
    context.checking(new Expectations() {{
      allowing(progressBar).update(0);
      allowing(progressBar).update(10);
      allowing(progressBar).update(20);
      allowing(progressBar).update(30);
      allowing(progressBar).update(40);
      allowing(progressBar).update(50);
      allowing(progressBar).update(60);
      allowing(progressBar).update(70);
      allowing(progressBar).update(80);
      allowing(progressBar).update(90);
      allowing(progressBar).update(100);

//      oneOf(progressBar).update(25);
//      oneOf(progressBar).update(50);
//      oneOf(progressBar).update(75);
//      oneOf(progressBar).update(100);

    }});

    File actual = new File("file.jpg");
    downloadAgent.downloadFile("File:test/com/clouway/downloadagent/file.jpg", "test/com/clouway/downloadagent/otherfile.jpg");
    File expected = new File("otherfile.jpg");
    assertThat(expected.getTotalSpace(), is(actual.getTotalSpace()));
  }
}
