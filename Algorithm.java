package one;

import java.util.*;

/**
 * Created by Parham on 05-Jun-19.
 */
public class Algorithm {
    public ArrayList<Problem> breadthFirstSearch(Problem initialProblem) {
        int maximumNodes = 0;
        ArrayList<Problem> path = new ArrayList<>();
        Problem node = initialProblem;
        Queue<Problem> frontier = new LinkedList<>();
        frontier.add(node);
        path.add(node);
        ArrayList<Problem> explored = new ArrayList<>();
        maximumNodes = Math.max(Math.max(maximumNodes, frontier.size() + explored.size()), solutionFromPath(path).size());
        if (node.finalState()) {
            printReport(frontier.size(), explored.size(), solutionFromPath(path).size(), maximumNodes);
            return solutionFromPath(path);
        }
        while (true) {
            if (frontier.peek() == null) {
                System.out.println("Failure");
                printReport(frontier.size(), explored.size(), solutionFromPath(path).size(), maximumNodes);
                return null;
            }
            node = frontier.remove();
            explored.add(node);
            maximumNodes = Math.max(Math.max(maximumNodes, frontier.size() + explored.size()), solutionFromPath(path).size());
            for (Problem child : node.nextStates()) {
                if (!arrayListContains(explored, child) && !queueContains(frontier, child)) {
                    frontier.add(child);
                    path.add(child);
                    maximumNodes = Math.max(Math.max(maximumNodes, frontier.size() + explored.size()), solutionFromPath(path).size());
                    if (child.finalState()) {
                        printReport(frontier.size(), explored.size(), solutionFromPath(path).size(), maximumNodes);
                        return solutionFromPath(path);
                    }
                }
            }
        }
    }

    private boolean queueContains(Queue<Problem> queue, Problem problem) {
        Problem temp;
        boolean result = false;
        for (int i = 0; i < queue.size(); i++) {
            temp = queue.remove();
            if (temp.equals(problem))
                result = true;
            queue.add(temp);
        }
        return result;
    }

    private boolean uCQueueContains(Queue<UCState> queue, UCState uCState) {
        UCState temp;
        boolean result = false;
        for (int i = 0; i < queue.size(); i++) {
            temp = queue.remove();
            if (temp.problem.equals(uCState.problem))
                result = true;
            queue.add(temp);
        }
        return result;
    }

    private boolean arrayListContains(ArrayList<Problem> arrayList, Problem problem) {
        boolean result = false;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).equals(problem))
                result = true;
        }
        return result;
    }

    private ArrayList<Problem> solutionFromPath(ArrayList<Problem> path) {
        ArrayList<Problem> solution = new ArrayList<>();
        Problem temp;
        temp = path.get(path.size() - 1);
        while (true) {
            solution.add(temp);
            if (temp.getParentId().equals("-1"))
                break;
            for (int i = path.size() - 1; i > -1; i--) {
                if (path.get(i).getId().equals(temp.getParentId())) {
                    temp = path.get(i);
                    break;
                }
            }
        }
        Collections.reverse(solution);
        return solution;
    }

    private ArrayList<Problem> solutionFromTwoPaths(ArrayList<Problem> path, ArrayList<Problem> path2, Problem common) {
        ArrayList<Problem> solution = new ArrayList<>();
        Problem temp = null;
        for (int i = path.size() - 1; i > -1; i--) {
            if (path.get(i).equals(common)) {
                temp = path.get(i);
            }
        }
        while (true) {
            solution.add(temp);
            if (temp.getParentId().equals("-1"))
                break;
            for (int i = path.size() - 1; i > -1; i--) {
                if (path.get(i).getId().equals(temp.getParentId())) {
                    temp = path.get(i);
                    break;
                }
            }
        }
        Collections.reverse(solution);

        ArrayList<Problem> solution2 = new ArrayList<>();
        Problem temp2 = null;
        for (int i = path2.size() - 1; i > -1; i--) {
            if (path2.get(i).equals(common)) {
                temp2 = path2.get(i);
            }
        }
        while (true) {
            solution2.add(temp2);
            if (temp2.getParentId().equals("-2"))
                break;
            for (int i = path2.size() - 1; i > -1; i--) {
                if (path2.get(i).getId().equals(temp2.getParentId())) {
                    temp2 = path2.get(i);
                    break;
                }
            }
        }
        ArrayList<Problem> solution3 = new ArrayList<>();
        for (int i = 0; i < solution.size(); i++) {
            solution3.add(i, solution.get(i));
        }
        for (int i = 0; i < solution2.size(); i++) {
            solution3.add(solution.size() + i, solution2.get(i));
        }
        if (solution.size() > 0)
            solution3.remove(solution.size() - 1);
        else if (solution2.size() > 0)
            solution3.remove(solution3.size() - solution2.size());
        return solution3;
    }

    public ArrayList<Problem> depthLimitedSearch(Problem initialProblem, int limit, boolean iterative) throws OutOfMemoryError {
        int maximumNodes = 0;
        DfsState node = new DfsState(initialProblem, 0);
        Stack<DfsState> frontier = new Stack<>();
        frontier.push(node);
        ArrayList<Problem> path = new ArrayList<>();
        while (!frontier.isEmpty()) {
            if (frontier.peek().depth <= limit) {
                node = frontier.pop();
                path.add(node.problem);
                if (node.problem.finalState()) {
                    printReport(frontier.size(), -1, solutionFromPath(path).size(), maximumNodes);
                    return solutionFromPath(path);
                } else {
                    for (Problem s : node.problem.nextStates()) {
                        if (!arrayListContains(path, s))
                            frontier.push(new DfsState(s, node.depth + 1));
                    }
                }
            } else frontier.pop();
            maximumNodes = Math.max(Math.max(maximumNodes, frontier.size()), solutionFromPath(path).size());
        }
        if (!iterative)
            System.out.println("Goal does not exist in selected depth!");
        printReport(frontier.size(), -1, solutionFromPath(path).size(), maximumNodes);
        return null;
    }

    private class DfsState {
        Problem problem;
        int depth;

        DfsState(Problem problem, int depth) {
            this.problem = problem;
            this.depth = depth;
        }
    }

    public ArrayList<Problem> bidirectionalSearch(Problem initialProblem) {
        int maximumNodes = 0;
        int maximumNodes2 = 0;
        ArrayList<Problem> path = new ArrayList<>();
        Problem node = initialProblem;
        Queue<Problem> frontier = new LinkedList<>();
        frontier.add(node);
        path.add(node);
        ArrayList<Problem> explored = new ArrayList<>();

        ArrayList<Problem> path2 = new ArrayList<>();
        Problem node2 = Problem.getFinalState();
        Queue<Problem> frontier2 = new LinkedList<>();
        frontier2.add(node2);
        path2.add(node2);
        ArrayList<Problem> explored2 = new ArrayList<>();

        if (node.equals(node2)) {
            maximumNodes = Math.max(Math.max(maximumNodes, frontier.size() + explored.size()), solutionFromPath(path).size());
            printReport(frontier.size(), explored.size(), solutionFromPath(path).size(), maximumNodes + 1);
            return solutionFromPath(path);
        }
        while (true) {
            if (frontier.peek() == null || frontier2.peek() == null) {
                System.out.println("Failure");
                printReport(frontier2.size() + frontier.size(), explored2.size() + explored.size(),
                        -1, maximumNodes2 + maximumNodes + 1);
                return null;
            }
            node = frontier.remove();
            explored.add(node);
            for (Problem child : node.nextStates()) {
                maximumNodes = Math.max(maximumNodes, frontier.size() + explored.size());
                maximumNodes2 = Math.max(maximumNodes2, frontier2.size() + explored2.size());
                if (!arrayListContains(explored, child) && !queueContains(frontier, child)) {
                    frontier.add(child);
                    path.add(child);
                    if (arrayListContains(path2, child)) {
                        printReport(frontier2.size() + frontier.size(), explored2.size() + explored.size(),
                                solutionFromTwoPaths(path, path2, child).size(), Math.max(maximumNodes2 + maximumNodes + 1, solutionFromTwoPaths(path, path2, child).size()));
                        return solutionFromTwoPaths(path, path2, child);
                    }
                }
            }
            node2 = frontier2.remove();
            explored2.add(node2);
            for (Problem child : node2.nextStates()) {
                maximumNodes = Math.max(maximumNodes, frontier.size() + explored.size());
                maximumNodes2 = Math.max(maximumNodes2, frontier2.size() + explored2.size());
                if (!arrayListContains(explored2, child) && !queueContains(frontier2, child)) {
                    frontier2.add(child);
                    path2.add(child);
                    if (arrayListContains(path, child)) {
                        printReport(frontier2.size() + frontier.size(), explored2.size() + explored.size(),
                                solutionFromTwoPaths(path, path2, child).size(), Math.max(maximumNodes2 + maximumNodes + 1, solutionFromTwoPaths(path, path2, child).size()));
                        return solutionFromTwoPaths(path, path2, child);
                    }
                }
            }
        }
    }

    public ArrayList<Problem> uniformCostSearch(Problem initialProblem) {
        int maximumNodes = 0;
        ArrayList<Problem> path = new ArrayList<>();
        UCState node = new UCState(initialProblem, initialProblem.getCost());
        Queue<UCState> frontier = new LinkedList<>();
        frontier.add(node);
        path.add(node.problem);
        ArrayList<Problem> explored = new ArrayList<>();
        maximumNodes = Math.max(Math.max(maximumNodes, frontier.size() + explored.size()), solutionFromPath(path).size());
        if (node.problem.finalState()) {
            printReport(frontier.size(), explored.size(), solutionFromPath(path).size(), maximumNodes);
            return solutionFromPath(path);
        }
        ArrayList<UCState> tempChildren = new ArrayList<>();
        int cost;
        UCState minimum = null;
        int size;
        while (true) {
            if (frontier.peek() == null) {
                System.out.println("Failure");
                printReport(frontier.size(), explored.size(), -1, maximumNodes);
                return null;
            }
            node = frontier.remove();
            explored.add(node.problem);
            path.add(node.problem);
            maximumNodes = Math.max(Math.max(maximumNodes, frontier.size() + explored.size()), solutionFromPath(path).size());
            if (node.problem.finalState()) {
                printReport(frontier.size(), explored.size(), solutionFromPath(path).size(), maximumNodes);
                return solutionFromPath(path);
            }
            tempChildren = new ArrayList<>();
            for (Problem p : node.problem.nextStates()) {
                tempChildren.add(new UCState(p, initialProblem.getCost()));
            }
            size = tempChildren.size();
            for (int i = 0; i < size; i++) {
                cost = Integer.MAX_VALUE;
                for (UCState p : tempChildren) {
                    if (p.cost < cost) {
                        cost = p.cost;
                        minimum = p;
                    }
                }
                if (!arrayListContains(explored, minimum.problem) && !uCQueueContains(frontier, minimum)) {
                    frontier.add(minimum);
                }
                tempChildren.remove(minimum);
            }
        }
    }

    private class UCState {
        Problem problem;
        int cost;

        UCState(Problem problem, int cost) {
            this.problem = problem;
            this.cost = cost;
        }
    }

    public ArrayList<Problem> aStar(Problem initialProblem) {
        int maximumNodes = 0;
        ArrayList<Problem> path = new ArrayList<>();
        UCState node = new UCState(initialProblem, initialProblem.getHeuristic());
        Queue<UCState> frontier = new LinkedList<>();
        frontier.add(node);
        path.add(node.problem);
        ArrayList<Problem> explored = new ArrayList<>();
        maximumNodes = Math.max(Math.max(maximumNodes, frontier.size() + explored.size()), solutionFromPath(path).size());
        if (node.problem.finalState()) {
            printReport(frontier.size(), explored.size(), solutionFromPath(path).size(), maximumNodes);
            return solutionFromPath(path);
        }
        ArrayList<UCState> tempChildren = new ArrayList<>();
        int cost;
        UCState minimum = null;
        int size;
        while (true) {
            maximumNodes = Math.max(Math.max(maximumNodes, frontier.size() + explored.size()), solutionFromPath(path).size());
            if (frontier.peek() == null) {
                System.out.println("Failure");
                printReport(frontier.size(), explored.size(), -1, maximumNodes);
                return null;
            }
            node = frontier.remove();
            explored.add(node.problem);
            path.add(node.problem);
            maximumNodes = Math.max(Math.max(maximumNodes, frontier.size() + explored.size()), solutionFromPath(path).size());
            if (node.problem.finalState()) {
                printReport(frontier.size(), explored.size(), solutionFromPath(path).size(), maximumNodes);
                return solutionFromPath(path);
            }
            tempChildren = new ArrayList<>();
            for (Problem p : node.problem.nextStates()) {
                tempChildren.add(new UCState(p, p.getHeuristic()));
            }
            size = tempChildren.size();
            for (int i = 0; i < size; i++) {
                cost = Integer.MAX_VALUE;
                for (UCState p : tempChildren) {
                    if (p.cost < cost) {
                        cost = p.cost;
                        minimum = p;
                    }
                }
                if (!arrayListContains(explored, minimum.problem) && !uCQueueContains(frontier, minimum)) {
                    frontier.add(minimum);
                }
                tempChildren.remove(minimum);
            }
        }
    }

    private void printReport(int frontier, int explored, int goalDepth, int maximumNodes) {
        System.out.println("------------------------------");
        System.out.println("Frontier: " + frontier);
        System.out.println("Explored: " + explored);
        System.out.println("Goal Depth: " + goalDepth);
        System.out.println("Maximum Nodes: " + maximumNodes);
        System.out.println("------------------------------");
        System.out.println();
    }
}
