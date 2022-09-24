package maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Maze {

    static int INF = Integer.MAX_VALUE; // значение "бесконечность"
    static String MAZE_FORMAT_ERROR = "Cannot load the maze. It has an invalid format";
    int[][] maze = null;

    public List<Edge> prim(int n, int[][] g) {
        // входные данные
//        int n = 12;
//        int INF = Integer.MAX_VALUE; // значение "бесконечность"
//        int[][] g = new int[n][n];

        List<Edge> edges = new ArrayList<>();

        boolean[] used = new boolean[n];

        int[] minE = new int[n];
        Arrays.fill(minE, INF);

        int[] selE = new int[n];
        Arrays.fill(selE, -1);

        minE[0] = 0;
        for (int i = 0; i < n; i++) {
            int v = -1;
            for (int j = 0; j < n; j++) {
                if (!used[j] && (v == -1 || minE[j] < minE[v])) {
                    v = j;
                }
            }

            if (minE[v] == INF) {
                System.out.println("No MST!");
                System.exit(0);
            }

            used[v] = true;
            if (selE[v] != -1) {
//                System.out.println(v + " " + selE[v]);
                if (v <= selE[v]) {
                    edges.add(new Edge(v, selE[v]));
                } else {
                    edges.add(new Edge(selE[v], v));
                }
            }

            for (int to=0; to<n; ++to) {
                if (g[v][to] < minE[to]) {
                    minE[to] = g[v][to];
                    selE[to] = v;
                }
            }
        }

        return edges;
    }

    public void generate(int height, int width) {

        int h = (height - 1) / 2;
        int w = (width - 1) / 2;

        int n = h * w;
        int[][] g = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    g[i][j] = 0;
                } else {
                    g[i][j] = INF;
                }
            }
        }

        Random random = new Random();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int k = i * w + j;
                int l = (i + 1) * w + j;

                int r = random.nextInt(2) + 1;
                if (j != w - 1) {
                    g[k][k + 1] = r;
                    g[k + 1][k] = r;
                }

                r = random.nextInt(2) + 1;
                if (i != h - 1) {
                    g[k][l] = r;
                    g[l][k] = r;
                }
            }
        }

        List<Edge> edges = prim(n, g);

//        int[][] maze = new int[height][width];
        maze = new int[height][width];

        // walls around the maze
        for (int i = 0; i < height; i++) {
            maze[i][0] = -1;
            maze[i][width - 1] = -1;
        }

        for (int i = 0; i < width; i++) {
            maze[0][i] = -1;
            maze[height - 1][i] = -1;
        }

        // maze
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int k = i * w + j;
                int l = (i + 1) * w + j;

//                int x0 = 2 * j + 1;
//                int y0 = 2 * i + 1;
//                maze[y0][x0] = 2;

                if (!edges.contains(new Edge(k, k + 1))) {
                    int x = 2 * j + 2;
                    int y = 2 * i;
                    maze[y][x] = -1;

                    y++;
                    maze[y][x] = -1;

                    y++;
                    maze[y][x] = -1;
                }

                if (!edges.contains(new Edge(k, l))) {
                    int x = 2 * j;
                    int y = 2 * i + 2;
                    maze[y][x] = -1;

                    x++;
                    maze[y][x] = -1;

                    x++;
                    maze[y][x] = -1;
                }

            }
        }

        // entrance and exit
        maze[1][0] = 0;
        maze[maze.length - 3][0] = 0;
    }

    public boolean isMaze() {
        return maze != null;
    }

    public void save(String saveFile) {
        File file = new File(saveFile);

        try (PrintWriter printWriter = new PrintWriter(file)) {
            int height = maze.length;
            int width = maze[0].length;

            printWriter.println(height + " " + width);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    printWriter.print(maze[i][j] == 0 ? "0" : "1");
                }
                printWriter.println();
            }
        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
        }
    }

    public void load(String loadFile) {
        File file = new File(loadFile);

        try (Scanner scanner = new Scanner(file)) {
            if (!scanner.hasNextLine()) {
                System.out.println(MAZE_FORMAT_ERROR);
                maze = null;
                return;
            }

            String[] arr = scanner.nextLine().split(" ");
            int height = Integer.parseInt(arr[0]);
            int width = Integer.parseInt(arr[1]);
            maze = new int[height][width];

            for (int i = 0; i < height; i++) {
                if (!scanner.hasNextLine()) {
                    System.out.println(MAZE_FORMAT_ERROR);
                    maze = null;
                    return;
                }

                String line = scanner.nextLine();
                for (int j = 0; j < line.length(); j++) {
                    if (line.charAt(j) == '1') {
                        maze[i][j] = -1;
                    } else {
                        maze[i][j] = 0;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("The file " + loadFile + " does not exist");
        }
    }

    public void findEscape() {
        int height = maze.length;
        int width = maze[0].length;

        int[][] maze2 = new int[height][width];
        int[][] maze3 = new int[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (maze[i][j] == 0) {
                    maze2[i][j] = 0;
                    maze3[i][j] = 0;
                } else {
                    maze2[i][j] = -1;
                    maze3[i][j] = -1;
                }
            }
        }


        List<Edge> edges = new ArrayList<>();

        for (int i = 0; i < height; i++) {
            if (maze2[i][0] == 0) {
                edges.add(new Edge(0, i));
            }

            if (maze2[i][width - 1] == 0) {
                edges.add(new Edge(width - 1, i));
            }
        }

        for (int i = 1; i < width - 1; i++) {
            if (maze2[0][i] == 0) {
                edges.add(new Edge(i, 0));
            }

            if (maze2[height - 1][i] == 0) {
                edges.add(new Edge(i, height - 1));
            }
        }

        if (edges.size() < 2) {
            System.out.println("In (out) not found");
            return;
        }

        dfs(maze2, edges.get(0).getX(), edges.get(0).getY(), 1);
        //show(maze2);

        int x = edges.get(1).getX();
        int y = edges.get(1).getY();
        int v = maze2[y][x];

        int minX = x;
        int minY = y;
        int minV = v;
        maze3[minY][minX] = -2;

        while (v != 1) {
            if (x - 1 >= 0) {
                if (maze2[y][x - 1] > 0 && maze2[y][x - 1] < minV) {
                    minV = maze2[y][x - 1];
                    minX = x - 1;
                    minY = y;
                }
            }

            if (x + 1 < width) {
                if (maze2[y][x + 1] > 0 && maze2[y][x + 1] < minV) {
                    minV = maze2[y][x + 1];
                    minX = x + 1;
                    minY = y;
                }
            }

            if (y - 1 >= 0) {
                if (maze2[y - 1][x] > 0 && maze2[y - 1][x] < minV) {
                    minV = maze2[y - 1][x];
                    minX = x;
                    minY = y - 1;
                }
            }

            if (y + 1 < height) {
                if (maze2[y + 1][x] > 0 && maze2[y + 1][x] < minV) {
                    minV = maze2[y + 1][x];
                    minX = x;
                    minY = y + 1;
                }
            }

            maze3[minY][minX] = -2;
            x = minX;
            y = minY;
            v = minV;
        }

        show(maze3);
    }


    public void dfs(int[][] maze2, int x, int y, int step) {
        maze2[y][x] = step;
        step++;

        int h = maze2.length;
        int w = maze2[0].length;

        if (x - 1 >= 0) {
            if (maze2[y][x - 1] == 0) {
                dfs(maze2, x - 1, y, step);
            }
        }

        if (x + 1 < w) {
            if (maze2[y][x + 1] == 0) {
                dfs(maze2, x + 1, y, step);
            }
        }

        if (y - 1 >= 0) {
            if (maze2[y - 1][x] == 0) {
                dfs(maze2, x, y - 1, step);
            }
        }

        if (y + 1 < h) {
            if (maze2[y + 1][x] == 0) {
                dfs(maze2, x, y + 1, step);
            }
        }
    }

    public void show(int[][] maze) {
        String EMPTY_CELL = "  ";
        String WALL_CELL = "\u2588\u2588";
        String PATH_CELL = "//";

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 0) {
                    System.out.print(EMPTY_CELL);
                } else if (maze[i][j] == -1) {
                    System.out.print(WALL_CELL);
                } else if (maze[i][j] == -2) {
                    System.out.print(PATH_CELL);
                } else {
                    System.out.printf("%2d", maze[i][j]);
                }
            }
            System.out.println();
        }
    }

    public void show() {
        show(maze);
    }

}
