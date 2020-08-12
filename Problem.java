package one;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Parham on 05-Jun-19.
 */
public class Problem {
    private String parentId;
    private String id;
    private int[][] puzzle;

    public Problem() {
        puzzle = new int[3][3];
        parentId = "-1";
        id = "0";
        List<Integer> rows = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            rows.add(i);
        }
        Collections.shuffle(rows);
        for (int i = 0; i < 9; i++) {
            puzzle[i / 3][i % 3] = rows.get(i);
        }
        System.out.println("Initial Problem: ");
        printState();
    }

    public Problem(int[][] initialState, String parentId, String id) {
        puzzle = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                puzzle[i][j] = initialState[i][j];
            }
        }
        this.parentId = parentId;
        this.id = id;
    }

    public boolean finalState() {
        for (int i = 0; i < 8; i++) {
            if (puzzle[i / 3][i % 3] != i + 1)
                return false;
        }
        return true;
    }

    public ArrayList<Problem> nextStates() throws OutOfMemoryError {
        ArrayList<Problem> result = new ArrayList<>();
        int[][] temp = new int[3][3];
        int zeroI = -1, zeroJ = -1;
        for (int i = 0; i < 9; i++) {
            if (puzzle[i / 3][i % 3] == 0) {
                zeroI = i / 3;
                zeroJ = i % 3;
                break;
            }
        }
        if (zeroI == -1 || zeroJ == -1) {
            System.out.println("Next Problem Error!");
            printState();
            return null;
        }
        if (zeroJ < 2) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    temp[i][j] = puzzle[i][j];
                }
            }
            temp[zeroI][zeroJ] = temp[zeroI][zeroJ + 1];
            temp[zeroI][zeroJ + 1] = 0;
            result.add(new Problem(temp, id, id + "1"));
        }
        if (zeroJ > 0) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    temp[i][j] = puzzle[i][j];
                }
            }
            temp[zeroI][zeroJ] = temp[zeroI][zeroJ - 1];
            temp[zeroI][zeroJ - 1] = 0;
            result.add(new Problem(temp, id, id + "2"));
        }
        if (zeroI < 2) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    temp[i][j] = puzzle[i][j];
                }
            }
            temp[zeroI][zeroJ] = temp[zeroI + 1][zeroJ];
            temp[zeroI + 1][zeroJ] = 0;
            result.add(new Problem(temp, id, id + "3"));
        }
        if (zeroI > 0) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    temp[i][j] = puzzle[i][j];
                }
            }
            temp[zeroI][zeroJ] = temp[zeroI - 1][zeroJ];
            temp[zeroI - 1][zeroJ] = 0;
            result.add(new Problem(temp, id, id + "4"));
        }
        return result;
    }

    public void printState() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(puzzle[i][j] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public boolean equals(Problem problem) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (problem.getPuzzle()[i][j] != puzzle[i][j])
                    return false;
        return true;
    }

    public int[][] getPuzzle() {
        return puzzle;
    }

    public String getParentId() {
        return parentId;
    }

    public String getId() {
        return id;
    }

    public static Problem getFinalState() {
        return new Problem(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}}, "-2", "!");
    }

    public int getHeuristic() {
        int result = 0;
        int temp;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                temp = puzzle[i][j];
                if (temp == 0)
                    temp = 9;
                result += Math.abs(((temp - 1) / 3) - i) + Math.abs(((temp - 1) % 3) - j);
            }
        }
        return result;
    }

    public int getCost() {
        return 1;
    }
}
