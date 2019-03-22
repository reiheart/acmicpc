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
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Solution2162 {

  private static String getTestString() {
    // input sample
    // 3
    // 1 1 2 3
    // 2 1 0 0
    // 1 0 1 1
    // answer
    // 1
    // 3
    // return "3\n" +
    // "1 1 2 3\n" +
    // "2 1 0 0\n" +
    // "1 0 1 1";
    return "8\n" +
        "1 1 3 3\n" +
        "2 1 2 3\n" +
        "3 1 1 3\n" +
        "3 2 1 2\n" +
        "4 1 4 3\n" +
        "1 4 3 4\n" +
        "1 5 3 5\n" +
        "2 4 2 6";
    // answer
    // 3
    // 4
    // return "9\n" +
    // "1 11 80 93\n" +
    // "46 19 7 53\n" +
    // "72 83 99 51\n" +
    // "84 97 42 98\n" +
    // "73 63 94 22\n" +
    // "44 73 79 40\n" +
    // "58 86 13 97\n" +
    // "34 53 30 16\n" +
    // "34 72 3 81";
    // answer
    // 6
    // 4
    // return "8\n" +
    // "0 0 10 10\n" +
    // "10 0 10 10\n" +
    // "0 10 10 10\n" +
    // "20 0 0 20\n" +
    // "-5 -5 100 100\n" +
    // "-300 -300 300 300\n" +
    // "1000 1000 0 0\n" +
    // "0 0 1000 1000";
    // answer
    // 1
    // 8
    // return "6\n" +
    // "1 0 3 0\n" +
    // "1 1 3 1\n" +
    // "3 1 2 3\n" +
    // "2 3 1 1\n" +
    // "0 2 2 4\n" +
    // "2 0 0 3";
    // answer
    // 1
    // 6
    // return "5\n" +
    // "3 0 0 3\n" +
    // "4 0 0 4\n" +
    // "3 0 4 0\n" +
    // "0 0 -1 -1\n" +
    // "-1 -1 -1 7";
    // answer
    // 2
    // 3
    // return "6\n" +
    // "2 5 0 0\n" +
    // "1 7 0 0\n" +
    // "-1 -1 0 0\n" +
    // "0 0 -1 3\n" +
    // "7 -9 0 0\n" +
    // "-3000 -3000 3000 3000";
    // answer
    // 1
    // 6
    // return "12\n" +
    // "1 1 -1 1\n" +
    // "-1 1 -1 -1\n" +
    // "-1 -1 1 -1\n" +
    // "1 -1 1 1\n" +
    // "0 2 2 0\n" +
    // "2 0 0 2\n" +
    // "-2 0 0 -2\n" +
    // "0 2 -2 0\n" +
    // "0 1 1 0\n" +
    // "-1 0 0 -1\n" +
    // "-1 0 0 1\n" +
    // "1 0 0 -1";
    // answer
    // 1
    // 12
    // return "2\n" +
    // "1 1 4 4\n" +
    // "2 2 3 3";
    // answer
    // 1
    // 2
    // return "5\n" +
    // "0 2 2 0\n" +
    // "0 2 -2 0\n" +
    // "0 -2 -2 0\n" +
    // "0 -2 2 0\n" +
    // "0 0 0 1";
    // answer
    // 2
    // 4
    // return "2\n" +
    // "1 1 2 2\n" +
    // "4 4 2 2";
    // answer
    // 1
    // 2
    // return "2\n" +
    // "0 0 1 0\n" +
    // "2 0 3 0";
    // answer
    // 2
    // 1
    // return "2\n" +
    // "1 7 2 8\n" +
    // "3 9 2 2";
    // answer
    // 2
    // 1
    // return "2\n" +
    // "1 1 1 2\n" +
    // "1 3 3 3";
    // answer
    // 2
    // 1
    //    return "3\n" +
    //        "1 1 2 3\n" +
    //        "2 1 0 0\n" +
    //        "1 0 1 1";
    // answer
    // 1
    // 3
    // return "6\n" +
    // "1 1 -1 1\n" +
    // "-1 1 -1 -1\n" +
    // "-1 -1 1 -1\n" +
    // "1 -1 1 1\n" +
    // "-1 -1 1 1\n" +
    // "-1 1 1 -1";
    // answer
    // 1
    // 6
    // return "2\n" +
    // "0 0 2 11\n" +
    // "1 1 1 10";
    // answer
    // 1
    // 2
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final int N = EasyReader.readInt(context);

    double[] X1 = new double[N];
    double[] Y1 = new double[N];
    double[] X2 = new double[N];
    double[] Y2 = new double[N];
    double[] A = new double[N];
    double[] B = new double[N];
    for (int n = 0; n < N; ++n) {
      double x1 = EasyReader.readDouble(context);
      double y1 = EasyReader.readDouble(context);
      double x2 = EasyReader.readDouble(context);
      double y2 = EasyReader.readDouble(context);

      if (x1 < x2) {
        X1[n] = x1;
        Y1[n] = y1;
        X2[n] = x2;
        Y2[n] = y2;
      } else if (x1 > x2) {
        X1[n] = x2;
        Y1[n] = y2;
        X2[n] = x1;
        Y2[n] = y1;
      } else {
        if (y1 <= y2) {
          X1[n] = x1;
          Y1[n] = y1;
          X2[n] = x2;
          Y2[n] = y2;
        } else {
          X1[n] = x2;
          Y1[n] = y2;
          X2[n] = x1;
          Y2[n] = y1;
        }
      }
      A[n] = getA(x1, y1, x2, y2);
      B[n] = getB(x1, y1, x2, y2);
    }

    Map<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>();
    for (int n = 0; n < N - 1; ++n) {
      for (int m = n + 1; m < N; ++m) {
        if (Double.isInfinite(A[n])) {
          if (Double.isInfinite(A[m])) {
            if (X1[n] != X1[m]) {
              continue;
            } else if (Math.max(Y1[m], Y2[m]) < Math.min(Y1[n], Y2[n])
                || Math.max(Y1[n], Y2[n]) < Math.min(Y1[m], Y2[m])) {
              continue;
            }
          } else {
            double crossY = (X1[n] * A[m]) + B[m];
            if (X1[n] < Math.min(X1[m], X2[m]) || Math.max(X1[m], X2[m]) < X1[n]
                || crossY < Math.min(Y1[n], Y2[n]) || Math.max(Y1[n], Y2[n]) < crossY
                || crossY < Math.min(Y1[m], Y2[m]) || Math.max(Y1[m], Y2[m]) < crossY) {
              continue;
            }
          }
          addLine(graph, n, m);
          addLine(graph, m, n);
        } else if (Double.isInfinite(A[m])) {
          double crossY = (X1[m] * A[n]) + B[n];
          if (X1[m] < Math.min(X1[n], X2[n]) || Math.max(X1[n], X2[n]) < X1[m]
              || crossY < Math.min(Y1[n], Y2[n]) || Math.max(Y1[n], Y2[n]) < crossY
              || crossY < Math.min(Y1[m], Y2[m]) || Math.max(Y1[m], Y2[m]) < crossY) {
            continue;
          }
          addLine(graph, n, m);
          addLine(graph, m, n);
        } else if (A[n] == A[m]) {
          if ((B[n] != B[m])) {
            continue;
          }
          if (Math.max(X1[m], X2[m]) < Math.min(X1[n], X2[n]) || Math.max(X1[n], X2[n]) < Math.min(X1[m], X2[m])) {
            continue;
          }
          addLine(graph, n, m);
          addLine(graph, m, n);
        } else {
          double x = getCrossX(A[n], B[n], A[m], B[m]);
          if (Math.min(X1[m], X2[m]) <= x && x <= Math.max(X1[m], X2[m])
              && Math.min(X1[n], X2[n]) <= x && x <= Math.max(X1[n], X2[n])) {
            addLine(graph, n, m);
            addLine(graph, m, n);
          }
        }
      }
    }

    int count = 0;
    int maxNodes = 0;
    boolean[] visit = new boolean[N];
    Deque<Integer> travers = new ArrayDeque<Integer>();
    for (int n = 0; n < N; ++n) {
      if (visit[n]) {
        continue;
      }

      count++;
      int root = n;
      int nodeCount = 0;
      travers.clear();
      travers.push(root);
      while (!travers.isEmpty()) {
        int current = travers.pop();
        if (visit[current]) {
          continue;
        }
        nodeCount++;
        visit[current] = true;

        List<Integer> children = graph.get(current);
        if (children != null) {
          Iterator<Integer> childrenIterator = children.iterator();
          while (childrenIterator.hasNext()) {
            int child = childrenIterator.next();
            if (!visit[child]) {
              travers.push(child);
            }
          }
        }
      }
      maxNodes = Math.max(maxNodes, nodeCount);
    }

    System.out.println(count);
    System.out.println(maxNodes);
    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  private static double getCrossX(double an, double bn, double am, double bm) {
    if (an == am) {
      return Double.POSITIVE_INFINITY;
    } else {
      return (bm - bn) / (an - am);
    }
  }

  private static double getB(double x1, double y1, double x2, double y2) {
    if (x1 == x2) {
      return Double.POSITIVE_INFINITY;
    }
    return ((x2 * y1) - (x1 * y2)) / (x2 - x1);
  }

  private static double getA(double x1, double y1, double x2, double y2) {
    if (x1 == x2) {
      return Double.POSITIVE_INFINITY;
    }
    return (y2 - y1) / (x2 - x1);
  }

  private static void addLine(Map<Integer, List<Integer>> graph, int start, int end) {
    // if (start == 2 || end == 2) {
    // System.out.println("line : " + start + ", " + end);
    // }
    List<Integer> list = graph.get(start);
    if (list == null) {
      list = new ArrayList<Integer>();
      graph.put(start, list);
    }
    list.add(end);
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