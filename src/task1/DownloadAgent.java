package task1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class DownloadAgent {

    public File downloadFile(String link, String fileName, String extension) {
        File file = null;
        try {
            final byte[] data = new byte[1024];
            file = new File(fileName + extension);
            BufferedInputStream reader = new BufferedInputStream(new URL(link).openStream());
            FileOutputStream writer = new FileOutputStream(file);
            int count;
            while ((count = reader.read(data, 0, 1024)) != -1) {
                writer.write(data, 0, count);
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
