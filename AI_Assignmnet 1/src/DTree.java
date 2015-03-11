
import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JButton;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class DTree{
    int plyDepth;
    int dimentions;
    public Color turn;
    public class Node{
        public LinkedList<Node> children;
        public JButton[] state;
        public Color[] cGrid;
        
        public Node(int size){
            state = new JButton[size*size];
            children = new LinkedList<>();
            cGrid = new Color[size*size];
        }
        
        public void InitState(JButton[] grid){
            for(int i = 0; i < grid.length;i++){
                state[i] = grid[i];
                cGrid[i] = state[i].getBackground();
            }
        }
    }
    
    Node root;
    
    public DTree(JButton[] grid, int _dimentions, int _plyDepth){
        this.dimentions = _dimentions;
        root = new Node(_dimentions);
        root.InitState(grid);
        this.plyDepth =_plyDepth;
    }
    
    public void BuildTree(){
        Node step = root;
        Queue<Node> q = new LinkedList<>();
        q.add(step);
        int depth = 0;
        turn = Color.blue;
        
        while(!q.isEmpty() && depth < plyDepth){
            step = q.remove();
            for(int i=0;i<step.state.length;i++){
                if(step.cGrid[i] == turn){
                    for(int j=0;j<step.cGrid.length;j++){
                        if(ValidMove(step.cGrid, i, j, dimentions)){
                            Color[] tempstates = new Color[dimentions * dimentions];
                            for(int k = 0 ; k < step.cGrid.length ; k++){
                                tempstates[k] = step.cGrid[k];
                            }
                            tempstates[i] = (Color.white);
                            tempstates[j] = (turn);
                            Node n = new Node(dimentions);
                            n.cGrid = tempstates;
                            step.children.add(n);
                        }
                    }
                    for(Node e : step.children)
                    {
                        q.add(e);
                    }
                    
                    depth++;
                    if(turn == Color.blue)
                        turn = Color.red;
                    else
                        turn = Color.blue;
                }
            }
        }
    }
    
    public boolean ValidMove(Color[] state, int index, int moveToIndex, int dimentions){
            int AllowedNumSteps = 1;
            int indexUp = moveToIndex;
            int indexDown = moveToIndex;
            LinkedList<Color> vertical = new LinkedList<>();
            LinkedList<Color> horizontal = new LinkedList<>();
            Color selectedButton = state[index];
            
            if(state[moveToIndex] == Color.blue || state[moveToIndex] == Color.red)
                return false;
            
            for(int k = 0; k < AllowedNumSteps ; k++){
                indexUp -= dimentions;
                if(indexUp >= 0){
                    vertical.add(state[indexUp]);
                }
                indexDown += dimentions;
                if(indexDown < dimentions*dimentions){
                    vertical.add(state[indexDown]);
                }
            }
            
            int indexLeft = moveToIndex;
            int indexRight = moveToIndex+1;
            int steps = 0;
            
            while(indexLeft % dimentions != 0 && steps < AllowedNumSteps){
                indexLeft--;
                horizontal.add(state[indexLeft]);
                steps++;
            }
            
            steps = 0;
            while(indexRight % dimentions != 0 && steps < AllowedNumSteps){
                horizontal.add(state[indexRight]);
                indexRight++;
                steps++;
            }
            boolean contain = false;            
            
            if(vertical.contains(selectedButton)) contain = true;
            if(horizontal.contains(selectedButton)) contain = true;
            
            if(!contain) {
                return false;
            }
        return true;
    }
    
}
