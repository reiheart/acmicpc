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

public class Solution11266 {

  private static String getTestString() {
    // input sample
    // 7 7
    // 1 4
    // 4 5
    // 5 1
    // 1 6
    // 6 7
    // 2 7
    // 7 3
    // answer
    // 3
    // 1 6 7
    return "7 7\n" +
    "1 4\n" +
    "4 5\n" +
    "5 1\n" +
    "1 6\n" +
    "6 7\n" +
    "2 7\n" +
    "7 3";
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final int V = EasyReader.readInt(context);
    final int E = EasyReader.readInt(context);

    Map<Integer, List<Integer>> graph = new HashMap<Integer, List<Integer>>();
    for (int e = 0; e < E; ++e) {
      final int A = EasyReader.readInt(context);
      final int B = EasyReader.readInt(context);
      addLine(graph, A, B);
      addLine(graph, B, A);
    }

    int[] rank = new int[V + 1];
    int[] lowRank = new int[V + 1];
    Deque<Integer> stack = new ArrayDeque<Integer>();
    Deque<Integer> pathStack = new ArrayDeque<Integer>();

    boolean[] visit = new boolean[V + 1];
    int[] answer = new int[V + 1];
    int offset = 1;
    for (int v = 1; v <= V; ++v) {
      if (visit[v]) {
        continue;
      }

      visit[v] = true;
      List<Integer> rootChildren = graph.get(v);
      pathStack.push(v);
      boolean visitAllOnFirst = true;
      if (rootChildren != null && !rootChildren.isEmpty()) {
        Iterator<Integer> cIterator = rootChildren.iterator();
        int c = cIterator.next();
        stack.push(c);
        do {
          int r = 1;
          rank[v] = r;
          int scan = 1;
          lowRank[v] = r;
          while (!stack.isEmpty()) {
            int pop = stack.pop();
            pathStack.push(v);
            r++;
            rank[v] = r;
            lowRank[v] = r;
            visit[pop] = true;
            List<Integer> popChild = graph.get(pop);
            if (popChild != null && !popChild.isEmpty()) {
              Iterator<Integer> iterator = popChild.iterator();
              while (iterator.hasNext()) {
                int popC = iterator.next();
                if (popC == pop) {
                  continue;
                }
                if (visit[popC]) {
                  lowRank[pop] = Math.min(lowRank[pop], lowRank[popC]);
                } else {
                  stack.push(popC);
                }
              }
            }
          }

          if (cIterator.hasNext()) {
            c = cIterator.next();
            if (!visit[c]) {
              visitAllOnFirst = false;
              stack.push(c);
            }
          }
        } while (cIterator.hasNext());

      }
      if (!visitAllOnFirst) {
        offset = pushAnswer(answer, offset, v);
      }
      stack.clear();
    }

    System.out.println(offset - 1);
    for (int i = offset; i > 0; --i) {
      System.out.print(popAnswer(answer, i) + " ");
    }
    System.out.println();
    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  private static int pushAnswer(int[] answer, int offset, int num) {
    System.out.println("push answer " + num + "/" + offset);
    int scan = offset;
    answer[scan] = num;
    while (scan / 2 > 0 && answer[scan] > answer[scan / 2]) {
      int temp = answer[scan];
      answer[scan] = answer[scan / 2];
      answer[scan / 2] = temp;

      scan = scan / 2;
    }

    return ++offset;
  }

  private static int popAnswer(int[] answer, int offset) {
    if (offset < 1) {
      return -1;
    }
    int scan = 1;
    int min = answer[scan];
    answer[scan] = answer[--offset];
    while (scan * 2 < offset) {
      int minChild = scan * 2;
      if (minChild + 1 < offset) {
        if (answer[minChild] > answer[minChild + 1]) {
          minChild += 1;
        }
      }
      if (answer[scan] > answer[minChild]) {
        int temp = answer[scan];
        answer[scan] = answer[minChild];
        answer[minChild] = temp;

        scan = minChild;
      } else {
        break;
      }
    }

    return min;
  }

  private static void addLine(Map<Integer, List<Integer>> graph, int start, int end) {
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