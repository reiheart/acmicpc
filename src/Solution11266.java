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
    // return "7 7\n" +
    // "1 4\n" +
    // "4 5\n" +
    // "5 1\n" +
    // "1 6\n" +
    // "6 7\n" +
    // "2 7\n" +
    // "7 3";

    // input sample
    // 7 6
    // 1 4
    // 1 3
    // 5 1
    // 1 6
    // 7 1
    // 2 1
    // answer
    // 1
    // 1
    return "13 13\n" +
    "1 4\n" +
    "1 3\n" +
    "5 1\n" +
    "1 6\n" +
    "7 1\n" +
    "2 1\n" +
    "3 8\n" +
    "3 9\n" +
    "8 9\n"+
    "10 11\n" +
    "10 12\n" +
    "11 12\n" +
    "13 12";
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
    int[] path = new int[V + 1];
    int pathOffset = 1;
    Deque<Integer> stack = new ArrayDeque<Integer>();
    Deque<Integer> pathStack = new ArrayDeque<Integer>();

    boolean[] visit = new boolean[V + 1];
    boolean[] answerChecker = new boolean[V + 1];
    int[] answer = new int[V + 1];
    int answerOffset = 1;
    for (int v = 1; v <= V; ++v) {
      if (visit[v]) {
        continue;
      }

      // dfs from (root = v)
      final int root = v;

      stack.push(root);
      pathStack.push(0);
      while (!stack.isEmpty()) {
        int pop = stack.pop();
        int prePath = pathStack.pop();
        if (visit[pop]) {
          continue;
        }
        if (path[pathOffset - 1] != prePath) {
          // back to pre path
          int markLowRank = lowRank[path[pathOffset - 1]];
          do {
            --pathOffset;
            if (pathOffset <= 0) {
              break;
            }
            if (!answerChecker[path[pathOffset - 1]]) {
              if (root == path[pathOffset - 1]) {
                if (!stack.isEmpty()) {
                  answerOffset = pushAnswer(answer, answerOffset, root);
                  answerChecker[root] = true;
                }
              } else if (markLowRank >= lowRank[path[pathOffset - 1]]) {
                answerOffset = pushAnswer(answer, answerOffset, path[pathOffset - 1]);
                answerChecker[path[pathOffset - 1]] = true;
              }
            }
            markLowRank = Math.min(markLowRank, lowRank[path[pathOffset - 1]]);
          } while (path[pathOffset - 1] != prePath);
        }

        visit[pop] = true;
        rank[pop] = pathOffset;
        lowRank[pop] = pathOffset;
        path[pathOffset++] = pop;

        List<Integer> rootChildren = graph.get(pop);
        if (rootChildren != null) {
          Iterator<Integer> childIterator = rootChildren.iterator();
          while (childIterator.hasNext()) {
            int child = childIterator.next();
            if (!visit[child]) {
              stack.push(child);
              pathStack.push(pop);
            } else if (prePath != child) {
              lowRank[pop] = Math.min(lowRank[pop], lowRank[child]);
            }
          }
        }
      }

      int markLowRank = lowRank[path[pathOffset - 1]];
      for (pathOffset = pathOffset - 1; pathOffset > 1; --pathOffset) {
        // back to root path
        if (!answerChecker[path[pathOffset - 1]]) {
          if (root == path[pathOffset - 1]) {
            if (!stack.isEmpty()) {
              answerOffset = pushAnswer(answer, answerOffset, root);
              answerChecker[root] = true;
            }
          } else if (markLowRank >= lowRank[path[pathOffset - 1]]) {
            answerOffset = pushAnswer(answer, answerOffset, path[pathOffset - 1]);
            answerChecker[path[pathOffset - 1]] = true;
          }
        }
        markLowRank = Math.min(markLowRank, lowRank[path[pathOffset - 1]]);
      }

      stack.clear();
      pathStack.clear();
    }

    StringBuffer sb = new StringBuffer();
    sb.append(answerOffset - 1);
    if (answerOffset - 1 > 0) {
      sb.append("\n");
      for (int i = answerOffset; i > 1; --i) {
        if (answerOffset != i) {
          sb.append(" ");
        }
        sb.append(popAnswer(answer, i));
      }
      answerOffset = 1;
    }

    System.out.println(sb.toString());
    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  private static int pushAnswer(int[] answer, int offset, int num) {
    int scan = offset;
    answer[scan] = num;
    while (scan / 2 > 0 && answer[scan] < answer[scan / 2]) {
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