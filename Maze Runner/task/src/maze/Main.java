package maze;

import java.util.*;

class Edge {
    private int x;
    private int y;

    public Edge(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        if (x != edge.x) return false;
        return y == edge.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}

public class Main {

    static int INF = Integer.MAX_VALUE; // значение "бесконечность"

    public static List<Edge> prim(int n, int[][] g) {
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
            for (int j = 0; j < n; j++)
                if (!used[j] && (v == -1 || minE[j] < minE[v]))
                    v = j;
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

            for (int to=0; to<n; ++to)
                if (g[v][to] < minE[to]) {
                    minE[to] = g[v][to];
                    selE[to] = v;
                }
        }

        return edges;
    }


    public static void showMaze(int[][] maze) {
        String EMPTY_CELL = "  ";
        String WALL_CELL = "\u2588\u2588";

        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 0) {
                    System.out.print(EMPTY_CELL);
                } else if (maze[i][j] == 1) {
                    System.out.print(WALL_CELL);
                } else {
                    System.out.print("OO");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {


        System.out.println("Please, enter the size of a maze");

        Scanner scanner = new Scanner(System.in);
        int height = scanner.nextInt();
        int width = scanner.nextInt();

//        int height = 7;
//        int width = 9;

        int h = (height - 1) / 2;
        int w = (width - 1) / 2;

        int n = h * w;
        int[][] g = new int[n][n];

//        //                   0    1    2    3    4    5    6    7    8    9   10   11
//        g[0] =  new int[] {  0,   2, INF, INF,   1, INF, INF, INF, INF, INF, INF, INF};
//        g[1] =  new int[] {  2,   0,   2, INF, INF,   1, INF, INF, INF, INF, INF, INF};
//        g[2] =  new int[] {INF,   2,   0,   1, INF, INF,   1, INF, INF, INF, INF, INF};
//        g[3] =  new int[] {INF, INF,   1,   0, INF, INF, INF,   2, INF, INF, INF, INF};
//        g[4] =  new int[] {  1, INF, INF, INF,   0,   1, INF, INF,   2, INF, INF, INF};
//        g[5] =  new int[] {INF,   1, INF, INF,   1,   0,   1, INF, INF,   1, INF, INF};
//        g[6] =  new int[] {INF, INF,   1, INF, INF,   1,   0,   1, INF, INF,   2, INF};
//        g[7] =  new int[] {INF, INF, INF,   2, INF, INF,   1,   0, INF, INF, INF,   2};
//        g[8] =  new int[] {INF, INF, INF, INF,   2, INF, INF, INF,   0,   1, INF, INF};
//        g[9] =  new int[] {INF, INF, INF, INF, INF,   1, INF, INF,   1,   0,   2, INF};
//        g[10] = new int[] {INF, INF, INF, INF, INF, INF,   2, INF, INF,   2,   0,   1};
//        g[11] = new int[] {INF, INF, INF, INF, INF, INF, INF,   2, INF, INF,   1,   0};

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

        int[][] maze = new int[height][width];

        // walls around the maze
        for (int i = 0; i < height; i++) {
            maze[i][0] = 1;
            maze[i][width - 1] = 1;
        }

        for (int i = 0; i < width; i++) {
            maze[0][i] = 1;
            maze[height - 1][i] = 1;
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
                    maze[y][x] = 1;

                    y++;
                    maze[y][x] = 1;

                    y++;
                    maze[y][x] = 1;
                }

                if (!edges.contains(new Edge(k, l))) {
                    int x = 2 * j;
                    int y = 2 * i + 2;
                    maze[y][x] = 1;

                    x++;
                    maze[y][x] = 1;

                    x++;
                    maze[y][x] = 1;
                }

            }
        }

        // entrance and exit
        maze[1][0] = 0;
        maze[3][maze[0].length - 1] = 0;

        showMaze(maze);
    }
}
