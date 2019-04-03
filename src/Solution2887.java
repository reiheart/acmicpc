import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Solution2887 {
  private static String getTestString() {
    return "5\n" +
        "11 -15 -15\n" +
        "14 -5 -15\n" +
        "-1 -1 -5\n" +
        "10 -4 -1\n" +
        "19 -4 19";
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final int N = EasyReader.readInt(context);
    int[] X = new int[N];
    int[] Y = new int[N];
    int[] Z = new int[N];
    int[] sortX = new int[N + 1];
    int[] sortY = new int[N + 1];
    int[] sortZ = new int[N + 1];
    for (int n = 0; n < N; ++n) {
      X[n] = EasyReader.readInt(context);
      Y[n] = EasyReader.readInt(context);
      Z[n] = EasyReader.readInt(context);
      addAndSort(sortX, n + 1, n, X);
      addAndSort(sortY, n + 1, n, Y);
      addAndSort(sortZ, n + 1, n, Z);
    }
    int i = 0;
    int[] sortedX = new int[N];
    int[] sortedY = new int[N];
    int[] sortedZ = new int[N];
    for (int n = N + 1; n > 1; --n) {
      sortedX[i] = getFromSort(sortX, n, X);
      sortedY[i] = getFromSort(sortY, n, Y);
      sortedZ[i++] = getFromSort(sortZ, n, Z);
    }

    long min = 0;
    int[] current = new int[2];
    current[0] = 0;
    current[1] = 0;
    boolean[] visit = new boolean[N];
    int[] sortNode = new int[N * 6];
    int[] sortCost = new int[N * 6];
    int sortOffset = 1;
    sortOffset = addAndSort(sortNode, sortCost, sortOffset, current[0], current[0], X, Y, Z);
    while (sortOffset > 1) {
      current = getFromSort(sortNode, sortCost, sortOffset--);
      if (visit[current[0]]) {
        continue;
      }
      visit[current[0]] = true;
      min += current[1];

      int byX = binSearch(sortedX, current[0], X);
      int byY = binSearch(sortedY, current[0], Y);
      int byZ = binSearch(sortedZ, current[0], Z);
      for (int x = 1; byX - x >= 0; ++x) {
        if (visit[sortedX[byX - x]]) {
          continue;
        }
        sortOffset = addAndSort(sortNode, sortCost, sortOffset, sortedX[byX - x], sortedX[byX], X, Y, Z);
        break;
      }
      for (int x = 1; byX + x < sortedX.length; ++x) {
        if (visit[sortedX[byX + x]]) {
          continue;
        }
        sortOffset = addAndSort(sortNode, sortCost, sortOffset, sortedX[byX + x], sortedX[byX], X, Y, Z);
        break;
      }
      for (int y = 1; byY - y >= 0; ++y) {
        if (visit[sortedY[byY - y]]) {
          continue;
        }
        sortOffset = addAndSort(sortNode, sortCost, sortOffset, sortedY[byY - y], sortedY[byY], X, Y, Z);
        break;
      }
      for (int y = 1; byY + y < sortedY.length; ++y) {
        if (visit[sortedY[byY + y]]) {
          continue;
        }
        sortOffset = addAndSort(sortNode, sortCost, sortOffset, sortedY[byY + y], sortedY[byY], X, Y, Z);
        break;
      }
      for (int z = 1; byZ - z >= 0; ++z) {
        if (visit[sortedZ[byZ - z]]) {
          continue;
        }
        sortOffset = addAndSort(sortNode, sortCost, sortOffset, sortedZ[byZ - z], sortedZ[byZ], X, Y, Z);
        break;
      }
      for (int z = 1; byZ + z < sortedZ.length; ++z) {
        if (visit[sortedZ[byZ + z]]) {
          continue;
        }
        sortOffset = addAndSort(sortNode, sortCost, sortOffset, sortedZ[byZ + z], sortedZ[byZ], X, Y, Z);
        break;
      }
    }

    System.out.println(min);

    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  public static int getCost(int ax, int ay, int az, int bx, int by, int bz) {
    int x = abs(ax - bx);
    int y = abs(ay - by);
    int z = abs(az - bz);

    if (x < y) {
      if (x < z) {
        return x;
      }
      return z;
    } else if (y < z) {
      return y;
    } else {
      return z;
    }
  }

  public static int abs(int a) {
    if (a < 0) {
      return -a;
    }
    return a;
  }

  private static int binSearch(int[] sorted, int index, int[] values) {
    int from = 0;
    int to = sorted.length - 1;

    while (from != to) {
      int mid = (from + to) / 2;
      if (sorted[mid] == index) {
        return mid;
      }

      if (values[sorted[mid]] < values[index]) {
        from = mid + 1;
      } else if (values[sorted[mid]] == values[index]) {
        int f = mid - 1;
        while (f >= 0 && values[index] == values[sorted[f]]) {
          if (index == sorted[f]) {
            return f;
          }
          f--;
        }
        f = mid + 1;
        while (f < sorted.length && values[index] == values[sorted[f]]) {
          if (index == sorted[f]) {
            return f;
          }
          f++;
        }
      } else {
        to = mid;
      }
    }

    if (sorted[from] == index) {
      return from;
    } else {
      return -1;
    }
  }

  private static int addAndSort(int[] sort, int offset, int index, int[] valueArr) {
    int arrIndex = offset++;
    sort[arrIndex] = index;
    while (arrIndex / 2 > 0) {
      int next = arrIndex / 2;
      if (valueArr[sort[arrIndex]] > valueArr[sort[next]]) {
        break;
      }

      int temp = sort[arrIndex];
      sort[arrIndex] = sort[next];
      sort[next] = temp;

      arrIndex = next;
    }
    return offset;
  }

  public static int getFromSort(int[] sort, int offset, int[] valueArr) {
    int arrIndex = 1;
    int get = sort[arrIndex];
    sort[arrIndex] = sort[--offset];
    while (arrIndex * 2 < offset) {
      int next = arrIndex * 2;
      if (next + 1 < offset) {
        if (valueArr[sort[next]] > valueArr[sort[next + 1]]) {
          next += 1;
        }
      }

      if (valueArr[sort[arrIndex]] < valueArr[sort[next]]) {
        break;
      }

      int temp = sort[arrIndex];
      sort[arrIndex] = sort[next];
      sort[next] = temp;

      arrIndex = next;
    }

    return get;
  }

  private static int addAndSort(int[] node, int[] cost, int offset, int n, int m, int[] X, int[] Y, int[] Z) {
    int arrIndex = offset++;
    node[arrIndex] = n;
    cost[arrIndex] = getCost(X[n], Y[n], Z[n], X[m], Y[m], Z[m]);
    while (arrIndex / 2 > 0) {
      int next = arrIndex / 2;
      if (cost[arrIndex] > cost[next]) {
        break;
      }

      int temp = node[arrIndex];
      node[arrIndex] = node[next];
      node[next] = temp;

      temp = cost[arrIndex];
      cost[arrIndex] = cost[next];
      cost[next] = temp;

      arrIndex = next;
    }
    return offset;
  }

  private static int[] getFromSort(int[] node, int[] cost, int offset) {
    int arrIndex = 1;
    int[] get = new int[2];
    get[0] = node[arrIndex];
    get[1] = cost[arrIndex];

    cost[arrIndex] = cost[--offset];
    node[arrIndex] = node[offset];
    while (arrIndex * 2 < offset) {
      int next = arrIndex * 2;
      if (next + 1 < offset) {
        if (cost[next] > cost[next + 1]) {
          next += 1;
        }
      }

      if (cost[arrIndex] < cost[next]) {
        break;
      }

      int temp = node[arrIndex];
      node[arrIndex] = node[next];
      node[next] = temp;

      temp = cost[arrIndex];
      cost[arrIndex] = cost[next];
      cost[next] = temp;

      arrIndex = next;
    }

    return get;
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