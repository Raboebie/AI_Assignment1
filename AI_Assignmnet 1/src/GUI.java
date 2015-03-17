
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dihan
 */
public class GUI implements ActionListener{
    public static int dimentions = 0;
    public static JTextField sizeField;
    public static JTextField txtStartingCell;
    public static JFrame frame;
    public enum Pos{LEFT,RIGHT}
    public enum Colour{WHITE,RED,BLUE}
    public static JButton[] grid;
    public static boolean Turn;
    public static JButton selectedButton;
    public static int AllowedNumSteps = 1;
    public static boolean playingWithAI;
    
    public void createGUI(int w , int h)
    {
        playingWithAI = false;
        Turn = true;
        System.out.println(w + " " + h);
        frame = new JFrame();
        
        frame.setSize(w,h);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        JButton button1 = new JButton();
        button1.setVisible(true);
        button1.setSize(20 , 50);
        button1.setText("Click me");
        
        //panel.add(button1);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
            JMenuItem exit = new JMenuItem("Exit");
                exit.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
            file.add(exit);
        JMenu options = new JMenu("Options");
        ButtonGroup bg = new ButtonGroup();
        JRadioButtonMenuItem PvP = new JRadioButtonMenuItem("Player vs Player");
        PvP.setSelected(true);
        bg.add(PvP);
        JRadioButtonMenuItem PvAI = new JRadioButtonMenuItem("Player vs AI");
        PvAI.setSelected(false);
        bg.add(PvAI);
        PvAI.addActionListener(this);
        JMenuItem size = new JMenuItem("Board size");
        size.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame boardSize = new JFrame("Set board size");
                boardSize.setLayout(null);
                boardSize.setVisible(true);
                boardSize.setSize(400,200);
                
                JLabel setSize = new JLabel("Input Size: ");
                setSize.setBounds(10,10,100,20);
                boardSize.add(setSize);
                
                sizeField = new  JTextField();
                sizeField.setBounds(110, 10, 200, 20);
                boardSize.add(sizeField);
                
                
                
                JLabel lblStartingCell = new JLabel("Number of starting cells: ");
                lblStartingCell.setBounds(10,30,100,20);
                boardSize.add(lblStartingCell);
                
                txtStartingCell = new JTextField();
                txtStartingCell.setBounds(110, 30, 200, 20);
                boardSize.add(txtStartingCell);
                
                JButton confirmSize = new JButton("Confirm");
                confirmSize.setBounds(110, 120, 100, 30);
                confirmSize.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dimentions = Integer.parseInt(sizeField.getText());
                        int startingCellNumber = Integer.parseInt(txtStartingCell.getText());
                        
                        grid = new JButton[dimentions* dimentions];
                        int colombs = 0;
                        int rows =0;
                        
                        for(int k = 0 ; k < dimentions * dimentions ; k++)
                        {
                            colombs++;
                            if(k % dimentions == 0)
                            {
                                rows++;
                                colombs = 0;
                            }
                                grid[k] = new JButton("");
                                grid[k].setBackground(Color.WHITE);
                                grid[k].setName(k + "");
                                grid[k].setBorder(new LineBorder(Color.BLACK,1));
                                if(colombs < dimentions / 2)
                                    grid[k].putClientProperty("POS", Pos.LEFT);
                                else
                                    grid[k].putClientProperty("POS", Pos.RIGHT);
                                    
                                grid[k].addActionListener(new ActionListener() {

                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    clickedButton(((JButton)e.getSource()).getName());
                                }
                            });
                                grid[k].setBounds(colombs*70,rows * 30 , 70, 30);
                                frame.add(grid[k]);
                               
                        }
                        
                     //   int[] randomStartingBlock = new int[startingCellNumber*2];
                        Random rand = new Random();
                        int countLeft = 0;
                        int countRight = 0;
                        
                        while(countLeft < startingCellNumber){
                            int number = rand.nextInt(dimentions * dimentions);
                            if(grid[number].getClientProperty("POS") == Pos.LEFT)
                            {
                                if(grid[number].getBackground() != Color.red)
                                {
                                    if(grid[number+1].getBackground() != Color.blue)
                                    {
                                       
                                        colourCell(number, Color.red, Color.pink);
                                        countLeft++;
                                    
                                    }
                                }
                            }
                            
                        }
                        
                        while(countRight < startingCellNumber)
                        {
                            int number = rand.nextInt(dimentions * dimentions);
                            if(grid[number].getClientProperty("POS") == Pos.RIGHT)
                            {
                                if(grid[number].getBackground() != Color.blue)
                                {
                                     if(grid[number-1].getBackground() != Color.red)
                                     {
                                        colourCell(number, Color.blue, Color.cyan);
                                        countRight++;
                                     }
                                }
                            }
                        }
                                                
                        frame.repaint();
                        
                    }
                });
                boardSize.add(confirmSize);
                
            }
        });

        options.add(PvP);
        options.add(PvAI);
        options.add(size);

        menuBar.add(file);
        menuBar.add(options);

        frame.setJMenuBar(menuBar);
       
        frame.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        playingWithAI = e.getActionCommand().equals("Player vs AI");
    }
    
    public static boolean clickedButton(String indexOfClickedButton)
    {
        LinkedList<JButton> vertical = new LinkedList<JButton>();
        LinkedList<JButton> horizontal = new LinkedList<JButton>();
        
        if(selectedButton == null){       
            selectedButton = grid[Integer.parseInt(indexOfClickedButton)];
            selectedButton.putClientProperty("INDEX",Integer.parseInt(indexOfClickedButton));
            if(Turn){
                if(selectedButton.getBackground() != Color.red){
                    selectedButton = null;
                    return false;
                }                
            }else{
                if(selectedButton.getBackground() != Color.blue){
                    selectedButton = null;
                    return false;
                }   
                
            }
            AllowedNumSteps = checkNumberOfAllowedMoves(Integer.parseInt(indexOfClickedButton), new LinkedList<Integer>(), 0);
            grid[Integer.parseInt(indexOfClickedButton)].setBorder(new LineBorder(Color.GREEN, 1));
            frame.repaint();
        }
        else{
            if(Integer.parseInt(indexOfClickedButton) == (int)selectedButton.getClientProperty("INDEX")){
                selectedButton.setBorder(new LineBorder(Color.BLACK, 1));
                selectedButton = null;
                return false;
            }
            
            int indexUp = Integer.parseInt(indexOfClickedButton);
            int indexDown = Integer.parseInt(indexOfClickedButton);
            
            if(grid[Integer.parseInt(indexOfClickedButton)].getBackground() == Color.red || grid[Integer.parseInt(indexOfClickedButton)].getBackground() == Color.blue)
                return false;
            
            //AllowedNumSteps = checkNumberOfAllowedMoves((int)selectedButton.getClientProperty("INDEX"), null, 1);
            for(int k = 0; k < AllowedNumSteps ; k++){
                indexUp -= dimentions;
                    if(indexUp >= 0)
                       vertical.add(grid[indexUp]);
                indexDown += dimentions;
                    if(indexDown < dimentions*dimentions)
                        vertical.add(grid[indexDown]);
                
            }
            
            int indexLeft = Integer.parseInt(indexOfClickedButton);
            int indexRight = Integer.parseInt(indexOfClickedButton)+1;
            int steps = 0;
            
            while(indexLeft % dimentions != 0 && steps < AllowedNumSteps){
                indexLeft--;
                horizontal.add(grid[indexLeft]);
                steps++;
            }
            
            steps = 0;
            while(indexRight % dimentions != 0 && steps < AllowedNumSteps){
                horizontal.add(grid[indexRight]);
                indexRight++;
                steps++;
            }
            boolean contain = false;
            
            if(vertical.contains(selectedButton)) contain = true;
            if(horizontal.contains(selectedButton)) contain = true;
            
            if(!contain) {
                return false;
            }
            
            if(Turn){
                grid[Integer.parseInt(indexOfClickedButton)].setBackground(Color.red);
                Turn = false;
                frame.repaint();
            }
            else{ 
                grid[Integer.parseInt(indexOfClickedButton)].setBackground(Color.blue);
                Turn = true;
                frame.repaint();
            }
      
            for(int i = 0; i < grid.length; i++){
                if(grid[i].getBackground() != Color.red && grid[i].getBackground() != Color.blue){
                   grid[i].setBackground(Color.white); 
                }
            }
                       
            selectedButton.setBorder(new LineBorder(Color.BLACK, 1));
            selectedButton.setBackground(Color.white);
            selectedButton = null;
            
            checkPossesion(Integer.parseInt(indexOfClickedButton));
            
            for(int i = 0; i < grid.length; i++){
                if(grid[i].getBackground() == Color.red ){
                   colourCell(i,grid[i].getBackground(),Color.pink); 
                }
                if(grid[i].getBackground() == Color.blue){
                   colourCell(i,grid[i].getBackground(),Color.cyan);                     
                }
            }
            String[] results = checkWin();
            if(results[0].equals("false")){//TODO: Put this in a new frame
                System.out.println(results[1]);
            }
            
            frame.repaint();
            if(playingWithAI){
                if(!Turn){
                    Turn = true;
                    DTree ai = new DTree(grid,dimentions,1);
                    ai.BuildTree();
                }
            }
            
            results = checkWin();
            if(results[0].equals("false")){//TODO: Put this in a new frame
                System.out.println(results[1]);
            }
            
            return true;
        }
        
        return false;
    }
    
    public static int checkNumberOfAllowedMoves(int index, LinkedList<Integer> preTested,int count)
    {
        Color playerColour = grid[index].getBackground();
        
        //Check binne die een blokkie radius of daar 'n opponent is
        int indexUp1 = index-dimentions >= 0? index-dimentions : -1;
        int indexDown1 = index+dimentions < dimentions*dimentions? index + dimentions : -1; 
        int indexLeft1 = index - 1 >= 0? index - 1 : -1 ;
        int indexRight1 = index + 1 < dimentions*dimentions? index + 1 : -1;
        
        boolean up = false;
        
        if(indexUp1 > -1){
            if(grid[indexUp1].getBackground() == playerColour && !preTested.contains(indexUp1)){
                preTested.add(indexUp1);
                count = checkNumberOfAllowedMoves(indexUp1, preTested, count + 1);
            }
        }
        
        if(indexDown1 > -1){
            if(grid[indexDown1].getBackground() == playerColour && !preTested.contains(indexDown1)){
                preTested.add(indexDown1);
                count = checkNumberOfAllowedMoves(indexDown1, preTested, count + 1);
            }
        }
        
        if(indexLeft1 > -1 && index % dimentions != 0)
        {
            if(grid[indexLeft1].getBackground() == playerColour && !preTested.contains(indexLeft1)){
                preTested.add(indexLeft1);
                count = checkNumberOfAllowedMoves(indexLeft1, preTested, count + 1);
            }
        }
        
        if(indexRight1 > -1 && index % dimentions != dimentions -1)
        {
            if(grid[indexRight1].getBackground() == playerColour && !preTested.contains(indexRight1)){
                preTested.add(indexRight1);
                count = checkNumberOfAllowedMoves(indexRight1, preTested, count + 1);
            }
        }
        //Check binne die twee blokkie radius of daar 'n opponent is
        int indexUp2 = index-(dimentions*2) >= 0? index-(dimentions*2) : -1;
        int indexDown2 = index+(dimentions*2) < dimentions*dimentions? index + (dimentions*2) : -1; 
        int indexLeft2 = index - 2 >= 0? index - 2 : -1 ;
        int indexRight2 = index + 2 < dimentions*dimentions? index + 2 : -1;
        
        if(indexUp2 > -1){
            if(grid[indexUp2].getBackground() == playerColour && !preTested.contains(indexUp2)){
                preTested.add(indexUp2);
                count = checkNumberOfAllowedMoves(indexUp2, preTested, count + 1);
            }
        }
        
        if(indexDown2 > -1){
            if(grid[indexDown2].getBackground() == playerColour && !preTested.contains(indexDown2)){
                preTested.add(indexDown2);
                count = checkNumberOfAllowedMoves(indexDown2, preTested, count + 1);
            }
        }
                
        if(indexLeft2 > -1 && index % dimentions != 1 && index % dimentions != 0){
            if(grid[indexLeft2].getBackground() == playerColour && !preTested.contains(indexLeft2)){
                preTested.add(indexLeft2);
                count = checkNumberOfAllowedMoves(indexLeft2, preTested, count + 1);
            }
        }
        
        if(indexRight2 > -1 && index % dimentions != dimentions -2 && index % dimentions != dimentions -1){
            if(grid[indexRight2].getBackground() == playerColour && !preTested.contains(indexRight2)){
                preTested.add(indexRight2);
                count = checkNumberOfAllowedMoves(indexRight2, preTested, count + 1);
            }
        }
        
        return count > 0 ? count : 1;
    }
    
    public static void checkPossesion(int index)
    {
        Color opponentColour ;
        if(grid[index].getBackground() == Color.red)
            opponentColour = Color.blue;
        else
            opponentColour = Color.red;
        
        //Check binne die een blokkie radius of daar 'n opponent is
        int indexUp1 = index-dimentions >= 0? index-dimentions : -1;
        int indexDown1 = index+dimentions < dimentions*dimentions? index + dimentions : -1; 
        int indexLeft1 = index - 1 >= 0? index - 1 : -1 ;
        int indexRight1 = index + 1 < dimentions*dimentions? index + 1 : -1;
        
        if(indexUp1 > -1)
            if(grid[indexUp1].getBackground() == opponentColour){
                grid[indexUp1].setBackground(grid[index].getBackground());
                checkPossesion(indexUp1);
            }
        
        
        if(indexDown1 > -1)
            if(grid[indexDown1].getBackground() == opponentColour){
                grid[indexDown1].setBackground(grid[index].getBackground());
                checkPossesion(indexDown1);
            }
        
        
        if(indexLeft1 > -1 && index % dimentions != 0)
            if(grid[indexLeft1].getBackground() == opponentColour){
                grid[indexLeft1].setBackground(grid[index].getBackground());
                checkPossesion(indexLeft1);
            }
        
        if(indexRight1 > -1 && index % dimentions != dimentions -1)
            if(grid[indexRight1].getBackground() == opponentColour){
                grid[indexRight1].setBackground(grid[index].getBackground());
                checkPossesion(indexRight1);
            }
        
        //Check binne die twee blokkie radius of daar 'n opponent is
        int indexUp2 = index-(dimentions*2) >= 0? index-(dimentions*2) : -1;
        int indexDown2 = index+(dimentions*2) < dimentions*dimentions? index + (dimentions*2) : -1; 
        int indexLeft2 = index - 2 >= 0? index - 2 : -1 ;
        int indexRight2 = index + 2 < dimentions*dimentions? index + 2 : -1;
        
        if(indexUp2 > -1)
            if(grid[indexUp2].getBackground() == opponentColour){
                grid[indexUp2].setBackground(grid[index].getBackground());
                checkPossesion(indexUp2);
            }
        
        
        if(indexDown2 > -1)
            if(grid[indexDown2].getBackground() == opponentColour){
                grid[indexDown2].setBackground(grid[index].getBackground());
                checkPossesion(indexDown2);
            }
        
        
        if(indexLeft2 > -1 && index % dimentions != 1 && index % dimentions != 0)
            if(grid[indexLeft2].getBackground() == opponentColour){
                grid[indexLeft2].setBackground(grid[index].getBackground());
                checkPossesion(indexLeft2);
            }
        
        if(indexRight2 > -1 && index % dimentions != dimentions -2 && index % dimentions != dimentions -1)
            if(grid[indexRight2].getBackground() == opponentColour){
                grid[indexRight2].setBackground(grid[index].getBackground());
                checkPossesion(indexRight2);
            }
    }
    
    private static void colourCell(int index, Color color , Color tranparentColor)
    {
        int[] allowedCellIndexes = checkCellColor(index);
        
        for(int k = 0 ; k < allowedCellIndexes.length ; k++)
        {
            if(allowedCellIndexes[k] != -1)
            {
                if(grid[allowedCellIndexes[k]].getBackground() == Color.white)                {
                    grid[allowedCellIndexes[k]].setBackground(tranparentColor);
                }
            }
        }
        grid[index].setBackground(color);
    }
    
    public static String[] checkWin(){
        String stillInTheGame = "false";
        int red = 0, blue = 0;
        for(int i = 0; i < dimentions*dimentions; i++){
            if(grid[i].getBackground() == Color.red)
                red++;
            if(grid[i].getBackground() == Color.blue)
                blue++;
            
            if(red > 0 && blue > 0){
                stillInTheGame = "true";
                break;
            }
        }
        
        String message = "";
        if(stillInTheGame.equals("false")){
            if(red > 0)
                message = "Player red has won the game!";
            else
                message = "Player Blue has won the game!";
        }
        
        return new String[]{ stillInTheGame, message};
    }
    
    private static int[] checkCellColor(int index)
    {
        int[] allowedCellIndexes = new int[8];
        for(int k = 0 ; k < 8 ; k++)
            allowedCellIndexes[k] = -1;
        int  checkLeft = -1, checkRight = 1, checkTop = -dimentions, checkBottom = dimentions, checkDiagLeftTop = -dimentions -1 , checkDiagTopRight = -dimentions +1,
                checkDiagBottomLeft = dimentions -1 , checkDiagBottomRight = dimentions +1;
        
        if(checkLeft + index >= 0)
            allowedCellIndexes[0] = checkLeft + index;
        if(checkRight + index < dimentions * dimentions)
            allowedCellIndexes[1] = checkRight + index;
        if(checkTop + index >= 0)
            allowedCellIndexes[2] = checkTop + index;
        if(checkBottom + index < dimentions * dimentions)
            allowedCellIndexes[3] = checkBottom + index;
        if(checkDiagLeftTop + index >= 0)
            allowedCellIndexes[4] = checkDiagLeftTop + index;
        if(checkDiagTopRight + index >= 0)
            allowedCellIndexes[5] = checkDiagTopRight + index;
        if(checkDiagBottomLeft + index < dimentions * dimentions)
            allowedCellIndexes[6] = checkDiagBottomLeft + index;
        if(checkDiagBottomRight + index < dimentions * dimentions)
            allowedCellIndexes[7] = checkDiagBottomRight + index;
        
        if(index % dimentions == 0){
            allowedCellIndexes[0] = -1;
            allowedCellIndexes[4] = -1;
            allowedCellIndexes[6] = -1;
        }
        
        if(index % dimentions == dimentions-1){
            allowedCellIndexes[1] = -1;
            allowedCellIndexes[5] = -1;
            allowedCellIndexes[7] = -1;
        }
        
        return allowedCellIndexes;
    }
    
}
