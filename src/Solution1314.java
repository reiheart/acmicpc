import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;

public class Solution1314 {

  private static String getTestString() {
    // input sample
    // 3
    // 1 2
    // 1 3
    // 1 4
    // YYY
    // YYY
    // YYY
    // 2
    // answer
    // 9
    return "3\n" +
    "1 2\n" +
    "1 3\n" +
    "1 4\n" +
    "YYY\n" +
    "YYY\n" +
    "YYY\n" +
    "2";
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final int N = EasyReader.readInt(context);
    int[] weight = new int[N];
    int[] time = new int[N];
    int[] minSort = new int[N + 1];
    int minOffset = 1;
    int[] maxSort = new int[N + 1];
    int maxOffset = 1;
    boolean[][] trustMap = new boolean[N][N];

    for (int n = 0; n < N; ++n) {
      weight[n] = EasyReader.readInt(context);
      time[n] = EasyReader.readInt(context);
      minOffset = addNsort(true, time, minSort, minOffset, n);
      maxOffset = addNsort(false, time, maxSort, maxOffset, n);
    }
    System.out.println(Arrays.toString(minSort));
    System.out.println(Arrays.toString(maxSort));

    for (int n = 0; n < N; ++n) {
      String yn = EasyReader.readString(context);
      for (int nn = 0; nn < N; ++nn) {
        trustMap[n][nn] = yn.charAt(nn) == 'Y';
      }
    }
    final int B = EasyReader.readInt(context);

    int count = 0;
    boolean[] checker = new boolean[N];
    int[] pick = new int[N];
    int[] unpick = new int[N];
    int pickCount = 0;
    int unpickCount = 0;
    int sumWeight = 0;
    int minTime = Integer.MAX_VALUE;
    while (count != N) {
      // pick
      pick[pickCount] = getFromSort(true, time, minSort, minOffset--);
      if (weight[pick[pickCount]] >= B) {
        minTime = -1;
        break;
      }
      sumWeight += weight[pick[pickCount]];
      pickCount += 1;
      checker[pick[pickCount]] = true;
      while (true) {
        pick[pickCount] = getFromSort(false, time, maxSort, maxOffset--);
        if (weight[pick[pickCount]] >= B) {
          minTime = -1;
          break;
        }
        pickCount += 1;
      }

      if (minTime == -1) {
        break;
      }
    }

    System.out.println(minTime);
    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  private static int addNsort(boolean isMinSort, int[] time, int[] sortArray, int offset, int n) {
    int index = offset++;
    sortArray[index] = n;

    while (index / 2 > 0) {
      int next = index / 2;
      if (isMinSort) {
        if (time[sortArray[next]] <= time[sortArray[index]]) {
          break;
        }
      } else {
        if (time[sortArray[next]] >= time[sortArray[index]]) {
          break;
        }
      }
      int temp = sortArray[next];
      sortArray[next] = sortArray[index];
      sortArray[index] = temp;

      index = next;
    }

    return offset;
  }

  private static int getFromSort(boolean isMinSort, int[] time, int[] sortArray, int offset) {
    int index = 1;
    int get = sortArray[index];

    sortArray[index] = sortArray[--offset];
    while (index * 2 < offset) {
      int next = index * 2;
      if ((index * 2) + 1 < offset) {
        if (time[sortArray[index * 2]] > time[sortArray[(index * 2) + 1]]) {
          if (isMinSort) {
            next = (index * 2) + 1;
          }
        } else {
          if (!isMinSort) {
            next = (index * 2) + 1;
          }
        }
        if (time[sortArray[index]] > time[sortArray[next]]) {
          if (!isMinSort) {
            break;
          }
        } else {
          if (isMinSort) {
            break;
          }
        }

        int temp = sortArray[next];
        sortArray[next] = sortArray[index];
        sortArray[index] = temp;

        index = next;
      }
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

    public static String readString(ReadingContext context) throws IOException {
      ensureRead(context);

      return context.separatedLine[context.offSet++];
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