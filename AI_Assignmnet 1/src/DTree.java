
import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JButton;

/**
 * @author Dihan Kapp, 12015522, Theodore Badenhorst, 12002306
 */
public class DTree {

    int plyDepth;
    int dimentions;
    public Color turn;
    public JButton[] grid;

    public class Node {

        public LinkedList<Node> children;
        //public JButton[] state;
        public Color[] cGrid;
        public int HValue;

        public Node(int size) {
            //state = new JButton[size*size];
            children = new LinkedList<>();
            cGrid = new Color[size * size];
        }

        public void InitState(JButton[] grid) {
            for (int i = 0; i < grid.length; i++) {
                //state[i] = grid[i];
                if (grid[i].getBackground() != Color.red && grid[i].getBackground() != Color.blue) {
                    cGrid[i] = Color.white;
                } else {
                    cGrid[i] = grid[i].getBackground();
                }
                this.HValue = 0;
            }
        }
    }
    Node root;

    public DTree(JButton[] grid, int _dimentions, int _plyDepth) {
        this.dimentions = _dimentions;
        root = new Node(_dimentions);
        root.InitState(grid);
        this.plyDepth = _plyDepth;
        this.grid = grid;
    }

    public void BuildTree() {
        Node step = root;
        Queue<Node> q = new LinkedList<>();
        q.add(step);
        int depth = 0;
        turn = Color.blue;

        while (!q.isEmpty() && depth < plyDepth) {
            step = q.remove();
            //System.out.println(plyDepth);
            for (int i = 0; i < step.cGrid.length; i++) {
                if (step.cGrid[i] == turn) {
                    for (int j = 0; j < step.cGrid.length; j++) {
                        // System.out.println(ValidMove(step.cGrid , i , j , dimentions) + " from " + i + " to " + j);
                        if (ValidMove(step.cGrid, i, j, dimentions)) {
                            Color[] tempstates = new Color[dimentions * dimentions];
                            for (int k = 0; k < step.cGrid.length; k++) {
                                tempstates[k] = step.cGrid[k];
                            }
                            //  System.err.println("Checking state values");

                            tempstates[i] = (Color.white);
                            tempstates[j] = (turn);
                            checkPossesion(tempstates, j);

                            Node n = new Node(dimentions);
                            n.cGrid = tempstates;
                            step.children.add(n);
                        }
                    }
                    for (Node e : step.children) {
                        q.add(e);
                    }

                }
            }
            depth++;
            if (turn == Color.blue) {
                turn = Color.red;
            } else {
                turn = Color.blue;
            }
        }
    }

    public JButton[] makeMove(boolean useAlpha) {

        transverse(root, true);

        Color[] answer = new Color[dimentions * dimentions];
        for (Node child : root.children) {
            if (root.HValue == child.HValue) {
                answer = child.cGrid;
                break;
            }
        }

        for (int i = 0; i < this.grid.length; i++) {
            this.grid[i].setBackground(answer[i]);
        }

        return grid;
    }

    private void transverse(Node node, boolean turn) {
        if (node.children.size() < 1) {
            node.HValue = getHeuristicValue(node.cGrid, turn);
        } else {
            if (turn == true) {
                // node.HValue = 0;
            } else {
                node.HValue = 100;
            }
            for (Node n : node.children) {
                transverse(n, !turn);
                if (turn == true) {
                    if (node.HValue < n.HValue) {
                        node.HValue = n.HValue;
                    }
                } else {
                    if (node.HValue > n.HValue) {
                        node.HValue = n.HValue;
                    }
                }
            }
        }
    }

    public void checkPossesion(Color[] grid, int index) {
        Color opponentColour;
        if (grid[index] == Color.red) {
            opponentColour = Color.blue;
        } else {
            opponentColour = Color.red;
        }

        //Check binne die een blokkie radius of daar 'n opponent is
        int indexUp1 = index - dimentions >= 0 ? index - dimentions : -1;
        int indexDown1 = index + dimentions < dimentions * dimentions ? index + dimentions : -1;
        int indexLeft1 = index - 1 >= 0 ? index - 1 : -1;
        int indexRight1 = index + 1 < dimentions * dimentions ? index + 1 : -1;

        if (indexUp1 > -1) {
            if (grid[indexUp1] == opponentColour) {
                grid[indexUp1] = (grid[index]);
                checkPossesion(grid, indexUp1);
            }
        }

        if (indexDown1 > -1) {
            if (grid[indexDown1] == opponentColour) {
                grid[indexDown1] = (grid[index]);
                checkPossesion(grid, indexDown1);
            }
        }

        if (indexLeft1 > -1 && index % dimentions != 0) {
            if (grid[indexLeft1] == opponentColour) {
                grid[indexLeft1] = (grid[index]);
                checkPossesion(grid, indexLeft1);
            }
        }

        if (indexRight1 > -1 && index % dimentions != dimentions - 1) {
            if (grid[indexRight1] == opponentColour) {
                grid[indexRight1] = (grid[index]);
                checkPossesion(grid, indexRight1);
            }
        }

        //Check binne die twee blokkie radius of daar 'n opponent is
        int indexUp2 = index - (dimentions * 2) >= 0 ? index - (dimentions * 2) : -1;
        int indexDown2 = index + (dimentions * 2) < dimentions * dimentions ? index + (dimentions * 2) : -1;
        int indexLeft2 = index - 2 >= 0 ? index - 2 : -1;
        int indexRight2 = index + 2 < dimentions * dimentions ? index + 2 : -1;

        if (indexUp2 > -1) {
            if (grid[indexUp2] == opponentColour) {
                grid[indexUp2] = grid[index];
                checkPossesion(grid, indexUp2);
            }
        }

        if (indexDown2 > -1) {
            if (grid[indexDown2] == opponentColour) {
                grid[indexDown2] = grid[index];
                checkPossesion(grid, indexDown2);
            }
        }

        if (indexLeft2 > -1 && index % dimentions != 1 && index % dimentions != 0) {
            if (grid[indexLeft2] == opponentColour) {
                grid[indexLeft2] = grid[index];
                checkPossesion(grid, indexLeft2);
            }
        }

        if (indexRight2 > -1 && index % dimentions != dimentions - 2 && index % dimentions != dimentions - 1) {
            if (grid[indexRight2] == opponentColour) {
                grid[indexRight2] = grid[index];
                checkPossesion(grid, indexRight2);
            }
        }
    }

    private int getHeuristicValue(Color[] state, boolean turn) {

        int countRed = 0;
        int countBlue = 0;

        for (int i = 0; i < state.length; i++) {
            if (state[i] == Color.red) {
                countRed++;
            } else if (state[i] == Color.blue) {
                countBlue++;
            }
        }

        if (turn) { //Max's turn
            return countRed - countBlue;
        } else//Min's turn
        {
            return countBlue - countRed;
        }
    }

    private boolean ValidMove(Color[] state, int index, int moveToIndex, int dimentions) {
        int AllowedNumSteps = checkNumberOfAllowedMoves(state, index, new LinkedList<Integer>(), 0);
        int indexUp = moveToIndex;
        int indexDown = moveToIndex;
        LinkedList<Integer> vertical = new LinkedList<>();
        LinkedList<Integer> horizontal = new LinkedList<>();
        Color selectedButton = state[index];

        if (state[moveToIndex] == Color.blue || state[moveToIndex] == Color.red) {
            return false;
        }

        for (int k = 0; k < AllowedNumSteps; k++) {
            indexUp -= dimentions;
            if (indexUp >= 0) {
                vertical.add(indexUp);
            }
            indexDown += dimentions;
            if (indexDown < dimentions * dimentions) {
                vertical.add(indexDown);
            }
        }

        int indexLeft = moveToIndex;
        int indexRight = moveToIndex + 1;
        int steps = 0;

        while (indexLeft % dimentions != 0 && steps < AllowedNumSteps) {
            indexLeft--;
            horizontal.add(indexLeft);
            steps++;
        }

        steps = 0;
        while (indexRight % dimentions != 0 && steps < AllowedNumSteps) {
            horizontal.add(indexRight);
            indexRight++;
            steps++;
        }
        boolean contain = false;

        if (vertical.contains(index)) {
            contain = true;
        }
        if (horizontal.contains(index)) {
            contain = true;
        }

        return contain;
    }

    public int checkNumberOfAllowedMoves(Color[] state, int index, LinkedList<Integer> preTested, int count) {
        Color playerColour = state[index];

        //Check binne die een blokkie radius of daar 'n opponent is
        int indexUp1 = index - dimentions >= 0 ? index - dimentions : -1;
        int indexDown1 = index + dimentions < dimentions * dimentions ? index + dimentions : -1;
        int indexLeft1 = index - 1 >= 0 ? index - 1 : -1;
        int indexRight1 = index + 1 < dimentions * dimentions ? index + 1 : -1;

        boolean up = false;

        if (indexUp1 > -1) {
            if (state[indexUp1] == playerColour && !preTested.contains(indexUp1)) {
                preTested.add(indexUp1);
                count = checkNumberOfAllowedMoves(state, indexUp1, preTested, count + 1);
            }
        }

        if (indexDown1 > -1) {
            if (state[indexDown1] == playerColour && !preTested.contains(indexDown1)) {
                preTested.add(indexDown1);
                count = checkNumberOfAllowedMoves(state, indexDown1, preTested, count + 1);
            }
        }

        if (indexLeft1 > -1 && index % dimentions != 0) {
            if (state[indexLeft1] == playerColour && !preTested.contains(indexLeft1)) {
                preTested.add(indexLeft1);
                count = checkNumberOfAllowedMoves(state, indexLeft1, preTested, count + 1);
            }
        }

        if (indexRight1 > -1 && index % dimentions != dimentions - 1) {
            if (state[indexRight1] == playerColour && !preTested.contains(indexRight1)) {
                preTested.add(indexRight1);
                count = checkNumberOfAllowedMoves(state, indexRight1, preTested, count + 1);
            }
        }
        //Check binne die twee blokkie radius of daar 'n opponent is
        int indexUp2 = index - (dimentions * 2) >= 0 ? index - (dimentions * 2) : -1;
        int indexDown2 = index + (dimentions * 2) < dimentions * dimentions ? index + (dimentions * 2) : -1;
        int indexLeft2 = index - 2 >= 0 ? index - 2 : -1;
        int indexRight2 = index + 2 < dimentions * dimentions ? index + 2 : -1;

        if (indexUp2 > -1) {
            if (state[indexUp2] == playerColour && !preTested.contains(indexUp2)) {
                preTested.add(indexUp2);
                count = checkNumberOfAllowedMoves(state, indexUp2, preTested, count + 1);
            }
        }

        if (indexDown2 > -1) {
            if (state[indexDown2] == playerColour && !preTested.contains(indexDown2)) {
                preTested.add(indexDown2);
                count = checkNumberOfAllowedMoves(state, indexDown2, preTested, count + 1);
            }
        }

        if (indexLeft2 > -1 && index % dimentions != 1 && index % dimentions != 0) {
            if (state[indexLeft2] == playerColour && !preTested.contains(indexLeft2)) {
                preTested.add(indexLeft2);
                count = checkNumberOfAllowedMoves(state, indexLeft2, preTested, count + 1);
            }
        }

        if (indexRight2 > -1 && index % dimentions != dimentions - 2 && index % dimentions != dimentions - 1) {
            if (state[indexRight2] == playerColour && !preTested.contains(indexRight2)) {
                preTested.add(indexRight2);
                count = checkNumberOfAllowedMoves(state, indexRight2, preTested, count + 1);
            }
        }

        return count > 0 ? count : 1;
    }
}
