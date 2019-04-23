import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Solution5676 {

  private static String getTestString() {
    // input sample
    // 4 6
    // -2 6 0 -1
    // C 1 10
    // P 1 4
    // C 3 7
    // P 2 2
    // C 4 -5
    // P 1 4
    // 5 9
    // 1 5 -2 4 3
    // P 1 2
    // P 1 5
    // C 4 -5
    // P 1 5
    // P 4 5
    // C 3 0
    // P 1 5
    // C 4 -5
    // C 4 -5
    // answer
    // 0+-
    // +-+-0
    return "4 6\n" +
    "-2 6 0 -1\n" +
    "C 1 10\n" +
    "P 1 4\n" +
    "C 3 7\n" +
    "P 2 2\n" +
    "C 4 -5\n" +
    "P 1 4\n" +
    "5 9\n" +
    "1 5 -2 4 3\n" +
    "P 1 2\n" +
    "P 1 5\n" +
    "C 4 -5\n" +
    "P 1 5\n" +
    "P 4 5\n" +
    "C 3 0\n" +
    "P 1 5\n" +
    "C 4 -5\n" +
    "C 4 -5";
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    // URL resource = Solution5676.class.getClassLoader().getResource("Sample5676.in");
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(Paths.get(resource.toURI()));

    long st = System.nanoTime();

    while (true) {
      int N = 0;
      int K = 0;
      try {
        N = EasyReader.readInt(context);
        K = EasyReader.readInt(context);
      } catch (NullPointerException e) {
        return;
      }

      int length = getSegLength(N);
      int[] Xm = new int[length];
      boolean[] Xz = new boolean[length];
      for (int n = 1; n <= N; ++n) {
        final int x = EasyReader.readInt(context);
        addToMSeg(Xm, 1, 1, N, x, n);
        addToZSeg(Xz, 1, 1, N, x == 0, n);
      }

      StringBuffer sb = new StringBuffer();
      for (int k = 0; k < K; ++k) {
        String command = EasyReader.readString(context);
        int i = EasyReader.readInt(context);
        int j = EasyReader.readInt(context);

        if ("C".equals(command)) {
          updateMSeg(Xm, 1, 1, N, j, i);
          updateZSeg(Xz, 1, 1, N, j == 0, i);
        } else if ("P".equals(command)) {
          boolean checkZ = queryZ(Xz, 1, 1, N, i, j);
          if (checkZ) {
            sb.append("0");
            continue;
          }
          int countM = queryM(Xm, 1, 1, N, i, j);
          if (countM % 2 != 0) {
            sb.append("-");
            continue;
          }
          sb.append("+");
        }
      }
      System.out.println(sb);

      long dur = System.nanoTime() - st;
      System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
    }

  }

  private static int addToMSeg(int[] X, int index, int start, int end, int x, int i) {
    if (i < start || end < i) {
      return X[index];
    }
    if (start == end) {
      if (x < 0) {
        X[index] = 1;
      }
      return X[index];
    }

    int mid = (start + end) / 2;
    int left = addToMSeg(X, index * 2, start, mid, x, i);
    int right = addToMSeg(X, (index * 2) + 1, mid + 1, end, x, i);

    X[index] = left + right;

    return X[index];
  }

  private static int updateMSeg(int[] X, int index, int start, int end, int x, int i) {
    if (i < start || end < i) {
      return 0;
    }
    if (start == end) {
      if (X[index] == 0) {
        if (x < 0) {
          X[index] = 1;
          return 1;
        }
        return 0;
      } else {
        if (x < 0) {
          return 0;
        }
        X[index] = 0;
        return -1;
      }
    }

    int mid = (start + end) / 2;
    int left = updateMSeg(X, index * 2, start, mid, x, i);
    int right = updateMSeg(X, (index * 2) + 1, mid + 1, end, x, i);

    int update = left + right;
    X[index] += update;

    return update;
  }

  private static int queryM(int[] X, int index, int start, int end, int i, int j) {
    if (j < start || end < i) {
      return 0;
    }
    if (i <= start && end <= j) {
      return X[index];
    }

    int mid = (start + end) / 2;
    int left = queryM(X, index * 2, start, mid, i, j);
    int right = queryM(X, (index * 2) + 1, mid + 1, end, i, j);

    return left + right;
  }

  private static boolean addToZSeg(boolean[] X, int index, int start, int end, boolean x, int i) {
    if (i < start || end < i) {
      return X[index];
    }
    if (start == end) {
      X[index] = x;
      return X[index];
    }

    int mid = (start + end) / 2;
    boolean left = addToZSeg(X, index * 2, start, mid, x, i);
    boolean right = addToZSeg(X, (index * 2) + 1, mid + 1, end, x, i);

    X[index] = left || right;
    return X[index];
  }

  private static void updateZSeg(boolean[] X, int index, int start, int end, boolean x, int i) {
    addToZSeg(X, index, start, end, x, i);
  }

  private static boolean queryZ(boolean[] X, int index, int start, int end, int i, int j) {
    if (j < start || end < i) {
      return false;
    }
    if (i <= start && end <= j) {
      return X[index];
    }

    int mid = (start + end) / 2;
    boolean left = queryZ(X, index * 2, start, mid, i, j);
    boolean right = queryZ(X, (index * 2) + 1, mid + 1, end, i, j);

    return left || right;
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
