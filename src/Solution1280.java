import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;

public class Solution1280 {

  private static String getTestString() {
    // input sample
    // 5
    // 3
    // 4
    // 5
    // 6
    // 7
    // answer
    // 180
    return "5\n" +
    "3\n" +
    "4\n" +
    "5\n" +
    "6\n" +
    "7";

  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final int N = EasyReader.readInt(context);
    int[] P = new int[N];
    Arrays.fill(P, Integer.MAX_VALUE);

    int mul = 1;
    P[0] = EasyReader.readInt(context);
    for (int n = 1; n < N; ++n) {
      P[n] = EasyReader.readInt(context);

      int nCount = 0;
      int lSum = 0;
      int bSum = 0;
      for (int m = 0; m < n; ++m) {
        if (P[n] > P[m]) {
          nCount++;
          lSum += P[m];
          continue;
        }
        if (P[n] < P[m]) {
          nCount--;
          bSum += P[m];
        }
      }
      System.out.println(lSum + "/" + bSum + "-" + mul);
      mul *= (lSum - bSum) - (nCount * P[n]);
      while (mul > 1000000007) {
        mul -= 1000000007;
      }
    }

    System.out.println(mul);

    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  static class Tree {

    public int add(int i) {
      // TODO Auto-generated method stub
      return 0;
    }

  }

  private static int getDistance(int pi, int pj) {
    int d = pi - pj;
    if (d < 0) {
      return -d;
    } else {
      return d;
    }
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