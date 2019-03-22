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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Solution2146 {

  private static String getTestString() {
    // input sample
    // 10
    // 1 1 1 0 0 0 0 1 1 1
    // 1 1 1 1 0 0 0 0 1 1
    // 1 0 1 1 0 0 0 0 1 1
    // 0 0 1 1 1 0 0 0 0 1
    // 0 0 0 1 0 0 0 0 0 1
    // 0 0 0 0 0 0 0 0 0 1
    // 0 0 0 0 0 0 0 0 0 0
    // 0 0 0 0 1 1 0 0 0 0
    // 0 0 0 0 1 1 1 0 0 0
    // 0 0 0 0 0 0 0 0 0 0
    // answer
    // 3
    // return "10\n" +
    // "1 1 1 0 0 0 0 1 1 1\n" +
    // "1 1 1 1 0 0 0 0 1 1\n" +
    // "1 0 1 1 0 0 0 0 1 1\n" +
    // "0 0 1 1 1 0 0 0 0 1\n" +
    // "0 0 0 1 0 0 0 0 0 1\n" +
    // "0 0 0 0 0 0 0 0 0 1\n" +
    // "0 0 0 0 0 0 0 0 0 0\n" +
    // "0 0 0 0 1 1 0 0 0 0\n" +
    // "0 0 0 0 1 1 1 0 0 0\n" +
    // "0 0 0 0 0 0 0 0 0 0";
    // return "10\n" +
    // "1 1 1 0 0 0 0 0 0 0\n" +
    // "1 0 1 0 0 0 0 0 0 0\n" +
    // "0 0 1 0 0 0 0 0 0 0\n" +
    // "0 0 1 0 0 0 0 0 0 0\n" +
    // "0 0 1 0 0 0 0 0 0 0\n" +
    // "0 0 1 0 0 0 0 1 0 0\n" +
    // "0 0 0 0 0 0 1 1 0 0\n" +
    // "0 0 0 0 1 0 1 0 0 0\n" +
    // "0 0 0 0 1 1 1 0 0 0\n" +
    // "0 0 0 0 0 0 0 0 0 0";
    // ansewer
    // 3
    // return "10\n" +
    // "1 1 1 0 0 0 0 1 0 0\n" +
    // "1 0 1 0 0 0 0 0 0 0\n" +
    // "0 0 1 0 0 0 1 1 0 0\n" +
    // "0 0 1 0 0 0 1 0 0 0\n" +
    // "0 0 1 1 1 1 1 0 0 0\n" +
    // "0 0 1 0 0 0 0 0 0 0\n" +
    // "0 0 0 0 0 0 0 1 0 0\n" +
    // "0 0 0 0 1 0 1 1 0 0\n" +
    // "0 0 0 0 1 1 1 0 0 0\n" +
    // "0 0 0 0 0 0 0 0 0 0";
    // ansewer
    // 1
    // return "4\n" +
    // "1 1 0 0\n" +
    // "0 0 0 1\n" +
    // "0 1 1 1\n" +
    // "0 0 0 0";
    // answer
    // 1
    return "30\n" +
    "1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "1 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "1 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "1 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "1 0 0 1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "1 0 1 1 1 1 1 0 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "1 0 0 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "1 1 1 1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0\n" +
    "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1";
    // return "4\n" +
    // "1 1 1 1\n" +
    // "1 1 0 1\n" +
    // "1 0 0 1\n" +
    // "1 1 1 1";
    // return "5\n" +
    // "1 0 0 0 1\n" +
    // "0 0 0 0 0\n" +
    // "0 0 0 0 0\n" +
    // "0 0 0 0 0\n" +
    // "1 1 0 0 1";
    //    return "4\n" +
    //    "1 0 0 1\n" +
    //    "0 0 0 0\n" +
    //    "1 0 1 0\n" +
    //    "0 0 0 0";
    // return "5\n" +
    // "1 0 0 0 0\n" +
    // "1 0 0 0 1\n" +
    // "1 1 1 0 1\n" +
    // "0 0 0 0 0\n" +
    // "0 0 0 1 0";
    // return "5\n" +
    // "1 0 0 0 0\n" +
    // "0 0 0 0 0\n" +
    // "0 0 1 0 0\n" +
    // "0 0 0 0 1\n" +
    // "0 0 0 0 1";
  }

  public static void main(String[] args) throws IOException, URISyntaxException {
    // EasyReader.ReadingContext context = new EasyReader.ReadingContext(System.in);

    String testStr = getTestString();
    ByteArrayInputStream bis = new ByteArrayInputStream(testStr.getBytes());
    EasyReader.ReadingContext context = new EasyReader.ReadingContext(bis);

    long st = System.nanoTime();

    final int N = EasyReader.readInt(context);
    int[][] map = new int[N][N];
    for (int n = 0; n < N; ++n) {
      for (int nn = 0; nn < N; ++nn) {
        map[n][nn] = EasyReader.readInt(context);
      }
    }
    SolutionGraph graph = new SolutionGraph();
    graph.build(map);

    System.out.println(graph.getMinPath());
    long dur = System.nanoTime() - st;
    System.out.println(dur + " nsec" + " / " + (dur / 1000000) + " msec");
  }

  public static class SolutionGraph {
    public static class MergedPair {
      int i;
      int j;

      public MergedPair(int i, int j) {
        this.i = Math.min(i, j);
        this.j = Math.max(i, j);
      }

      @Override
      public boolean equals(Object o) {
        if (o instanceof MergedPair) {
          return i == ((MergedPair) o).i && j == ((MergedPair) o).j;
        }
        return super.equals(o);
      }

      @Override
      public int hashCode() {
        return (i + "-" + j).hashCode();
      }
    }

    Map<Integer, Set<Integer>> graph;
    int groundID = -1;
    int nodeID = 2;
    Set<MergedPair> mergedGround;

    public SolutionGraph() {
      graph = new HashMap<Integer, Set<Integer>>();
      mergedGround = new HashSet<MergedPair>();
    }

    public void build(int[][] map) {
      for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map[i].length; ++j) {
          if (map[i][j] == 1) {
            if (i - 1 >= 0 && map[i - 1][j] < 0) {
              map[i][j] = map[i - 1][j];
            } else if (j - 1 >= 0 && map[i][j - 1] < 0) {
              map[i][j] = map[i][j - 1];
            } else {
              map[i][j] = groundID--;
            }

            if (i - 1 >= 0 && map[i - 1][j] != map[i][j]) {
              if (map[i - 1][j] < 0) {
                mergeGround(map[i - 1][j], map[i][j]);
              } else {
                addLink(map[i - 1][j], map[i][j]);
              }
            }
            if (j - 1 >= 0 && map[i][j - 1] != map[i][j]) {
              if (map[i][j - 1] < 0) {
                mergeGround(map[i][j - 1], map[i][j]);
              } else {
                addLink(map[i][j - 1], map[i][j]);
              }
            }
          } else if (map[i][j] == 0) {
            map[i][j] = nodeID++;
            if (i - 1 >= 0) {
              addLink(map[i - 1][j], map[i][j]);
            }
            if (j - 1 >= 0) {
              addLink(map[i][j - 1], map[i][j]);
            }
          }
        }
      }
    }

    private void mergeGround(int i, int j) {
      Set<MergedPair> addedMerge = new HashSet<MergedPair>();
      Iterator<MergedPair> iterator = mergedGround.iterator();
      while (iterator.hasNext()) {
        MergedPair pair = iterator.next();
        if (pair.i == j) {
          addedMerge.add(new MergedPair(i, pair.j));
        }
        if (pair.i == i) {
          addedMerge.add(new MergedPair(j, pair.j));
        }
        if (pair.j == i) {
          addedMerge.add(new MergedPair(j, pair.i));
        }
        if (pair.j == j) {
          addedMerge.add(new MergedPair(i, pair.i));
        }
      }
      mergedGround.add(new MergedPair(i, j));
      mergedGround.addAll(addedMerge);
    }

    private void addLink(int i, int j) {
      addLink0(i, j);
      addLink0(j, i);
    }

    private void addLink0(int i, int j) {
      Set<Integer> set = graph.get(i);
      if (set == null) {
        set = new HashSet<Integer>();
        graph.put(i, set);
      }
      set.add(j);
    }

    public int getMinPath() {
      int min = Integer.MAX_VALUE;
      boolean[] visit = new boolean[nodeID - groundID];
      int[] hop = new int[nodeID - groundID];
      int[] queue = new int[20000];
      int front = 0;
      int reer = 0;
      for (int i = groundID + 1; i < 0; ++i) {
        // push
        queue[reer++] = i;
        if (reer >= 20000) {
          reer = 0;
        }
        hop[nodeID - i] = 0;
        while (front != reer) {
          // pop
          int pop = queue[front++];
          if (front >= 20000) {
            front = 0;
          }
          if (visit[nodeID - pop]) {
            continue;
          }
          visit[nodeID - pop] = true;

          Set<Integer> set = graph.get(pop);
          if (set != null) {
            Iterator<Integer> iterator = set.iterator();
            while (iterator.hasNext()) {
              int c = iterator.next();
              if (visit[nodeID - c]) {
                continue;
              }
              if (c < 0) {
                if (mergedGround.contains(new MergedPair(i, c))) {
                  continue;
                } else {
                  min = Math.min(hop[nodeID - pop], min);
                  front = 0;
                  reer = 0;
                  break;
                }
              }
              // push
              queue[reer++] = c;
              if (reer >= 20000) {
                reer = 0;
              }
              hop[nodeID - c] = hop[nodeID - pop] + 1;
            }
          }
        }

        front = 0;
        reer = 0;

        Arrays.fill(visit, false);
      }
      return min != Integer.MAX_VALUE ? min : 0;
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