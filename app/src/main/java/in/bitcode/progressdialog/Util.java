package in.bitcode.progressdialog;

public class Util {

    public static String download(String url) {
        for(int i = 0; i <= 100; i++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        return "/storage/downloads/file1";
    }
}
