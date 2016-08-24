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
      oneOf(progressBar).update(25);
      oneOf(progressBar).update(50);
      oneOf(progressBar).update(75);
      oneOf(progressBar).update(100);
    }});
    File actual = new File("test.txt");
    downloadAgent.downloadFile("File:test/com/clouway/downloadagent/test.txt", "test/com/clouway/downloadagent/testfiledownloaded.txt");
    File expected = new File("testfiledownloaded.txt");
    assertThat(expected.getTotalSpace(), is(actual.getTotalSpace()));
  }
}
