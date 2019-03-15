import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

public class Solution10835 {

  private static String getTestString() {
    return "3\n" +
        "3 2 5\n" +
        "2 4 1";
    // return "4\n" +
    // "1 2 3 4\n" +
    // "4 1 2 3";
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long sTime = System.nanoTime();
    int result = 0;

    final int count = EasyReader.readInt(context);

    int[] left = new int[count];
    SegTree leftMinSegTree = new SegTree(true, count);
    SegTree leftMaxSegTree = new SegTree(false, count);
    for (int i = 0; i < left.length; i++) {
      int value = EasyReader.readInt(context);
      left[i] = value;
      leftMinSegTree.add(value);
      leftMaxSegTree.add(value);
    }
    int[] right = new int[count];
    SegTree rightMinSegTree = new SegTree(true, count);
    SegTree rightMaxSegTree = new SegTree(false, count);
    for (int i = 0; i < right.length; i++) {
      int value = EasyReader.readInt(context);
      right[i] = value;
      rightMinSegTree.add(value);
      rightMaxSegTree.add(value);
    }

    System.out.println(leftMinSegTree.query(0, count));
    System.out.println(leftMaxSegTree.query(0, count));
    System.out.println(rightMinSegTree.query(0, count));
    System.out.println(rightMaxSegTree.query(0, count));
    System.out.println((System.nanoTime() - sTime) + " nano sec.");
  }

  public static class SegTree {
    private final boolean isMin;
    private final int capacity;
    private final int[] array;
    private final int[] tree;
    private final int addIndex = 0;

    public SegTree(boolean isMin, int capacity) {
      this.isMin = isMin;
      this.capacity = capacity;
      this.array = new int[capacity];
      this.tree = new int[capacity * 2];
    }

    public int add(int value) {
      array[addIndex] = value;
      return addTree(addIndex, value, 1, 0, capacity - 1);
    }

    private int addTree(int index, int value, int treeIndex, int start, int end) {
      if (index < start || end < index) {
        if (isMin) {
          return Integer.MAX_VALUE;
        } else {
          return Integer.MIN_VALUE;
        }
      } else if (start == end) {
        tree[treeIndex] = value;
        return value;
      }

      int leftTree = addTree(index, value, treeIndex * 2, start, end / 2);
      int rightTree = addTree(index, value, (treeIndex * 2) + 1, (end / 2) + 1, end);
      if (leftTree>rightTree) {
        if (isMin) {
          tree[treeIndex] = rightTree;
          return rightTree;
        } else {
          tree[treeIndex] = leftTree;
          return leftTree;
        }
      } else {
        if (isMin) {
          tree[treeIndex] = rightTree;
          return rightTree;
        } else {
          tree[treeIndex] = leftTree;
          return leftTree;
        }
      }
    }

    public int query(int start, int end) {
      return query(start, end, 0, 0, capacity - 1);
    }

    private int query(int start, int end, int treeIndex, int queryStart, int queryEnd) {
      if (queryStart < start) {
        if (end < queryEnd) {
        }

      }

      return 0;
    }

    public boolean isMinTree() {
      return isMin;
    }

    public boolean isMaxTree() {
      return !isMin;
    }
  }

  public static class EasyReader {
    public static class ReadingContext {
      private final String separator;
      private final BufferedReader reader;
      private String[] separatedLine;
      private String line;
      private int offSet;

      // public ReadingContext(Path path) throws FileNotFoundException {
      // this(new FileInputStream(path.toFile()));
      // }

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