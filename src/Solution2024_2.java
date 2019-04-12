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
import java.util.Comparator;

public class Solution2024_2 {

  private static String getTestString() {
    // input sample
    // 1
    // -1 0
    // 0 1
    // 0 0
    // answer
    // 1
    // return "1\n" +
    //    "-1 0\n" +
    //    "0 1\n" +
    // return "20\n" +
    // "-1 0\n" +
    // "2 10\n" +
    // "2 5\n" +
    // "1 9\n" +
    // "3 7\n" +
    // "7 17\n" +
    // "5 12\n" +
    // "14 19\n" +
    // "20 20\n" +
    return "4\n" +
        "-2 -1\n" +
        "-1 0\n" +
        "1 1\n" +
        "2 2\n" +
        "2 3\n" +
        "3 3\n" +
        "3 3\n" +
        "4 4\n" +
        "0 0\n";

  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    int nextM = EasyReader.readInt(context);
    while (true) {
      long st = System.nanoTime();

      int M = nextM;
      if (M == 0) {
        break;
      }

      Line[] LR = new Line[100000];

      int offset = 0;
      boolean endTest = false;
      while (true) {
        EasyReader.ensureRead(context);
        context.offSet = context.separatedLine.length;
        if (context.separatedLine.length < 2) {
          nextM = Integer.parseInt(context.separatedLine[0]);
          break;
        }

        int l = Integer.parseInt(context.separatedLine[0]);
        int r = Integer.parseInt(context.separatedLine[1]);
        if (l == 0 && r == 0) {
          endTest = true;
          break;
        }
        if (l < r) {
          if (r < 0 || l > M) {
            continue;
          }
          Line line = new Line();
          line.l = Math.max(l, 0);
          line.r = Math.min(r, M);
          LR[offset] = line;
        } else {
          if (l < 0 || r > M) {
            continue;
          }
          Line line = new Line();
          line.l = Math.max(r, 0);
          line.r = Math.min(l, M);
          LR[offset] = line;
        }
        offset++;
      }
      Arrays.sort(LR, new Comparator<Line>() {
        @Override
        public int compare(Line o1, Line o2) {
          if (o1 == null && o2 == null) {
            return 0;
          }
          if (o1 != null && o2 == null) {
            return -1;
          }
          if (o1 == null && o2 != null) {
            return 1;
          }
          if (o1.r < o2.r) {
            return 1;
          }
          if (o1.r > o2.r) {
            return -1;
          }
          return 0;
        }
      });

      int count = 0;
      int starter = 0;
      while (starter <= M) {
        int checker = starter;
        for (int i = 0; i < offset; ++i) {
          if (LR[i].l <= starter) {
            starter = LR[i].r + 1;
            count++;
            break;
          }
        }
        if (checker == starter) {
          count = 0;
          break;
        }
      }

      System.out.println(count);

      long dur = System.nanoTime() - st;
      System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");

      if (endTest) {
        break;
      }
    }
  }

  static class Line {
    int l;
    int r;

    @Override
    public String toString() {
      return "[" + l + "-" + r + "]";
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