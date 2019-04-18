import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;
import java.util.PriorityQueue;

public class Solution5719 {

  private static String getTestString() {
    // input sample
    // 7 9
    // 0 6
    // 0 1 1
    // 0 2 1
    // 0 3 2
    // 0 4 3
    // 1 5 2
    // 2 6 4
    // 3 6 2
    // 4 6 4
    // 5 6 1
    // 4 6
    // 0 2
    // 0 1 1
    // 1 2 1
    // 1 3 1
    // 3 2 1
    // 2 0 3
    // 3 0 2
    // 6 8
    // 0 1
    // 0 1 1
    // 0 2 2
    // 0 3 3
    // 2 5 3
    // 3 4 2
    // 4 1 1
    // 5 1 1
    // 3 0 1
    // 0 0
    // answer
    // 5
    // -1
    // 6
    return "7 9\n" +
    "0 6\n" +
    "0 1 1\n" +
    "0 2 1\n" +
    "0 3 2\n" +
    "0 4 3\n" +
    "1 5 2\n" +
    "2 6 4\n" +
    "3 6 2\n" +
    "4 6 4\n" +
    "5 6 1\n" +
    "4 6\n" +
    "0 2\n" +
    "0 1 1\n" +
    "1 2 1\n" +
    "1 3 1\n" +
    "3 2 1\n" +
    "2 0 3\n" +
    "3 0 2\n" +
    "6 8\n" +
    "0 1\n" +
    "0 1 1\n" +
    "0 2 2\n" +
    "0 3 3\n" +
    "2 5 3\n" +
    "3 4 2\n" +
    "4 1 1\n" +
    "5 1 1\n" +
    "3 0 1\n" +
    "5 3\n" +
    "0 4\n" +
    "0 1 1\n" +
    "0 2 1\n" +
    "0 3 1\n" +
    "4 4\n" +
    "0 3\n" +
    "0 1 2\n" +
    "1 2 2\n" +
    "2 3 2\n" +
    "0 3 10\n" +
    "4 5\n" +
    "0 2\n" +
    "0 1 1\n" +
    "0 3 5\n" +
    "1 2 2\n" +
    "1 3 1\n" +
    "3 2 1\n" +
    "4 5\n" +
    "0 2\n" +
    "0 1 1\n" +
    "0 3 5\n" +
    "1 2 2\n" +
    "1 3 1\n" +
    "3 2 1\n" +
    "4 5\n" +
    "0 2\n" +
    "0 1 5\n" +
    "0 3 1\n" +
    "1 2 1\n" +
    "3 1 1\n" +
    "3 2 2\n" +
    "4 5\n" +
    "0 2\n" +
    "0 1 1\n" +
    "0 3 5\n" +
    "1 2 1\n" +
    "1 3 1\n" +
    "3 2 1\n" +
    "0 0";
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    // String testStr = getTestString();
    // ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    URL resource = Solution5719.class.getClassLoader().getResource("Sample5719.in");
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(Paths.get(resource.toURI()));

    while (true) {
      long st = System.nanoTime();

      final int N = EasyReader.readInt(context);
      final int M = EasyReader.readInt(context);
      if (N == 0 && M == 0) {
        break;
      }
      final int S = EasyReader.readInt(context);
      final int D = EasyReader.readInt(context);

      DirectionGraph graph = new DirectionGraph(N);
      for (int m = 0; m < M; ++m) {
        final int U = EasyReader.readInt(context);
        final int V = EasyReader.readInt(context);
        final int P = EasyReader.readInt(context);
        graph.add(U, V, P);
      }

      System.out.println(graph.getAlmostMin(S, D));
      long dur = System.nanoTime() - st;
      // System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
    }
  }

  private static class DirectionGraph {
    int[][] cost;

    public DirectionGraph(int capa) {
      cost = new int[capa][capa];
      for (int i = 0; i < cost.length; ++i) {
        Arrays.fill(cost[i], -1);
      }
    }

    public int getAlmostMin(int s, int d) {
      int[] minLength = dijkstra(s);
      int min = minLength[d];
      if (min == Integer.MAX_VALUE) {
        return -1;
      }

      Deque<Integer> q = new ArrayDeque<Integer>();
      q.add(d);
      while (!q.isEmpty()) {
        int poll = q.poll();
        for (int i = 0; i < cost.length; ++i) {
          if (cost[i][poll] == -1) {
            continue;
          }
          if (minLength[poll] - cost[i][poll] == minLength[i]) {
            cost[i][poll] = -1;
            q.add(i);
          }
        }
      }

      minLength = dijkstra(s);

      // System.out.println(Arrays.toString(minLength) + d);
      return minLength[d] == Integer.MAX_VALUE ? -1 : minLength[d];
    }

    private int[] dijkstra(int s) {
      int[] result = new int[cost.length];
      Arrays.fill(result, Integer.MAX_VALUE);
      boolean[] visit = new boolean[cost.length];
      PriorityQueue<Integer> q = new PriorityQueue<Integer>(new Comparator<Integer>() {
        @Override
        public int compare(Integer o1, Integer o2) {
          if (result[o1.intValue()] < result[o2.intValue()]) {
            return -1;
          }
          if (result[o1.intValue()] > result[o2.intValue()]) {
            return 1;
          }
          return 0;
        }
      });

      result[s] = 0;
      q.add(s);


      while (!q.isEmpty()) {
        int poll = q.poll();
        if (visit[poll]) {
          continue;
        }
        visit[poll] = true;
        for (int i = 0; i < cost[poll].length; ++i) {
          if (cost[poll][i] == -1) {
            continue;
          }
          int calc = result[poll] + cost[poll][i];
          if (result[i] > calc) {
            result[i] = calc;
          }
          if (!visit[i]) {
            q.add(i);
          }
        }
      }

      return result;
    }

    public void add(int u, int v, int p) {
      cost[u][v] = p;
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