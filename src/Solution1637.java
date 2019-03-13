import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Solution1637 {

  public static void main(String[] args) throws IOException, URISyntaxException {
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    final int N = EasyReader.readInt(context);

    NumberHolder numberPool = new NumberHolder(N);
    for (int n = 0; n < N; ++n) {
      int A = EasyReader.readInt(context);
      int C = EasyReader.readInt(context);
      int B = EasyReader.readInt(context);

      NumberStruct numberInfo = new NumberStruct();
      numberInfo.A = A;
      numberInfo.B = B;
      numberInfo.C = C;

      numberPool.add(numberInfo);
    }

    long minIndex = 0;
    long maxIndex = Integer.MAX_VALUE;
    long count = 0;
    while (minIndex < maxIndex) {
      long midIndex = (minIndex + maxIndex) / 2;
      count = count(midIndex, numberPool);
      if (count % 2 == 0) {
        minIndex = midIndex + 1;
      } else {
        maxIndex = midIndex;
      }
    }
    long answer = maxIndex;
    if (count % 2 == 0) {
      count = count(maxIndex, numberPool) - count(maxIndex - 1, numberPool);
    } else {
      count = count - count(maxIndex - 1, numberPool);
    }

    if (count % 2 == 0) {
      System.out.println("NOTHING");
    } else {
      System.out.println(answer + " " + count);
    }
  }

  private static long count(long index, NumberHolder numbers) {
    long count = 0;
    Iterator<NumberStruct> numIter = numbers.iterator();
    while (numIter.hasNext()) {
      NumberStruct number = numIter.next();
      count += number.count(index);
    }
    return count;
  }

  public static class NumberStruct {
    int A;
    int B;
    int C;

    public long count(long index) {
      if (A > index) {
        return 0;
      }
      return ((Math.min(C, index) - A) / B) + 1;
    }
  }

  public static class NumberHolder {
    List<NumberStruct> array;

    public NumberHolder(int n) {
      array = new ArrayList<NumberStruct>(n);
    }

    public void add(NumberStruct numberInfo) {
      array.add(numberInfo);
    }

    public Iterator<NumberStruct> iterator() {
      return array.iterator();
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
