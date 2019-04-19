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

public class Solution4198 {

  private static String getTestString() {
    // input sample
    // 3
    // 1
    // 2
    // 3
    // answer
    // 3
    // return "3\n" +
    // "1\n" +
    // "2\n" +
    // "3";
    // input sample
    //    7
    //    8
    //    3
    //    2
    //    1
    //    6
    //    9
    //    7
    // answer
    // 5
    // return "7\n" +
    // "8\n" +
    // " 3\n" +
    // "2\n" +
    // "1\n" +
    // "6\n" +
    // "9\n" +
    // "7";
    return "10\n" +
    "1\n" +
    "2\n" +
    "3\n" +
    "9\n" +
    "6\n" +
    "7\n" +
    "13\n" +
    "21\n" +
    "4\n" +
    "8";

  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final int N = EasyReader.readInt(context);
    if (N == 0) {
      System.out.println("0");
      return;
    }
    int weight = EasyReader.readInt(context);

    SortedTrain sortedTrain = new SortedTrain(weight);
    for (int n = 1; n < N; ++n) {
      weight = EasyReader.readInt(context);
      sortedTrain.add(weight, n);
    }

    System.out.println(sortedTrain.size());

    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  public static class SortedTrain {
    static class Train {
      public Train(int value, int i) {
        weight = value;
        index = i;
      }
      int weight;
      int index;
    }

    int zeroWeight;
    PriorityQueue<Train> left;
    PriorityQueue<Train> right;

    public SortedTrain(int initValue) {
      Comparator<Train> comparator = new Comparator<Train>() {
        @Override
        public int compare(Train o1, Train o2) {
          if (o1.weight < o2.weight) {
            return -1;
          }
          if (o1.weight > o2.weight) {
            return 1;
          }
          return 0;
        }
      };
      left = new PriorityQueue<Train>(comparator);
      right = new PriorityQueue<Train>(comparator);
    }

    public int size() {
      int rightSize = 0;
      int leftSize = 0;

      return leftSize + rightSize - 1;
    }

    public void add(int value, int index) {
      if (value > zeroWeight) {
        right.add(new Train(value, index));
      } else {
        left.add(new Train(value, index));
      }
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