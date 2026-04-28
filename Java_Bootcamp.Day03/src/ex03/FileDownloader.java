import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.URL;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.file.StandardCopyOption;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FileDownloader {
    private final ExecutorService executor;

    public FileDownloader(int threadsNumber) {
        this.executor = Executors.newFixedThreadPool(threadsNumber);
    }

    public void downloadFiles(LinkedList<URL> urls) {
        for (URL url : urls) {
            executor.execute(new DownloadTask(url));
        }
        executor.shutdown();
    }

    private static class DownloadTask implements Runnable {
        private final URL url;
        public DownloadTask(URL url) {
            this.url = url;
        }
        @Override
        public void run() {
            String fileName = Paths.get(url.getPath()).getFileName().toString();
//            System.out.println(url.toString().substring(url.toString().lastIndexOf('/')+1));
            System.out.println("Start " + Thread.currentThread().getName() + " downloading " + fileName);
            int contentLength = 0;
            try {
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                httpConn.setRequestMethod("GET");
                // Получаем общий размер файла
                contentLength = httpConn.getContentLength();
//                System.out.println(fileName + " - " + contentLength/1024 + " Kb");
            } catch (IOException e) {
                e.printStackTrace();
            }
            File downloadDirectory = new File("./Downloads/");
            if (!downloadDirectory.exists()) {
                downloadDirectory.mkdir();
            }
            Path outputPath = Path.of("./Downloads/" + fileName);

            try (InputStream in = url.openStream()) {
                Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("End " + Thread.currentThread().getName() + " " + fileName + " - " + contentLength/1024 + " Kb");
        }
    }
}
