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

public class Solution10868 {

  private static String getTestString() {
    // input sample
    // 10 4
    // 75
    // 30
    // 100
    // 38
    // 50
    // 51
    // 52
    // 20
    // 81
    // 5
    // 1 10
    // 3 5
    // 6 9
    // 8 10
    // answer
    // 5
    // 38
    // 20
    // 5
    return "10 4\n" +
    "75\n" +
    "30\n" +
    "100\n" +
    "38\n" +
    "50\n" +
    "51\n" +
    "52\n" +
    "20\n" +
    "81\n" +
    "5\n" +
    "1 10\n" +
    "3 5\n" +
    "6 9\n" +
    "8 10";
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    int N = EasyReader.readInt(context);
    int M = EasyReader.readInt(context);

    int length = getSegLength(N);
    int[] minSeg = new int[length];
    Arrays.fill(minSeg, Integer.MAX_VALUE);
    for (int n = 1; n <= N; ++n) {
      final int x = EasyReader.readInt(context);
      addToMSeg(minSeg, 1, 1, N, x, n);
    }

    StringBuffer sb = new StringBuffer();
    for (int m = 0; m < M; ++m) {
      int a = EasyReader.readInt(context);
      int b = EasyReader.readInt(context);

      sb.append(queryM(minSeg, 1, 1, N, a, b)).append("\n");
    }
    System.out.println(sb.toString());

    long dur = System.nanoTime() - st;System.out.println(dur+" nsec"+" / "+(dur/1000000)+" msec");
  }

  private static int addToMSeg(int[] X, int index, int start, int end, int x, int i) {
    if (i < start || end < i) {
      return X[index];
    }
    if (start == end) {
      X[index] = x;
      return X[index];
    }

    int mid = (start + end) / 2;
    int left = addToMSeg(X, index * 2, start, mid, x, i);
    int right = addToMSeg(X, (index * 2) + 1, mid + 1, end, x, i);

    X[index] = Math.min(left, right);

    return X[index];
  }

  private static int queryM(int[] X, int index, int start, int end, int i, int j) {
    if (j < start || end < i) {
      return Integer.MAX_VALUE;
    }
    if (i <= start && end <= j) {
      return X[index];
    }

    int mid = (start + end) / 2;
    int left = queryM(X, index * 2, start, mid, i, j);
    int right = queryM(X, (index * 2) + 1, mid + 1, end, i, j);

    return Math.min(left, right);
  }

  private static int getSegLength(int n) {
    int length = 1;
    while (length < n) {
      length *= 2;
    }

    return length * 2;
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

    public static String readString(ReadingContext context) throws IOException {
      ensureRead(context);

      return context.separatedLine[context.offSet++];
    }

    public static int readInt(ReadingContext context) throws IOException {
      ensureRead(context);

      return Integer.parseInt(context.separatedLine[context.offSet++]);
    }

    public static double readDouble(ReadingContext context) throws IOException {
      ensureRead(context);

      return Double.parseDouble(context.separatedLine[context.offSet++]);
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
