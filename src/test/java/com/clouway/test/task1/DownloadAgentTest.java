package com.clouway.test.task1;

import com.clouway.task1.DownloadAgent;
import com.clouway.task1.ProgressBar;
import org.apache.commons.io.IOUtils;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class DownloadAgentTest {
    @Rule public JUnitRuleMockery context = new JUnitRuleMockery();
    ProgressBar progressBar = context.mock(ProgressBar.class);
    DownloadAgent download = new DownloadAgent(progressBar);

    @Test
    public void downloadJPG() throws Exception {
        context.checking(new Expectations(){{
            atLeast(6).of(progressBar).getProgress();
            will(onConsecutiveCalls(
                    returnValue(19),
                    returnValue(37),
                    returnValue(56),
                    returnValue(74),
                    returnValue(93),
                    returnValue(100)
            ));
        }});

        download.downloadFile("file:/home/clouway/workspaces/idea/networking-and-gui/src/test/java/com/clouway/test/task1/1.jpg","/home/clouway/workspaces/idea/networking-and-gui/src/test/java/com/clouway/test/task1/copy1");

        InputStream in = new BufferedInputStream(new FileInputStream("/home/clouway/workspaces/idea/networking-and-gui/src/test/java/com/clouway/test/task1/copy1.jpg"));
        byte[] imageOne = IOUtils.toByteArray(in);
        in = new BufferedInputStream(new FileInputStream("/home/clouway/workspaces/idea/networking-and-gui/src/test/java/com/clouway/test/task1/1.jpg"));
        byte[] imageTwo = IOUtils.toByteArray(in);
        in.close();

        assertArrayEquals(imageOne, imageTwo);
    }
}
