import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Solution5427 {

  private static String getTestString() {
    // input sample
    // 5
    // 4 3
    // ####
    // #*@.
    // ####
    // 7 6
    // ###.###
    // #*#.#*#
    // #.....#
    // #.....#
    // #..@..#
    // #######
    // 7 4
    // ###.###
    // #....*#
    // #@....#
    // .######
    // 5 5
    // .....
    // .***.
    // .*@*.
    // .***.
    // .....
    // 3 3
    // ###
    // #@#
    // ###
    // answer
    // 2
    // 5
    // IMPOSSIBLE
    // IMPOSSIBLE
    // IMPOSSIBLE
    return "7\n" +
    "4 3\n" +
    "####\n" +
    "#*@.\n" +
    "####\n" +
    "7 6\n" +
    "###.###\n" +
    "#*#.#*#\n" +
    "#.....#\n" +
    "#.....#\n" +
    "#..@..#\n" +
    "#######\n" +
    "7 4\n" +
    "###.###\n" +
    "#....*#\n" +
    "#@....#\n" +
    ".######\n" +
    "5 5\n" +
    ".....\n" +
    ".***.\n" +
    ".*@*.\n" +
    ".***.\n" +
    ".....\n" +
    "3 3\n" +
    "###\n" +
    "#@#\n" +
    "###\n" +
    "5 5\n" +
    "..#.#\n" +
    "....#\n" +
    "#*#..\n" +
    "#.@.#\n" +
    "###..\n" +
    "10 7\n" +
    "##########\n" +
    "#@.......#\n" +
    "#........#\n" +
    "#........#\n" +
    "#........#\n" +
    "#........#\n" +
    "#........#";

  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    // String testStr = getTestString();
    // ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    URL resource = Solution5427.class.getClassLoader().getResource("Sample5427.in");
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(Paths.get(resource.toURI()));

    final int T = EasyReader.readInt(context);
    char[] map = new char[1000000];
    boolean[] fVisit = new boolean[1000000];
    boolean[] hVisit = new boolean[1000000];
    Deque<Integer> fireNodes = new ArrayDeque<Integer>();
    Deque<Integer> human = new ArrayDeque<Integer>();
    for (int t = 0; t < T; ++t) {
      long st = System.nanoTime();

      int W = EasyReader.readInt(context);
      int H = EasyReader.readInt(context);
      fireNodes.clear();
      human.clear();
      for (int h = 0; h < H; ++h) {
        String line = EasyReader.read(context);
        for (int w = 0; w < W; ++w) {
          char atlas = line.charAt(w);
          int nodeNum = (h * W) + w;
          map[nodeNum] = atlas;
          if (atlas == '#') {
            continue;
          }
          switch (atlas) {
          case '*':
            fireNodes.add(nodeNum);
            continue;
          case '@':
            human.add(nodeNum);
            continue;
          default:
            break;
          }
        }
      }
      Arrays.fill(fVisit, false);
      Arrays.fill(hVisit, false);
      int count = 0;
      boolean exited = false;
      while (!human.isEmpty()) {
        int fires = fireNodes.size();
        for (int f = 0; f < fires; ++f) {
          int fNode = fireNodes.poll();
          if (fVisit[fNode]) {
            continue;
          }
          fVisit[fNode] = true;
          int w = fNode;
          while (w >= W) {
            w -= W;
          }
          if (w > 0 && !fVisit[fNode - 1] && map[fNode - 1] != '#') {
            map[fNode - 1] = '*';
            fireNodes.add(fNode - 1);
          }
          if (w + 1 < W && !fVisit[fNode + 1] && map[fNode + 1] != '#') {
            map[fNode + 1] = '*';
            fireNodes.add(fNode + 1);
          }
          int h = fNode / W;
          if (h > 0 && !fVisit[fNode - W] && map[fNode - W] != '#') {
            map[fNode - W] = '*';
            fireNodes.add(fNode - W);
          }
          if (h + 1 < H && !fVisit[fNode + W] && map[fNode + W] != '#') {
            map[fNode + W] = '*';
            fireNodes.add(fNode + W);
          }
        }

        int humans = human.size();
        for (int m = 0; m < humans; ++m) {
          int hPoll = human.poll();
          if (hVisit[hPoll]) {
            continue;
          }
          hVisit[hPoll] = true;
          int w = hPoll;
          while (w >= W) {
            w -= W;
          }
          int h = hPoll / W;
          if (h == 0 || h == H - 1 || w == 0 || w == W - 1) {
            exited = true;
            break;
          }
          if (w > 0 && !hVisit[hPoll - 1] && map[hPoll - 1] != '#' && map[hPoll - 1] != '*') {
            human.add(hPoll - 1);
          }
          if (w + 1 < W && !hVisit[hPoll + 1] && map[hPoll + 1] != '#' && map[hPoll + 1] != '*') {
            human.add(hPoll + 1);
          }
          if (h > 0 && !hVisit[hPoll - W] && map[hPoll - W] != '#' && map[hPoll - W] != '*') {
            human.add(hPoll - W);
          }
          if (h + 1 < H && !hVisit[hPoll + W] && map[hPoll + W] != '#' && map[hPoll + W] != '*') {
            human.add(hPoll + W);
          }
        }
        count++;
        if (exited) {
          break;
        }
      }

      if (exited) {
        System.out.println(count);
      } else {
        System.out.println("IMPOSSIBLE");
      }

      long dur = System.nanoTime() - st;
      System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
    }
  }

  private static void printMap(char[] map, int W, int H) {
    int index = 0;
    for (int h = 0; h < H; ++h) {
      for (int w = 0; w < W; ++w) {
        System.out.print(map[index++]);
      }
      System.out.println();
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