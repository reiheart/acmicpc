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

public class Solution2157 {

  private static String getTestString() {
    // input sample
    // 3 3 5
    // 1 3 10
    // 1 2 5
    // 2 3 3
    // 1 3 4
    // 3 1 100
    // answer
    // 10
    return "3 3 5\n" +
    "1 3 10\n" +
    "1 2 5\n" +
    "2 3 3\n" +
    "1 3 4\n" +
    "3 1 100";

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

    int[][] lines = new int[N + 1][N + 1];
    for (int n = 0; n < lines.length; ++n) {
      Arrays.fill(lines[n], -1);
    }
    for (int k = 1; k <= K; ++k) {
      final int a = EasyReader.readInt(context);
      final int b = EasyReader.readInt(context);
      final int c = EasyReader.readInt(context);
      if (a >= b) {
        continue;
      }

      if(lines[a][b] < c) {
        lines[a][b] = c;
      }
    }

    long[][] maxHolder = new long[N + 1][M + 1];
    for (int l = 2; l < lines[1].length; ++l) {
      if (lines[1][l] == -1) {
        continue;
      }
      maxHolder[l][2] = maxHolder[1][1] + lines[1][l];
    }
    for (int n = 2; n < N; ++n) {
      for (int l = n + 1; l < lines[n].length; ++l) {
        if (lines[n][l] == -1) {
          continue;
        }
        for (int m = 2; m < maxHolder[l].length - 1; ++m) {
          if (maxHolder[n][m] == 0) {
            continue;
          }
          long newMax = maxHolder[n][m] + lines[n][l];
          if (maxHolder[l][m + 1] < newMax) {
            maxHolder[l][m + 1] = newMax;
          }
        }
      }
    }

    long max = 0;
    for (int m = 1; m <= M; ++m) {
      if (maxHolder[N][m] > max) {
        max = maxHolder[N][m];
      }
    }
    System.out.println(max);

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