package task1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadAgent {

    public void downloadFile(BufferedInputStream reader, FileOutputStream writer) throws IOException {
        final byte[] data = new byte[1024];
        int count;
        while ((count = reader.read(data, 0, 1024)) != -1) {
            writer.write(data, 0, count);
        }
        reader.close();
        writer.close();
    }
}