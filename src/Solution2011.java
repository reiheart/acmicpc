import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Solution2011 {

  private static String getTestString() {
    // input sample
    // 25114
    // answer
    // 6
    return "010001";
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final String N = EasyReader.readString(context);

    Map<String, Integer> memo = new HashMap<String, Integer>();

    System.out.println(count(N, 0, 0, memo));
    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  private static int count(String str, int offset, int preValue, Map<String, Integer> memo) {
    if (offset >= str.length()) {
      return 1;
    }

    Integer memoCount = memo.get(offset + "/" + preValue);
    if (memoCount != null) {
      return memoCount.intValue();
    }

    char c = str.charAt(offset);
    int count = 0;
    if (c - '0' == 0) {
      if (preValue >= 10) {
        memo.put(offset + "/" + preValue, 0);
        return 0;
      }
    } else {
      count += count(str, offset + 1, c - '0', memo);
    }

    if (preValue == 1) {
      count += count(str, offset + 1, c - '0' + 10, memo);
    } else if (preValue == 2) {
      if (c - '0' <= 6) {
        count += count(str, offset + 1, c - '0' + 20, memo);
      }
    }

    while (count >= 1000000) {
      count -= 1000000;
    }
    memo.put(offset + "/" + preValue, count);
    return count;
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

    public static double readDouble(ReadingContext context) throws IOException {
      ensureRead(context);

      return Double.parseDouble(context.separatedLine[context.offSet++]);
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