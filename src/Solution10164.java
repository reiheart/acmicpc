import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Solution10164 {

  private static String getTestString() {
    // input sample
    // 3 5 8
    // answer
    // 9
    return "3 5 8\n";
  }

  private static final int[][] magic = new int[15][15];
  static {
    for (int j = 1; j < magic[0].length; j++) {
      magic[0][j] = 1;
    }
    for (int i = 1; i < magic.length; i++) {
      magic[i][0] = 1;
      for (int j = 1; j < magic[i].length; j++) {
        magic[i][j] = magic[i][j - 1] + magic[i - 1][j];
      }
    }
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final int N = EasyReader.readInt(context);
    final int M = EasyReader.readInt(context);
    final int K = EasyReader.readInt(context);

    if (K == 0) {
      System.out.println(magic[N - 1][M - 1]);
    } else {
      int kPathN = K / M;
      int kPathM = (K % M) - 1;
      if (kPathM == -1) {
        kPathN--;
        kPathM = M - 1;
      }

      int endPathN = N - kPathN - 1;
      int endPathM = M - kPathM - 1;

      System.out.println(magic[kPathN][kPathM] * magic[endPathN][endPathM]);
    }

    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  public static class EasyReader {
    public static class ReadingContext {
      private final String separator;
      private final BufferedReader reader;
      private String[] separatedLine;
      private String line;
      private int offSet;

      public ReadingContext(Path path) throws FileNotFoundException {
        this(new FileInputStream(path.toFile()));
      }

      public ReadingContext(InputStream is) {
        this(is, "[\\s\\t]+");
      }

      public ReadingContext(InputStream is, String separator) {
        this.separator = separator;
        reader = new BufferedReader(new InputStreamReader(is));
        offSet = 0;
      }

      @Override
      protected void finalize() throws Throwable {
        reader.close();
        super.finalize();
      }
    }

    public static int readInt(ReadingContext context) throws IOException {
      ensureRead(context);

      return Integer.parseInt(context.separatedLine[context.offSet++]);
    }

    public static long readLong(ReadingContext context) throws IOException {
      ensureRead(context);

      return Long.parseLong(context.separatedLine[context.offSet++]);
    }

    private static void ensureRead(ReadingContext context) throws IOException {
      if (context.line == null || context.separatedLine == null || context.offSet >= context.separatedLine.length) {
        readNewLine(context);
        ensureRead(context);
      }
    }

    private static void readNewLine(ReadingContext context) throws IOException {
      context.line = context.reader.readLine();
      context.separatedLine = context.line.split(context.separator);
      context.offSet = 0;
    }

  }
}