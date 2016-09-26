package agentTest;

import org.junit.Before;
import org.junit.Test;
import task1.DownloadAgent;

import java.io.File;
import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class DownloadAgentTest {

    @Test
    public void downloadedFileSize() throws Exception {
        DownloadAgent agent = new DownloadAgent();
        URL url = new URL("https://i.ytimg.com/vi/9Nwn-TZfFUI/maxresdefault.jpg");
        File file = agent.downloadFile("https://i.ytimg.com/vi/9Nwn-TZfFUI/maxresdefault.jpg", "file", ".jpg");
        long expected = url.openConnection().getContentLength();
        long actual = file.length();
        assertThat(actual, is(expected));
        file.delete();
    }
}