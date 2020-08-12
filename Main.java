package one;

import java.util.Scanner;

/**
 * Created by Parham on 05-Jun-19.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] input;
        Algorithm algorithm = new Algorithm();
        Problem problem;
        boolean reached;
        String[] l0, l1, l2;
        while (true) {
            try {
                System.out.println("Initial state(manual/random/default) Search type(bfs/dfs-normal/dfs-limited-#/dfs-iterative/bidirectional/uniform-cost/a-star)");
                input = scanner.nextLine().toLowerCase().split(" ");
                switch (input[0]) {
                    case "exit":
                        System.out.println("Goodbye!");
                        System.exit(0);
                        break;
                    case "manual":
                        System.out.println("Input initial state:");
                        try {
                            l0 = scanner.nextLine().split(" ");
                            l1 = scanner.nextLine().split(" ");
                            l2 = scanner.nextLine().split(" ");
                            problem = new Problem(new int[][]{{Integer.parseInt(l0[0]), Integer.parseInt(l0[1]), Integer.parseInt(l0[2])},
                                    {Integer.parseInt(l1[0]), Integer.parseInt(l1[1]), Integer.parseInt(l1[2])},
                                    {Integer.parseInt(l2[0]), Integer.parseInt(l2[1]), Integer.parseInt(l2[2])}}, "-1", "0");
                        } catch (Exception e) {
                            System.out.println("Wrong input!");
                            continue;
                        }
                        switch (input[1]) {
                            case "bfs":
                                for (Problem i : algorithm.breadthFirstSearch(problem)) {
                                    i.printState();
                                }
                                System.out.println("Problem solved!");
                                break;
                            case "dfs-normal":
                                try {
                                    for (Problem i : algorithm.depthLimitedSearch(problem, 57, false)) {
                                        i.printState();
                                    }
                                    System.out.println("Problem solved!");
                                } catch (OutOfMemoryError e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "dfs-iterative":
                                reached = false;
                                for (int j = 0; j < Integer.MAX_VALUE; j++) {
                                    System.out.println("Checking goal in depth: " + j);
                                    try {
                                        for (Problem i : algorithm.depthLimitedSearch(problem, j, true)) {
                                            i.printState();
                                            reached = true;
                                        }
                                    } catch (NullPointerException ignored) {
                                    }
                                    if (reached)
                                        break;
                                }
                                System.out.println("Problem solved!");
                                break;
                            case "bidirectional":
                                for (Problem i : algorithm.bidirectionalSearch(problem)) {
                                    i.printState();
                                }
                                System.out.println("Problem solved!");
                                break;
                            case "uniform-cost":
                                for (Problem i : algorithm.uniformCostSearch(problem)) {
                                    i.printState();
                                }
                                System.out.println("Problem solved!");
                                break;
                            case "a-star":
                                for (Problem i : algorithm.aStar(problem)) {
                                    i.printState();
                                }
                                System.out.println("Problem solved!");
                                break;
                            default:
                                if (input[1].substring(0, 12).equals("dfs-limited-")) {
                                    try {
                                        for (Problem i : algorithm.depthLimitedSearch(problem, Integer.parseInt(input[1].substring(12, input[1].length())), false)) {
                                            i.printState();
                                        }
                                        System.out.println("Problem solved!");
                                    } catch (NullPointerException ignored) {
                                    }
                                } else
                                    System.out.println("Wrong input!");
                        }
                        break;
                    case "random":
                        problem = new Problem();
                        switch (input[1]) {
                            case "bfs":
                                for (Problem i : algorithm.breadthFirstSearch(problem)) {
                                    i.printState();
                                }
                                System.out.println("Problem solved!");
                                break;
                            case "dfs-normal":
                                try {
                                    for (Problem i : algorithm.depthLimitedSearch(problem, 57, false)) {
                                        i.printState();
                                    }
                                    System.out.println("Problem solved!");
                                } catch (OutOfMemoryError e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "dfs-iterative":
                                reached = false;
                                for (int j = 0; j < Integer.MAX_VALUE; j++) {
                                    System.out.println("Checking goal in depth: " + j);
                                    try {
                                        for (Problem i : algorithm.depthLimitedSearch(problem, j, true)) {
                                            i.printState();
                                            reached = true;
                                        }
                                    } catch (NullPointerException ignored) {
                                    }
                                    if (reached)
                                        break;
                                }
                                System.out.println("Problem solved!");
                                break;
                            case "bidirectional":
                                for (Problem i : algorithm.bidirectionalSearch(problem)) {
                                    i.printState();
                                }
                                System.out.println("Problem solved!");
                                break;
                            case "uniform-cost":
                                for (Problem i : algorithm.uniformCostSearch(problem)) {
                                    i.printState();
                                }
                                System.out.println("Problem solved!");
                                break;
                            case "a-star":
                                for (Problem i : algorithm.aStar(problem)) {
                                    i.printState();
                                }
                                System.out.println("Problem solved!");
                                break;
                            default:
                                if (input[1].substring(0, 12).equals("dfs-limited-")) {
                                    try {
                                        for (Problem i : algorithm.depthLimitedSearch(problem, Integer.parseInt(input[1].substring(12, input[1].length())), false)) {
                                            i.printState();
                                        }
                                        System.out.println("Problem solved!");
                                    } catch (NullPointerException ignored) {
                                    }
                                } else
                                    System.out.println("Wrong input!");
                        }
                        break;
                    case "default":
                        problem = new Problem(new int[][]{{1, 2, 3}, {5, 6, 0}, {4, 7, 8}}, "-1", "0");
                        switch (input[1]) {
                            case "bfs":
                                for (Problem i : algorithm.breadthFirstSearch(problem)) {
                                    i.printState();
                                }
                                System.out.println("Problem solved!");
                                break;
                            case "dfs-normal":
                                try {
                                    for (Problem i : algorithm.depthLimitedSearch(problem, 31, false)) {
                                        i.printState();
                                    }
                                    System.out.println("Problem solved!");
                                } catch (OutOfMemoryError e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "dfs-iterative":
                                reached = false;
                                for (int j = 0; j < Integer.MAX_VALUE; j++) {
                                    System.out.println("Checking goal in depth: " + j);
                                    try {
                                        for (Problem i : algorithm.depthLimitedSearch(problem, j, true)) {
                                            i.printState();
                                            reached = true;
                                        }
                                    } catch (NullPointerException ignored) {
                                    }
                                    if (reached)
                                        break;
                                }
                                System.out.println("Problem solved!");
                                break;
                            case "bidirectional":
                                for (Problem i : algorithm.bidirectionalSearch(problem)) {
                                    i.printState();
                                }
                                System.out.println("Problem solved!");
                                break;
                            case "uniform-cost":
                                for (Problem i : algorithm.uniformCostSearch(problem)) {
                                    i.printState();
                                }
                                System.out.println("Problem solved!");
                                break;
                            case "a-star":
                                for (Problem i : algorithm.aStar(problem)) {
                                    i.printState();
                                }
                                System.out.println("Problem solved!");
                                break;
                            default:
                                if (input[1].length() > 12 && input[1].substring(0, 12).equals("dfs-limited-")) {
                                    try {
                                        for (Problem i : algorithm.depthLimitedSearch(problem, Integer.parseInt(input[1].substring(12, input[1].length())), false)) {
                                            i.printState();
                                        }
                                        System.out.println("Problem solved!");
                                    } catch (NullPointerException ignored) {
                                    }
                                } else
                                    System.out.println("Wrong input!");
                        }
                        break;
                    default:
                        System.out.println("Wrong input!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println();
            }
        }
    }
}
