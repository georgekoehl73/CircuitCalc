/*
Program: Graphic User Interface- Circuit Calc
This. Circuit.java 
Date: 11/29/14
Author: G.Koehl
Purpose: This class keeps the entered and computed values of the circuit. It contains
all the formulas and outputs needed for the CircuitCalc GUI. This Class has a single
NAC constructor that creates a circuit that has 0 values for its three fields. 
This class contains one computed value (Watt) that is accessible through the 
findWatt() method. Wattage is a value that is computed from Voltage * Amperage.

*/
package circuitcalcgui;


public class Circuit {
    //============Variables 
    private double circuitVoltage; //Voltage of circuit
    private double circuitResistance; //Resistance (Ohms) of the circuit 
    private double circuitAmperage; //Amperage of circuit
    
//==============CONSTRUCTORS 
    public Circuit(){
        //all new circuit objects variables are set to 0 on construction
        this.circuitAmperage = 0; 
        this.circuitVoltage = 0; 
        this.circuitResistance = 0; 
    }//NAC
    
    //========================Mutator Methods 
    public void setVoltage(double voltage){//set voltage
    
        this.circuitVoltage = voltage; 
    }//eomethod setVoltage
    
    public void setResistance (double resistance){//set resistance
    
        this.circuitResistance = resistance; 
    }//eomethod setResistance
    
    public void setAmperage(double amperage){//set amperage 
        
        this.circuitAmperage = amperage; 
    }//eomethod setAmperage 
    
    //=======================Accessor Methods 
    public double getVoltage(){
        
        return circuitVoltage;//get current voltage 
    }//eomethod getVoltage
    
    public double getResistance(){
        
        return circuitResistance; //get current Resistance 
    }//eomethod getResistance
    
    public double getAmperage(){//get current Amperage 
    
        return circuitAmperage; 
    }//eomethod getAmperage
    
    //======================Computation methods     
    public double findVoltage(){
        // calculate for voltage  
        return this.circuitResistance * this.circuitAmperage; // E = I*R
    }//eomethod findVoltage
    
    public double findResistance() {
        // calculate for resistance 
       return this.circuitVoltage / this.circuitAmperage; // R = V/I
    }//eomethod findResistance

    public double findAmperage() {
        // calculate for amperage 
        return this.circuitVoltage / this.circuitResistance; // I =V/R
    }//eomethod findAmperage
    
    public double findWatt(){
        //find wattage consumed by the circuit 
        double wattage = this.circuitAmperage * this.circuitVoltage;// W = V*I
        //since the values of V & I are by default 0 there is no need to 
        //assign a 0 value to wattage in the event this was called before the 
        //voltage and amperage are updated
        //wattage -  will return a value of if either is not changed
        return wattage; 
    }//eomethod findWatt

    //toString has been overridden from the Object class. It has been altered to 
    //display all the variables and the circuit wattage. 
    @Override
    public String toString(){
        //display the circuit data in String format 
        String circuitString = "Voltage: "+this.circuitVoltage+" Amperage: "+this.circuitAmperage
                +" Resistance: "+this.circuitResistance+" Wattage: "+ findWatt(); 
        return circuitString; 
    }//eomethod toString

}//EOCLASS 
