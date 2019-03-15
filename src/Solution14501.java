import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Solution14501 {

  private static String getTestString() {
    // input sample
    // 7
    // 3 10
    // 5 20
    // 1 10
    // 1 20
    // 2 15
    // 4 40
    // 2 200
    // answer
    // 45
    return "7\n" +
    "3 10\n" +
        "5 20\n" +
    "1 10\n" +
    "1 20\n" +
    "2 15\n" +
    "4 40\n" +
    "2 200";

    // input sample
    // 10
    // 1 1
    // 1 2
    // 1 3
    // 1 4
    // 1 5
    // 1 6
    // 1 7
    // 1 8
    // 1 9
    // 1 10
    // answer
    // 55
    // return "10\n" +
    // "1 1\n" +
    // "1 2\n" +
    // "1 3\n" +
    // "1 4\n" +
    // "1 5\n" +
    // "1 6\n" +
    // "1 7\n" +
    // "1 8\n" +
    // "1 9\n" +
    // "1 10";

    // input sample
    // 10
    // 5 10
    // 5 9
    // 5 8
    // 5 7
    // 5 6
    // 5 10
    // 5 9
    // 5 8
    // 5 7
    // 5 6
    // answer
    // 20
    // return "10\n" +
    // "5 10\n" +
    // "5 9\n" +
    // "5 8\n" +
    // "5 7\n" +
    // "5 6\n" +
    // "5 10\n" +
    // "5 9\n" +
    // "5 8\n" +
    // "5 7\n" +
    // "5 6";

    // input sample
    // 10
    // 5 50
    // 4 40
    // 3 30
    // 2 20
    // 1 10
    // 1 10
    // 2 20
    // 3 30
    // 4 40
    // 5 50
    // answer
    // 90
    // return "10\n" +
    // "5 50\n" +
    // "4 40\n" +
    // "3 30\n" +
    // "2 20\n" +
    // "1 10\n" +
    // "1 10\n" +
    // "2 20\n" +
    // "3 30\n" +
    // "4 40\n" +
    // "5 50";
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final int N = EasyReader.readInt(context);

    int[] maxs = new int[N + 5];
    int max = 0;
    for (int n = 0; n < N; ++n) {
      int t = EasyReader.readInt(context);
      int p = EasyReader.readInt(context);

      maxs[n + t - 1] = Math.max(maxs[n + t - 1], max + p);

      max = Math.max(max, maxs[n]);
    }

    System.out.println(max);
    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  private static int popFromSort(int[] sortedArray, int offset, int[] t, int[] p) {
    int pop = sortedArray[1];

    int check = 1;
    sortedArray[check] = sortedArray[--offset];
    while (check * 2 < offset) {
      int maxChild = check * 2;
      if (maxChild + 1 < offset) {
        if ((p[maxChild] * 1.0D) / (t[maxChild] * 1.0D) < (p[maxChild + 1] * 1.0D) / (t[maxChild + 1] * 1.0D)) {
          maxChild += 1;
        }
      }

      if ((p[maxChild] * 1.0D) / (t[maxChild] * 1.0D) < (p[check] * 1.0D) / (t[check] * 1.0D)) {
        break;
      }

      check = maxChild;
    }

    return pop;
  }

  private static int[] sortMult(int[] t, int[] p) {
    int[] sortArr = new int[t.length + 1];
    for (int i = 0; i < t.length; ++i) {
      int offset = i + 1;
      sortArr[offset] = i;
      while (offset / 2 > 1) {
        double parentCalc = (p[sortArr[offset / 2]] * 1.0D) / (t[sortArr[offset / 2]] * 1.0D);
        double myCalc = (p[sortArr[offset]] * 1.0D) / (t[sortArr[offset]] * 1.0D);
        if (parentCalc < myCalc) {
          int temp = sortArr[offset / 2];
          sortArr[offset / 2] = sortArr[offset];
          sortArr[offset] = temp;
          offset /= 2;
        } else {
          break;
        }
      }
    }
    return sortArr;
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