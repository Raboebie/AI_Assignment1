
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

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
    
    public void createGUI(int w , int h)
    {
        System.out.println(w + " " + h);
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(w,h);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        System.out.println("Creating panel..");
        JPanel panel = new JPanel();
        panel.setVisible(true);
        panel.setSize(50 , 50);
        panel.setBorder(BorderFactory.createLineBorder(Color.black));
        frame.add(panel);
        
        JButton button1 = new JButton();
        button1.setVisible(true);
        button1.setSize(20 , 50);
        button1.setText("Click me");
        
        panel.add(button1);
        
        button1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                
                clickedButton();
            }
        });
    }
    
    
    private static void clickedButton()
    {
        System.out.println("CLICKED IT");
    }
    
}
