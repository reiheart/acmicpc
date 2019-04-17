import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Solution1806 {

  private static String getTestString() {
    // input sample
    // 10 15
    // 5 1 3 5 10 7 4 9 2 8
    // answer
    // 2
    // return "10 15\n" +
    // "5 1 3 5 10 7 4 9 2 8";
    return "5 11\n" + // 3
    "1 2 3 4 5";
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final int N = EasyReader.readInt(context);
    final long S = EasyReader.readLong(context);
    long[] acc = new long[N + 1];
    for (int n = 1; n <= N; ++n) {
      long in = EasyReader.readLong(context);
      acc[n] = acc[n - 1] + in;
    }
    int i = 0;
    int j = 0;
    int min = Integer.MAX_VALUE;
    while (i <= N && j <= N) {
      if (acc[j] - acc[i] < S) {
        j++;
        if (j > N) {
          j = N;
          i++;
        }
      } else {
        int l = j - i;
        if (l == 1) {
          min = 1;
          break;
        }
        if (min > l) {
          min = l;
        }
        i++;
      }
    }

    if (min == Integer.MAX_VALUE) {
      System.out.println(0);
    } else {
      System.out.println(min);
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

    public static String read(ReadingContext context) throws IOException {
      ensureRead(context);
      String read = context.separatedLine[context.offSet++];
      while ("".equals(read)) {
        ensureRead(context);
        read = context.separatedLine[context.offSet++];
      }

      return read;
    }

    public static int readInt(ReadingContext context) throws IOException {
      return Integer.parseInt(read(context));
    }

    public static long readLong(ReadingContext context) throws IOException {
      return Long.parseLong(read(context));
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