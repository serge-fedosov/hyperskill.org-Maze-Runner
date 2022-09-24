package maze;

import java.util.*;


public class Main {

    public static void main(String[] args) {

        String MENU = "\n=== Menu ===\n" +
                "1. Generate a new maze.\n" +
                "2. Load a maze.\n" +
                "0. Exit.";

        String MENU_FULL = "\n=== Menu ===\n" +
                "1. Generate a new maze.\n" +
                "2. Load a maze.\n" +
                "3. Save the maze.\n" +
                "4. Display the maze.\n" +
                "5. Find the escape.\n" +
                "0. Exit.";

        String INCORRECT_COMMAND = "Incorrect option. Please try again";

        Scanner scanner = new Scanner(System.in);
        Maze maze = new Maze();

        while (true) {

            if (maze.isMaze()) {
                System.out.println(MENU_FULL);
            } else {
                System.out.println(MENU);
            }

            int command = Integer.parseInt(scanner.nextLine());
            if (!maze.isMaze() && (command == 3 || command == 4 || command == 5)) {
                System.out.println(INCORRECT_COMMAND);
                continue;
            }

            switch (command) {
                case 1:
                    System.out.println("Please, enter the size of a maze");
                    int n = Integer.parseInt(scanner.nextLine());
                    maze.generate(n, n);
                    maze.show();
                    break;
                case 2:
                    String loadFile = scanner.nextLine();
                    maze.load(loadFile);
                    break;
                case 3:
                    String saveFile = scanner.nextLine();
                    maze.save(saveFile);
                    break;
                case 4:
                    maze.show();
                    break;
                case 5:
                    maze.findEscape();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(INCORRECT_COMMAND);
                    break;
            }
        }
    }
}
