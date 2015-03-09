
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
public class GUI {
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
    
    public void createGUI(int w , int h)
    {
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
                                //System.out.println(colombs);
                                grid[k] = new JButton("");
                                grid[k].setBackground(Color.WHITE);
                                grid[k].putClientProperty("Colour", Colour.WHITE);
                                grid[k].putClientProperty("Index", k);
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

            options.add(size);

        menuBar.add(file);
        menuBar.add(options);

        frame.setJMenuBar(menuBar);
       
        frame.setVisible(true);
    }
    
    
    private static void clickedButton(String indexOfClickedButton)
    {
        LinkedList<JButton> vertical = new LinkedList<JButton>();
        LinkedList<JButton> horizontal = new LinkedList<JButton>();
        
        if(selectedButton == null){       
            selectedButton = grid[Integer.parseInt(indexOfClickedButton)];
            if(Turn){
                if(selectedButton.getBackground() != Color.red){
                    selectedButton = null;
                    return;
                }                
            }else{
                if(selectedButton.getBackground() != Color.blue){
                    selectedButton = null;
                    return;
                }   
                
            }
            grid[Integer.parseInt(indexOfClickedButton)].setBorder(new LineBorder(Color.GREEN, 1));
            frame.repaint();
        }
        else{
            int indexUp = Integer.parseInt(indexOfClickedButton);
            int indexDown = Integer.parseInt(indexOfClickedButton);
            
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
                return;
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
            
            for(int i = 0; i < grid.length; i++){
                if(grid[i].getBackground() == Color.red ){
                   colourCell(i,grid[i].getBackground(),Color.pink); 
                }
                if(grid[i].getBackground() == Color.blue){
                   colourCell(i,grid[i].getBackground(),Color.cyan);                     
                }
            }
            
            frame.repaint();
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
