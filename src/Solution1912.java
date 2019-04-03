import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Solution1912 {

  private static String getTestString() {
    // input sample
    // 10
    // 10 -4 3 1 5 6 -35 12 21 -1
    // answer
    // 33
    return "10\n" +
        "10 -4 3 1 5 6 -35 12 21 -1";
    // return "4\n"
    // + "5 -1 -2 5";
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final int N = EasyReader.readInt(context);
    int[] number = new int[N];
    long[] acc = new long[N];
    long maxsum = Long.MIN_VALUE;
    for (int n = 0; n < N; ++n) {
      number[n] = EasyReader.readInt(context);
      if (n==0) {
        acc[n] = number[n];
      } else {
        acc[n] = acc[n - 1] + number[n];
        if (acc[n] < number[n]) {
          acc[n] = number[n];
        }
      }
      if (maxsum < acc[n]) {
        maxsum = acc[n];
      }
    }
    System.out.println(maxsum);

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