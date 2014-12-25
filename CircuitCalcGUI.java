/*
Program: Graphic User Interface- Circuit Calc
This. CircutiCalcGUI.java 
Date: 11/29/14
Author: G.Koehl
Purpose: This Class takes care of the construction of the CircuitCalc class frame.
The frame will then house all the panels needed for the interface. 
*/
package circuitcalcgui;

import javax.swing.JFrame;//the Frame for the CircuitCalc panels 

public class CircutiCalcGUI {
//===============Main Method 
    public static void main(String[] args) {
        // TODO code application logic here
        CircuitCalc GUI = new CircuitCalc();//construct a new frame for the UI components 
        GUI.setTitle("Ohm's Law Circuit Calculator");//Set title for the frame
        GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//set close operation 
        GUI.setVisible(true);//make it visible 
         
    }//eomethod main
    
}//EOCLASS
