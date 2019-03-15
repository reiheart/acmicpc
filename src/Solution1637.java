import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
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

  private static String getTestString() {
    // input sample
    // 4
    // 1 10 1
    // 4 4 1
    // 1 5 1
    // 6 10 1
    // answer
    // 4 3
    return "4\n" +
        "1074000001 1074000010 1\n" +
        "1074000005 1074000005 1\n" +
        "1074000001 1074000005 1\n" +
        "1074000006 1074000010 1";

    // Random rand = new Random(System.currentTimeMillis());
    // StringBuffer randomCase = new StringBuffer();
    // int N = 100;
    // randomCase.append(N).append("\n");
    // for (int i = 0; i < N; ++i) {
    // int A_C = Math.abs(rand.nextInt(10000));
    // int B = Math.abs(rand.nextInt(300));
    // int BB = Math.abs(rand.nextInt(300));
    // int C_A = Math.abs(rand.nextInt(2147483647));
    // int CC_A = Math.abs(rand.nextInt(2147483647));
    // int A = Math.min(Math.min(A_C, C_A), CC_A);
    // int C = Math.max(Math.max(A_C, C_A), CC_A);
    // int CC = A == A_C ? (C == C_A ? CC_A : C_A) : (A == C_A ? (C == A_C ? CC_A : A_C) : (C == A_C ? C_A : A_C));
    // randomCase.append(A).append(" ").append(C).append(" ").append(Math.max(1, B)).append("\n");
    // randomCase.append(A).append(" ").append(CC).append(" ").append(Math.max(1, BB)).append("\n");
    // }
    // System.out.println(randomCase.toString());
    // return randomCase.toString();

    // StringBuffer wCase = new StringBuffer();
    // int N = 200;
    // wCase.append(N).append("\n");
    // for (int i = 0; i < N; ++i) {
    // wCase.append(1).append(" ").append(214748).append(" ").append(1).append("\n");
    // }
    // return wCase.toString();
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

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
    System.out.println(answer + "=>" + count(maxIndex - 1, numberPool) + "," + count + "/" + count(maxIndex, numberPool)
        + "," + count(maxIndex + 1, numberPool));
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
    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  private static long count(long midIndex, NumberHolder numbers) {
    long count = 0;
    Iterator<NumberStruct> numIter = numbers.iterator();
    while (numIter.hasNext()) {
      NumberStruct number = numIter.next();
      count += number.count(midIndex);
    }
    return count;
  }

  public static class NumberStruct {
    int A;
    int B;
    int C;

    public long count(long midIndex) {
      if (A > midIndex) {
        return 0;
      }
      return ((Math.min(C, midIndex) - A) / B) + 1;
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