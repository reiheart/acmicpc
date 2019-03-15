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
import java.util.Iterator;
import java.util.List;

public class Solution11437 {

  private static String getTestString() {
    // input sample
    // 15
    // 1 2
    // 1 3
    // 2 4
    // 3 7
    // 6 2
    // 3 8
    // 4 9
    // 2 5
    // 5 11
    // 7 13
    // 10 4
    // 11 15
    // 12 5
    // 14 7
    // 6
    // 6 11
    // 10 9
    // 2 6
    // 7 6
    // 8 13
    // 8 15
    // answer
    // 2
    // 4
    // 2
    // 1
    // 3
    // 1
    return "15\n" +
    "1 2\n" +
    "1 3\n" +
    "2 4\n" +
    "3 7\n" +
    "6 2\n" +
    "3 8\n" +
    "4 9\n" +
    "2 5\n" +
    "5 11\n" +
    "7 13\n" +
    "10 4\n" +
    "11 15\n" +
    "12 5\n" +
    "14 7\n" +
    "6\n" +
    "6 11\n" +
    "10 9\n" +
    "2 6\n" +
    "7 6\n" +
    "8 13\n" +
    "8 15";
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final int N = EasyReader.readInt(context);
    Tree t = new Tree(N);
    for (int n = 1; n < N; ++n) {
      int a = EasyReader.readInt(context);
      int b = EasyReader.readInt(context);
      t.add(a, b);
    }
    t.fixDepth(1);
    int[] nodeIndexer = new int[N + 1];
    Integer[] traversArray = travers(t, nodeIndexer);
    int[] segArray = buildSegTree(t, traversArray);
    StringBuffer sb = new StringBuffer();

    final int M = EasyReader.readInt(context);
    for (int m = 0; m < M; ++m) {
      if (m != 0) {
        sb.append("\n");
      }
      int a = EasyReader.readInt(context);
      int b = EasyReader.readInt(context);
      sb.append(query(t, segArray, traversArray.length - 1, nodeIndexer, a, b));
    }

    System.out.println(sb.toString());
    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  private static int query(Tree t, int[] segArray, int travSize, int[] nodeIndexer, int a, int b) {
    return query(t, segArray, 1, 0, travSize, Math.min(nodeIndexer[a], nodeIndexer[b]),
        Math.max(nodeIndexer[a], nodeIndexer[b]));
  }

  private static int query(Tree t, int[] segArray, int seg, int start, int end, int min, int max) {
    if (max < start || end < min) {
      return -1;
    } else if (min <= start && end <= max) {
      return segArray[seg];
    }

    if (start == end) {
      return segArray[seg];
    }

    int lValue = query(t, segArray, seg * 2, start, (start + end) / 2, min, max);
    int rValue = query(t, segArray, (seg * 2) + 1, ((start + end) / 2) + 1, end, min, max);

    if (lValue != -1 && rValue != -1) {
      if (t.nodes[lValue].depth > t.nodes[rValue].depth) {
        return rValue;
      } else {
        return lValue;
      }
    } else if (lValue != -1) {
      return lValue;
    } else if (rValue != -1) {
      return rValue;
    }
    return -1;
  }

  private static int[] buildSegTree(Tree t, Integer[] traversArray) {
    int[] array = new int[getSegTreeSize(traversArray.length)];
    for (int i = 0; i < traversArray.length; ++i) {
      buildSegTree(t, array, 1, 0, traversArray.length - 1, traversArray, i);
    }
    return array;
  }

  private static void buildSegTree(Tree t, int[] segArray, int seg, int start, int end,
      Integer[] traversArray, int id) {
    if (id < start || end < id) {
      return;
    }

    if (start == end) {
      segArray[seg] = traversArray[id];
      return;
    }

    buildSegTree(t, segArray, seg * 2, start, (start + end) / 2, traversArray, id);
    buildSegTree(t, segArray, (seg * 2) + 1, ((start + end) / 2) + 1, end, traversArray, id);

    if (segArray[seg * 2] != -1 && segArray[(seg * 2) + 1] != -1) {
      if (t.nodes[segArray[seg * 2]].depth > t.nodes[segArray[(seg * 2) + 1]].depth) {
        segArray[seg] = segArray[(seg * 2) + 1];
      } else {
        segArray[seg] = segArray[seg * 2];
      }
    } else if (segArray[seg * 2] != -1) {
      segArray[seg] = segArray[seg * 2];
    } else if (segArray[(seg * 2) + 1] != -1) {
      segArray[seg] = segArray[(seg * 2) + 1];
    } else {
      segArray[seg] = -1;
    }
  }

  private static int getSegTreeSize(int length) {
    int size = 2;
    while (length > size / 2) {
      size *= 2;
    }
    return size;
  }

  private static Integer[] travers(Tree t, int[] nodeIndexer) {
    List<Integer> trav = new ArrayList<Integer>();
    Deque<Integer> preStack = new ArrayDeque<Integer>();
    Deque<Integer> postStack = new ArrayDeque<Integer>();
    preStack.push(1);
    postStack.push(1);

    while (!preStack.isEmpty()) {
      int prePop = preStack.pop();
      int postPop = postStack.peek();

      if (prePop != postPop) {
        do  {
          postPop = postStack.pop();
          trav.add(t.nodes[postPop].parent);
        } while (t.nodes[prePop].parent != t.nodes[postPop].parent);
      }

      trav.add(prePop);
      nodeIndexer[prePop] = trav.size() - 1;

      Iterator<Integer> cIterator = t.nodes[prePop].children.iterator();
      while (cIterator.hasNext()) {
        int c = cIterator.next();
        preStack.push(c);
        postStack.push(c);
      }
    }

    return trav.toArray(new Integer[trav.size()]);
  }

  public static class Node {
    int parent;
    int depth;
    List<Integer> children;

    public Node() {
      children = new ArrayList<Integer>();
    }
  }

  public static class Tree {
    Node[] nodes;

    public Tree(int count) {
      nodes = new Node[count + 1];
      ensureNode(0);
      ensureNode(1);
      nodes[1].depth = 1;
    }

    public void add(int a, int b) {
      ensureNode(a);
      ensureNode(b);
      addChild(a, b);
      addChild(b, a);
    }

    private void ensureNode(int id) {
      if (nodes[id] == null) {
        nodes[id] = new Node();
      }
    }

    public void fixDepth(int id) {
      Deque<Integer> stack = new ArrayDeque<Integer>();
      stack.push(id);
      while (!stack.isEmpty()) {
        int parent = stack.pop();

        Iterator<Integer> childIterator = nodes[parent].children.iterator();
        while (childIterator.hasNext()) {
          int c = childIterator.next();
          if (c == nodes[parent].parent) {
            childIterator.remove();
            continue;
          }
          nodes[c].parent = parent;
          nodes[c].depth = nodes[parent].depth + 1;
          stack.push(c);
        }
      }
    }

    private void addChild(int a, int b) {
      nodes[a].children.add(b);
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