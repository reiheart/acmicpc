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
import java.util.Deque;

public class Solution1012 {

  private static String getTestString() {
    // input sample
    // 2
    // 10 8 17
    // 0 0
    // 1 0
    // 1 1
    // 4 2
    // 4 3
    // 4 5
    // 2 4
    // 3 4
    // 7 4
    // 8 4
    // 9 4
    // 7 5
    // 8 5
    // 9 5
    // 7 6
    // 8 6
    // 9 6
    // 10 10 1
    // 5 5
    // answer
    // 5
    // 1
    return "2\n" +
    "10 8 17\n" +
    "0 0\n" +
    "1 0\n" +
    "1 1\n" +
    "4 2\n" +
    "4 3\n" +
    "4 5\n" +
    "2 4\n" +
    "3 4\n" +
    "7 4\n" +
    "8 4\n" +
    "9 4\n" +
    "7 5\n" +
    "8 5\n" +
    "9 5\n" +
    "7 6\n" +
    "8 6\n" +
    "9 6\n" +
    "10 10 1\n" +
    "5 5";

  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final int T = EasyReader.readInt(context);
    for (int t = 0; t < T; ++t) {
      final int M = EasyReader.readInt(context);
      final int N = EasyReader.readInt(context);
      final int K = EasyReader.readInt(context);
      int[][] field = new int[M][N];
      for (int k = 0; k < K; ++k) {
        field[EasyReader.readInt(context)][EasyReader.readInt(context)] = 1;
      }
      int count = 1;
      for (int m = 0; m < M; ++m) {
        for (int n = 0; n < N; ++n) {
          if (field[m][n] != 1) {
            continue;
          }
          bfs(field, m, n, -count);
          count++;
        }
      }
      System.out.println(count - 1);
    }


    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  private static void bfs(int[][] field, int m, int n, int number) {
    Deque<Integer> mQueue = new ArrayDeque<Integer>();
    Deque<Integer> nQueue = new ArrayDeque<Integer>();
    mQueue.add(m);
    nQueue.add(n);

    while (!mQueue.isEmpty()) {
      int pollM = mQueue.poll();
      int pollN = nQueue.poll();
      if (field[pollM][pollN] != 1) {
        continue;
      }
      field[pollM][pollN] = number;
      if (pollM > 0 && field[pollM - 1][pollN] == 1) {
        mQueue.add(pollM - 1);
        nQueue.add(pollN);
      }
      if (pollN > 0 && field[pollM][pollN - 1] == 1) {
        mQueue.add(pollM);
        nQueue.add(pollN - 1);
      }
      if (pollM < field.length - 1 && field[pollM + 1][pollN] == 1) {
        mQueue.add(pollM + 1);
        nQueue.add(pollN);
      }
      if (pollN < field[pollM].length - 1 && field[pollM][pollN + 1] == 1) {
        mQueue.add(pollM);
        nQueue.add(pollN + 1);
      }
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