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
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Solution2843 {

  private static String getTestString() {
    // input sample
    // 3
    // 2 3 1
    // 7
    // 1 1
    // 1 2
    // 2 1
    // 1 2
    // 1 1
    // 2 2
    // 1 2
    // answer
    // CIKLUS
    // CIKLUS
    // 1
    // 1
    // 2
    return "3\n" +
    "2 3 1\n" +
    "7\n" +
    "1 1\n" +
    "1 2\n" +
    "2 1\n" +
    "1 2\n" +
    "1 1\n" +
    "2 2\n" +
    "1 2";

  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    // String testStr = getTestString();
    // ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    URL resource = Solution2843.class.getClassLoader().getResource("Sample2843.in");
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(Paths.get(resource.toURI()));

    long st = System.nanoTime();

    final int N = EasyReader.readInt(context);
    int[] P = new int[N + 1];
    // Map<Integer, List<Integer>> dests = new HashMap<Integer, List<Integer>>();
    for (int n = 1; n <= N; ++n) {
      int d = EasyReader.readInt(context);
      // List<Integer> list = dests.get(d);
      // if (list == null) {
      // list = new ArrayList<Integer>();
      // dests.put(d, list);
      // }
      // list.add(n);
      union(P, d, n);
    }
    long dur1 = System.nanoTime() - st;
    System.out.println(dur1 + " nsec" + " / " + (dur1 / 1000000) + " msec");
    final int Q = EasyReader.readInt(context);
    for (int q = 0; q < Q; ++q) {
      int t = EasyReader.readInt(context);
      int s = EasyReader.readInt(context);
      if (t == 1) {
        int result = query(P, s);
        if (result == -1) {
          System.out.println("CIKLUS");
        } else {
          System.out.println(result);
        }
      } else if (t == 2) {
        // remove(P, dests, s);
      }
    }

    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  private static int query(int[] p, int s) {
    return find(p, s);
  }

  private static void remove(int[] p, Map<Integer, List<Integer>> dests, int s) {
    p[s] = 0;

    Deque<Integer> q = new ArrayDeque<Integer>();
    q.push(s);
    while (!q.isEmpty()) {
      int pop = q.pop();

      List<Integer> list = dests.get(pop);
      if (list != null) {
        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()) {
          int next = iterator.next();
          if (next == s) {
            continue;
          }
          p[next] = 0;
          union(p, s, next);
          q.push(next);
        }
      }
    }
  }

  private static void union(int[] set, int a, int b) {
    set[find(set, b)] = find(set, a);
  }

  private static int find(int[] set, int a) {
    if (set[a] == 0) {
      return a;
    }
    if (set[a] == a) {
      return -1;
    }

    int root = find(set, set[a]);
    if (root != -1) {
      set[a] = root;
    }

    return root;
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