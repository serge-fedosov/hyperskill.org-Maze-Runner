package maze;

public class Main {
    public static void main(String[] args) {
        String EMPTY_CELL = "  ";
        String WALL_CELL = "\u2588\u2588";

        int[][] maze = new int[10][10];
        maze[0] = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        maze[1] = new int[] {0, 0, 1, 0, 1, 0, 1, 0, 0, 1};
        maze[2] = new int[] {1, 0, 1, 0, 0, 0, 1, 0, 1, 1};
        maze[3] = new int[] {1, 0, 0, 0, 1, 1, 1, 0, 0, 0};
        maze[4] = new int[] {1, 0, 1, 0, 0, 0, 0, 0, 1, 1};
        maze[5] = new int[] {1, 0, 1, 0, 1, 1, 1, 0, 1, 1};
        maze[6] = new int[] {1, 0, 1, 0, 1, 0, 0, 0, 1, 1};
        maze[7] = new int[] {1, 0, 1, 0, 1, 1, 1, 0, 1, 1};
        maze[8] = new int[] {1, 0, 1, 0, 0, 0, 1, 0, 0, 1};
        maze[9] = new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (maze[i][j] == 0) {
                    System.out.print(EMPTY_CELL);
                } else {
                    System.out.print(WALL_CELL);
                }
            }
            System.out.println();
        }
    }
}
