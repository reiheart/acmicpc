import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Solution6497 {

  private static String getTestString() {
    // input sample
    // 7 11
    // 0 1 7
    // 0 3 5
    // 1 2 8
    // 1 3 9
    // 1 4 7
    // 2 4 5
    // 3 4 15
    // 3 5 6
    // 4 5 8
    // 4 6 9
    // 5 6 11
    // 0 0
    // answer
    // 51
    return "7 11\n" +
    "0 1 7\n" +
    "0 3 5\n" +
    "1 2 8\n" +
    "1 3 9\n" +
    "1 4 7\n" +
    "2 4 5\n" +
    "3 4 15\n" +
    "3 5 6\n" +
    "4 5 8\n" +
    "4 6 9\n" +
    "5 6 11\n" +
    "0 0";

  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    while (true) {
      long st = System.nanoTime();

      final int M = EasyReader.readInt(context);
      final int N = EasyReader.readInt(context);

      if (M == 0 && N == 0) {
        break;
      }

      Queue<Road> queue = new PriorityQueue<Road>(new Comparator<Road>() {
        @Override
        public int compare(Road o1, Road o2) {
          if (o1.v < o2.v) {
            return -1;
          }
          if (o1.v > o2.v) {
            return 1;
          }
          return 0;
        }
      });

      int[] set = new int[M];
      initSet(set);

      long sum = 0;
      long min = 0;
      for (int i = 0; i < N; ++i) {
        int x = EasyReader.readInt(context);
        int y = EasyReader.readInt(context);
        int z = EasyReader.readInt(context);
        sum += z;
        queue.add(new Road(x, y, z));
      }
      while (!queue.isEmpty()) {
        Road poll = queue.poll();
        int findA = find(set, poll.a);
        int findB = find(set, poll.b);
        if (findA != findB) {
          min += poll.v;
          union(set, findA, findB);
        }
      }

      System.out.println(sum - min);

      long dur = System.nanoTime() - st;
      System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
    }
  }

  private static void initSet(int[] set) {
    for (int m = 0; m < set.length; ++m) {
      set[m] = m;
    }
  }

  private static void union(int[] set, int a, int b) {
    set[find(set, b)] = find(set, a);
  }

  private static int find(int[] set, int a) {
    if (set[a] == a) {
      return a;
    }

    int root = find(set, set[a]);
    set[a] = root;

    return root;
  }

  static class Road {
    int a;
    int b;
    int v;

    Road(int x, int y, int z) {
      a = x;
      b = y;
      if (a > b) {
        a = y;
        b = x;
      }
      v = z;
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