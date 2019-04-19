import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Solution1690 {

  private static String getTestString() {
    // input sample
    // 4
    // 3
    // 4
    // 1
    // 2
    // answer
    // 4
    // 2
    // 3
    // 4
    // 1
    return "4\n" +
    "3\n" +
    "4\n" +
    "1\n" +
    "2";

  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final int N = EasyReader.readInt(context);
    int[] B = new int[N + 1];
    for (int n = 1; n <= N; ++n) {
      B[n] = EasyReader.readInt(context);
    }

    boolean check = false;
    boolean[] dynamicChecker = new boolean[N + 1];
    int[] A = new int[N + 1];
    Arrays.fill(A, -1);
    Deque<Integer> level = new ArrayDeque<Integer>();
    Deque<Integer> number = new ArrayDeque<Integer>();

    for (int n = 1; n <= N; ++n) {
      level.push(1);
      number.push(n);
    }
    int levelChecker = 1;

    while (!level.isEmpty()) {
      int popLevel = level.pop();
      int popNumber = number.pop();
      if (dynamicChecker[popNumber]) {
        continue;
      }

      if (levelChecker > popLevel) {
        for (int i = levelChecker; i >= popLevel; --i) {
          dynamicChecker[A[i]] = false;
          A[i] = -1;
        }
      }
      levelChecker = popLevel;
      if (levelChecker > N) {
        break;
      }

      dynamicChecker[popNumber] = true;
      A[popLevel] = popNumber;

      if (A[A[popLevel]] != -1) {
        if (B[A[A[popLevel]]] != popLevel) {
          dynamicChecker[popNumber] = false;
          continue;
        }
        if (popLevel < N) {
          for (int n = 1; n < N; ++n) {
            level.push(popLevel + 1);
            number.push(n);
          }
        } else {
          boolean lastCheck = true;
          for (int n = 1; n <= N; ++n) {
            if (B[A[A[popLevel]]] != popLevel) {
              dynamicChecker[popNumber] = false;
              lastCheck = false;
              break;
            }
          }
          if (lastCheck) {
            check = true;
            break;
          }
        }
      } else {
        for (int n = 1; n <= N; ++n) {
          level.push(popLevel + 1);
          number.push(n);
        }
      }
    }

    if (check) {
      System.out.println(N);
      for (int n = 1; n <= N; ++n) {
        System.out.println(A[n]);
      }
    } else {
      System.out.println(0);
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