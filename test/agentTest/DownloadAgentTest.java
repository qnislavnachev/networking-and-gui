package agentTest;

import org.junit.Before;
import org.junit.Test;
import task1.DownloadAgent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DownloadAgentTest {

    @Test
    public void downloadedFileSize() throws Exception {
        DownloadAgent agent = new DownloadAgent();
        File file = new File("pic1.jpg");
        String link = file.toURI().toString();
        File downloaded = agent.downloadFile(link, "file", ".jpg");
        long expected = file.length();
        long actual = downloaded.length();
        assertThat(actual, is(expected));
        downloaded.delete();
    }
}