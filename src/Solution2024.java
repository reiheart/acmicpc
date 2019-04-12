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

public class Solution2024 {

  private static String getTestString() {
    // input sample
    // 1
    // -1 0
    // 0 1
    // 0 0
    // answer
    // 1
    // return "1\n" +
    //    "-1 0\n" +
    //    "0 1\n" +
    // return "20\n" +
    // "-1 0\n" +
    // "2 10\n" +
    // "2 5\n" +
    // "1 9\n" +
    // "3 7\n" +
    // "7 17\n" +
    // "5 12\n" +
    // "14 19\n" +
    // "20 20\n" +
    return "4\n" +
    "-2 -1\n" +
    "-1 0\n" +
    "1 1\n" +
    "2 2\n" +
        "2 3\n" +
    "3 3\n" +
    "3 3\n" +
    "4 4\n" +
    "0 0";

  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final int M = EasyReader.readInt(context);
    int[] L = new int[100000];
    int[] R = new int[100000];
    PriorityQueue<Integer> sortLength = new PriorityQueue<Integer>(new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        int l1 = R[o1.intValue()] - L[o1.intValue()];
        int l2 = R[o2.intValue()] - L[o2.intValue()];
        if (l1 > l2) {
          return -1;
        }
        if (l1 < l2) {
          return 1;
        }
        return 0;
      }
    });
    int offset = 0;
    while (true) {
      int l = EasyReader.readInt(context);
      int r = EasyReader.readInt(context);
      if (l == 0 && r == 0) {
        break;
      }
      if (l < r) {
        if (r < 0 || l > M) {
          continue;
        }
        L[offset] = Math.max(l, 0);
        R[offset] = Math.min(r, M);
      } else {
        if (l < 0 || r > M) {
          continue;
        }
        L[offset] = Math.max(r, 0);
        R[offset] = Math.min(l, M);
      }
      sortLength.add(offset++);
    }

    int count = 0;
    boolean[] segTree = new boolean[getSegTreeSize(M + 1)];
    while (!sortLength.isEmpty()) {
      int poll = sortLength.poll();
      if (queryCoveredAndFix(M, L, R, poll, segTree)) {
        // System.out.println("L" + Arrays.toString(Arrays.copyOfRange(L, 0, offset)));
        // System.out.println("R" + Arrays.toString(Arrays.copyOfRange(R, 0, offset)));
        // System.out.println(poll + "S" + Arrays.toString(segTree));
        cover(M, L, R, poll, segTree);
        count++;
      } else {
        if (R[poll] < L[poll]) {
          continue;
        }
        sortLength.add(poll);
      }
      if (segTree[1]) {
        break;
      }
    }

    if (!segTree[1]) {
      count = 0;
    }
    System.out.println(count);

    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  private static boolean queryCoveredAndFix(int m, int[] l, int[] r, int poll, boolean[] segTree) {
    int[] result = queryCovered(segTree, 1, 0, m, l, r, poll);
    if (result[0] > result[1]) {
      return true;
    }
    if (l[poll] < result[0]) {
      r[poll] = result[0] - 1;
    } else if (r[poll] > result[1]) {
      l[poll] = result[1] + 1;
    } else {
      l[poll] = result[1] + 1;
      r[poll] = result[0] - 1;
    }

    System.out.println(poll + " fix " + l[poll] + "," + r[poll]);
    return false;
  }

  static int[] falseReturn = new int[] { -1, -2 };
  private static int[] queryCovered(boolean[] segTree, int index, int i, int j, int[] l, int[] r, int poll) {
    if (r[poll] < i || l[poll] > j) {
      return falseReturn;
    }
    if (l[poll] <= i && j <= r[poll]) {
      if (segTree[index]) {
        return new int[] { i, j };
      }
      if (i == j) {
        return falseReturn;
      }
    }

    if (segTree[index]) {
      return new int[] { Math.max(i, l[poll]), Math.min(j, r[poll]) };
    }

    int mid = (i + j) / 2;
    int[] lquery = queryCovered(segTree, index * 2, i, mid, l, r, poll);
    int[] rquery = queryCovered(segTree, (index * 2) + 1, mid + 1, j, l, r, poll);

    if (lquery[0] > -1) {
      if (rquery[0] > -1) {
        lquery[1] = rquery[1];
        return lquery;
      } else {
        return lquery;
      }
    } else {
      return rquery;
    }
  }

  private static void cover(int m, int[] l, int[] r, int poll, boolean[] segTree) {
    cover(segTree, 1, 0, m, l[poll], r[poll]);
  }

  private static boolean cover(boolean[] segTree, int index, int i, int j, int l, int r) {
    if (r < i || l > j) {
      return segTree[index];
    }
    if (l <= i && j <= r) {
      segTree[index] = true;
      return true;
    }

    if (segTree[index]) {
      return true;
    }

    int mid = (i + j) / 2;
    boolean lCover = cover(segTree, index * 2, i, mid, l, r);
    boolean rCover = cover(segTree, (index * 2) + 1, mid + 1, j, l, r);

    segTree[index] = lCover && rCover;
    return segTree[index];
  }

  private static int getSegTreeSize(int offset) {
    int size = 1;
    while (size < offset) {
      size *= 2;
    }
    return size * 2;
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