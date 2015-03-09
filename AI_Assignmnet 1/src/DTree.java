
import java.awt.Color;
import java.util.LinkedList;
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
    public class Node{
        public LinkedList<Node> children;
        public JButton[] state;
        
        public Node(int size){
            state = new JButton[size*size];
            children = new LinkedList<>();
        }
        
        public void InitState(JButton[] grid){
            for(int i = 0; i < grid.length;i++){
                state[i] = grid[i];
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
        for(int i=0;i<root.state.length;i++){
            if(root.state[i].getBackground() == Color.blue){
                for(int j=0;j<root.state.length;j++){
                    if(ValidMove(root.state, i, j, dimentions)){
                        //root.children.add(root.state);//TODO
                        System.out.println("Move from: "+i+" to: "+ j +" isvalid? " + ValidMove(root.state, i, j, dimentions));
                    }
                }
            }
        }
    }
    
    public boolean ValidMove(JButton[] state, int index, int moveToIndex, int dimentions){
            int AllowedNumSteps = 1;
            int indexUp = moveToIndex;
            int indexDown = moveToIndex;
            LinkedList<JButton> vertical = new LinkedList<>();
            LinkedList<JButton> horizontal = new LinkedList<>();
            JButton selectedButton = state[index];
            
            if(state[moveToIndex].getBackground() == Color.blue || state[moveToIndex].getBackground() == Color.red)
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
