import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;

public class EasyReader {
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

  public static String readString(ReadingContext context) throws IOException {
    ensureRead(context);

    return context.separatedLine[context.offSet++];
  }

  public static int readInt(ReadingContext context) throws IOException {
    ensureRead(context);

    return Integer.parseInt(context.separatedLine[context.offSet++]);
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
