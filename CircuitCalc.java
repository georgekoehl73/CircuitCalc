/*
Program: Graphic User Interface- Circuit Calc
This. CircuitCalc.java 
Date: 11/29/14
Author: G.Koehl
Purpose: This Class takes care of the construction and feature implementations 
of the Circuit Calc interface. This class contains all the methods and the subclasses 
needed for proper operation and error checking for the GUI. 
The userInput for this program is limited to the on-screen keypad. This is to 
prevent non-supported data input for the calculations. There are several stages 
of error checking to prevent incorrect or incomplete data from being passed to 
the Circuit Object(which takes care of all circuit calculations). User input that
does not conform with expected inputs is altered or cleared to prevent the 
program from behaving in unexpected ways. The input that is returned to the 
input fields is limited to a selected number of characters to prevent the need to 
scroll back to the beginning of the number.
For the sake of accuracy a records area has been added 
that keeps records of all calculations and displays the calculated values in 
their original accuracy. This is so the user has access to whatever level of answer
accuracy they need for their circuit. 
Last Minute alteration- 
In the interest of user-friendliness, I opened the text-fields so that people who 
use the keyboard more can input numbers without needing to use the screen input. 
I added error catching code to prevent the system from crashing on none supported 
values added to the text fields.  I originally was using a try/catch for error 
checking of the string input from the textfields, but realized we have not covered 
those in class and had to devise another method to allow maximum user friendliness 
yet stay within the constrains of the classroom rules. 
The last update was to add focusListeners to the textfields that allows the input
selectors(which are needed for the keypad buttons to update the proper field, to move
to the selected field, if the user moves from field to field with their mouse. 
*/
package circuitcalcgui;
//Libraries imported from the API 
import javax.swing.JFrame; 
import javax.swing.JLabel; 
import javax.swing.JTextField; 
import javax.swing.JTextArea;
import javax.swing.JPanel; 
import javax.swing.JButton; 
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup; 
import javax.swing.JOptionPane; 
import javax.swing.JScrollPane; 
import java.awt.GridLayout; 
import java.awt.BorderLayout; 
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent; 
import javax.swing.JSlider; 
import javax.swing.event.ChangeListener; 
import javax.swing.event.ChangeEvent; 
import javax.swing.border.EtchedBorder; 
import java.awt.event.FocusListener; 
import java.awt.event.FocusEvent; 

/**
 *
 * @author George
 */
public class CircuitCalc extends JFrame{
    
    //==========Variables and Constants 
    private int recordCounter = 1; //keep running total of recorded calculations
    protected int displayTrimLength =VAS_INT; //default value for the accuracy display
    
    static final int VAS_MIN = 6; 
    static final int VAS_MAX = 12; 
    static final int VAS_INT = 9; 
    
    private final int FRAME_WIDTH =620; //value for frame width
    private final int FRAME_HEIGHT =320; //value for frame height 
    private final int TEXT_AREA_ROWS = 6; // value for textarea row count 
    private final int TEXT_AREA_COLUMNS =55; //value for textarea column count 
    private final int DEFAULT_WIDTH = 8; //to set Default TextField widths
    private final Circuit CALC_CIRCUIT; //default circuit to use for calculations 
    
    //=========Initialize SWING COMPONENTS CONSTANTS 
    private final JLabel VOLTAGE_LABEL = new JLabel("<html><h3 >&nbsp;  Voltage:</h3></html> ");  //UI label for Voltage field 
    private final JLabel RESISTANCE_LABEL = new JLabel("<html><h3 >&nbsp; Resistance:</h3></html> "); //UI label for Resistance field
    private final JLabel AMPERAGE_LABEL = new JLabel("<html><h3 >&nbsp; Amperage:</h3></html> "); //UI lable for Amperage field 
    private final JRadioButton INPUT_VOLT = new JRadioButton("V"); //input lock radio button 
    private final JRadioButton INPUT_OHM = new JRadioButton("R"); //input lock radio button 
    private final JRadioButton INPUT_AMP = new JRadioButton("I"); //input lock radio button 
    private final JTextField VOLT_FIELD = new JTextField(DEFAULT_WIDTH);//volt text field
    private final JTextField OHM_FIELD = new JTextField(DEFAULT_WIDTH); //ohm text field 
    private final JTextField AMP_FIELD = new JTextField(DEFAULT_WIDTH); //amp text field 
    private final JTextArea CALC_RECORD = new JTextArea(TEXT_AREA_ROWS,TEXT_AREA_COLUMNS);//record field
    private final JSlider DISPLAY_ACCURACY = new JSlider(JSlider.VERTICAL,VAS_MIN,VAS_MAX,VAS_INT); //accuracy selection
    private final JLabel TF_OUTPUT = new JLabel ("  Output "); 
    private final JLabel TF_ACCURACY = new JLabel("<html> &nbsp;Adjust<br>&nbsp;output<br>&nbsp;length</html> "); 
    //=========Initialize Swing Component Variable 
    
    private JButton calcButton; //for calc functions 
    private JButton clearButton; //reset circuit 
    private JButton backSpaceButton; //keypad backspace 
    private JButton infoButton; //for help message
    
     //==================Main Frame Constructor   
    public CircuitCalc(){
        //where the main frame for the gui will invoke methods to add components 
        CALC_CIRCUIT = new Circuit(); //make a new circuit object for use in the calculations
        createFieldData(CALC_CIRCUIT);//sets the starting values for the field text
        makeMainPanel(); //construct a main panel 
        setSize(FRAME_WIDTH,FRAME_HEIGHT); //set the size of the calc panel 
        setResizable(false); //prevent frame from being resized 
    }//eoConstructor NAC
    
    //============User Input fields for circuit calculator 
      private void createFieldData(Circuit circuit){
        //initialize each text field with values from the circuit 
        VOLT_FIELD.setHorizontalAlignment(JTextField.RIGHT);   
        VOLT_FIELD.setText("" + circuit.getVoltage());
        FocusListener voltListen = new VoltFocusListener(); 
        VOLT_FIELD.addFocusListener(voltListen); 
        
        OHM_FIELD.setHorizontalAlignment(JTextField.RIGHT);
        OHM_FIELD.setText("" + circuit.getResistance());
        FocusListener ohmListen = new OhmFocusListener(); 
        OHM_FIELD.addFocusListener(ohmListen); 
        
        AMP_FIELD.setHorizontalAlignment(JTextField.RIGHT);
        AMP_FIELD.setText("" + circuit.getAmperage()); 
        FocusListener ampListen = new AmpFocusListener(); 
        AMP_FIELD.addFocusListener(ampListen); 
       
    }//eomethod createFieldData
      
      
      //textfield focus listener classes 
      //these allow the user to simply click on the field and the 
      //input selector will move to the selected field 
      //these were a last minute update to increase usability 
      class VoltFocusListener implements FocusListener{

        @Override
        public void focusGained(FocusEvent fe) {
            INPUT_VOLT.setSelected(true);
        }

        @Override
        public void focusLost(FocusEvent fe) {
           //do nothing on loss of focus
        }
      }//EOCLASS VoltFocusListener
      
       class AmpFocusListener implements FocusListener{

        @Override
        public void focusGained(FocusEvent fe) {
            INPUT_AMP.setSelected(true);
        }

        @Override
        public void focusLost(FocusEvent fe) {
           //do nothing on loss of focus
        }
      
      }//EOCLASS AmpFocusListener
      
        class OhmFocusListener implements FocusListener{

        @Override
        public void focusGained(FocusEvent fe) {
            INPUT_OHM.setSelected(true);
        }

        @Override
        public void focusLost(FocusEvent fe) {
           //do nothing on loss of focus
        }
      }//EOCLASS OhmFocusListener
      
    //======================GUI PANELS 
    private void makeMainPanel(){
        //main panel for the project-using Borderlayout 
        JPanel mainPanel = new JPanel(new BorderLayout());//use the borderlayout 
        mainPanel.add(makeUIPanels(), BorderLayout.WEST); //add the user input panel 
        mainPanel.add(makeKeyPad(),BorderLayout.CENTER);//keypad to the center 
        mainPanel.add(makeCalcRecord(),BorderLayout.SOUTH); //records at the bottom of the panel 
        
        add(mainPanel); //add the main panel to the GUI Frame (CircuitCalc)
    }//eomethod makeMainPanel
    
   //=====================User Input Fields and Field controls 
    private JPanel makeUIPanels(){
        JPanel UIFields = new JPanel(new BorderLayout()); //make panel with borderlayout 
        UIFields.add(makeAccuracy(),BorderLayout.WEST); //add slider to the left 
        UIFields.add(makeInputFields(),BorderLayout.CENTER);//add input fields to the center 
        
        return UIFields; //return constructed panel 
    }
    
     private JPanel makeAccuracy(){
    
        JPanel accuracyPanel = new JPanel(new GridLayout(2,1));//make a panel with gridlayout 
        accuracyPanel.setBorder(new EtchedBorder());//set a border around the components
        accuracyPanel.add(TF_ACCURACY); //add the label for the user 
        //construct a new ChangeLister for slider 
        ChangeListener listener = new AccuracyListener();
        DISPLAY_ACCURACY.addChangeListener(listener);//attach listener to slider object
        DISPLAY_ACCURACY.setMajorTickSpacing(1);//set spacing for ticks
        DISPLAY_ACCURACY.setPaintTicks(true);//shoe the ticks 
        DISPLAY_ACCURACY.setPaintLabels(true);//show the number
        accuracyPanel.add(DISPLAY_ACCURACY); //attach slider to the panel
        return accuracyPanel; //return constructed panel 
    }//eomethod makeAccuracy
    
    class AccuracyListener implements ChangeListener{//changelistener for the slider 

        @Override
        public void stateChanged(ChangeEvent ce) {
         
            displayTrimLength = DISPLAY_ACCURACY.getValue(); //update TrimLength when slider moves 
        }
    }//EOCLASS 
    
    private JPanel makeInputFields(){
    //Panel that will hold the labels and textfields
        JPanel inputFields = new JPanel();
        
        INPUT_VOLT.setSelected(true);//default selection when a new CircuitCalc is made
        //button group makes logical connections between all the buttons assigned to the group
        //this prevents the linked buttons to have multiple selections at the same time  
        ButtonGroup inputLock = new ButtonGroup(); //make a button group of the input lock 
        inputLock.add(INPUT_VOLT); //add to button group
        inputLock.add(INPUT_OHM); //add to button group
        inputLock.add(INPUT_AMP); //add to button group
        
        //user input panel construction 
        inputFields.setLayout(new GridLayout(4,3));//
        //VOLTAGE INPUT AREA 
        inputFields.add(VOLTAGE_LABEL); //Label 
        inputFields.add(INPUT_VOLT); //Radio Button
        inputFields.add(VOLT_FIELD); //TextField 
        //AMPERAGE INPUT AREA 
        inputFields.add(AMPERAGE_LABEL); //Label 
        inputFields.add(INPUT_AMP); //Radio Button
        inputFields.add(AMP_FIELD); //TextField 
        //RESISTANCE INPUT AREA 
        inputFields.add(RESISTANCE_LABEL); //Label 
        inputFields.add(INPUT_OHM); //Radio Button
        inputFields.add(OHM_FIELD); //TextField 
        //OMNI-BUTTON INPUT AREA 
        createInfoButton(); //create button 
        inputFields.add(infoButton); //add to panel 
        createClearButton(); //create button 
        inputFields.add(clearButton); //add to panel 
        createCalcButton(); //create button 
        inputFields.add(calcButton); //add to panel 
        return inputFields; //return the constructed panel 
    }//eomethod makeInputFields
    
    private JPanel makeKeyPad(){
        //User input for this program is restricted to input from 
        //the keypad only. This is to help limit the amount improper user input
        //Create a new JPanel for the keypad buttons to be placed 
        JPanel keyPad = new JPanel(new GridLayout(4,3));
        keyPad.add(makeKeyPadButton("7"));//make keypad button using the method 
        keyPad.add(makeKeyPadButton("8"));//make keypad button using the method 
        keyPad.add(makeKeyPadButton("9"));//make keypad button using the method 
        keyPad.add(makeKeyPadButton("4"));//make keypad button using the method 
        keyPad.add(makeKeyPadButton("5"));//make keypad button using the method 
        keyPad.add(makeKeyPadButton("6"));//make keypad button using the method 
        keyPad.add(makeKeyPadButton("1"));//make keypad button using the method 
        keyPad.add(makeKeyPadButton("2"));//make keypad button using the method 
        keyPad.add(makeKeyPadButton("3"));//make keypad button using the method 
        keyPad.add(makeKeyPadButton("."));//make keypad button using the method 
        keyPad.add(makeKeyPadButton("0"));//make keypad button using the method 
        backSpaceButton = new JButton("<<");//make backspace button
        ActionListener bslistener = new KeyPadBackSpaceListener();//make a backspace listener 
        backSpaceButton.addActionListener(bslistener);//add listener to the backspace button
        keyPad.add(backSpaceButton); //add the button to the keyPad panel
        return keyPad; //return the constructed panel 
    }//eomethod makeKeyPad
    
    //keep records of all past calculations 
    private JPanel makeCalcRecord(){
        
        JPanel calcRecords = new JPanel(); //create a panel for the textArea
        JScrollPane scrollRecords = new JScrollPane(CALC_RECORD); //Scroll pane with text_area embedded 
        calcRecords.add(scrollRecords); //add Scroll pane to the panel
        return calcRecords; //return constructed panel 
    }//eomethod makeCalcRecord

    //====================KEYPAD Buttons 
    //this method will make and customize a button and its listener based on the
    //"digit"\character that is passed to the method.
    private JButton makeKeyPadButton(String digit){
        
        JButton button = new JButton(digit); //make a button with text matching digit
        ActionListener kplistener = new KeyPadButtonListener(digit);//make a listener passing digit through 
        button.addActionListener(kplistener);//add the listener to the button
        return button;//return the button to the panel which called for its construction 
    }//eomethod makeKeyPadButton
    
    class KeyPadButtonListener implements ActionListener{
        
         String buttonDigit; //variable to assign value to the button 
        
        public KeyPadButtonListener(String digit){
        
            buttonDigit = digit; //assign passed string the the buttonDigit 
        }//eoconstructor String

        @Override
        public void actionPerformed(ActionEvent ae) {
            //with three fields that the button can update
            //the button has to have a way to determine which 
            //field to update 
          
            if (INPUT_VOLT.isSelected())//check for selected radio button 
            {
                setButtonFunction(VOLT_FIELD, this.buttonDigit);
            }
            else if (INPUT_OHM.isSelected())//check for selected radio button 
            {
                setButtonFunction(OHM_FIELD, this.buttonDigit);
            }
            else if (INPUT_AMP.isSelected())//check for selected radio button 
            {
                setButtonFunction(AMP_FIELD, this.buttonDigit); 
            }
        }//eomethod actionPerformed
    }//EOCLASS KeyPadButtonListener
    
    private void setButtonFunction(JTextField field , String buttonDigit){
        //0.0 is a default value for the new circuit
        if (field.getText().equals("0.0"))//if current value matches remove the value and update the field text
        {
            field.setText(buttonDigit);//remove the default text and update the field text with button value 
        }
        else
        {
            field.setText(field.getText() + buttonDigit); //append the digit to existing field text 
        }
        field.requestFocus();//return focus to selected field 
    }//eomethod setButtonFunction
    
    //listener for the backspace key
    //since user input is constrained to the keypad a backspace button
    //is needed to allow the user a chance to alter and erase any erroneous input
    class KeyPadBackSpaceListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            //check for which input field is selected 
            //call the format method and then return 
            //focus to the selected field (see the method) 
         if (INPUT_VOLT.isSelected())//check if radioButton is currently selected 
            {
                formatBackSpaceText(VOLT_FIELD); //call for backspace 
            }
            else if (INPUT_OHM.isSelected())//check if radioButton is currently selected 
            {
                formatBackSpaceText(OHM_FIELD); //call for backspace 
            }
            else if (INPUT_AMP.isSelected())//check if radioButton is currently selected 
            {
                formatBackSpaceText(AMP_FIELD); //call for backspace 
            }
        }//eomethod actionPerformed
    }//EOCLASS KeyPadBackSpaceListener
    
    
    
    private void formatBackSpaceText(JTextField field){
        String currentString =""; //initialize the String variable 
        int stringLength; //initialize the stringLenght variable 
        final int TRIM_LENGTH = -1; //the number of places to remove from the string 
        
        currentString = field.getText(); //set current string to calling field text 
        stringLength = currentString.length(); //record the length of the passed string 
        
        //test that there is a value in the currentString that needs to be shortened 
        if (stringLength >0){//only backspace if the length is greater than 0
            //reassign the string to currentString variable -1 character
            currentString = currentString.substring(0, stringLength + TRIM_LENGTH);
            field.setText(currentString); //update the field text 
        }
        field.requestFocus();//return to the calling field 
        //This is to prevent the need for the user to have to reselect a field 
        //between button clicks. This was un-needed on the touchscreen device 
        //but the mouse-click operates differently and the program had to be 
        //altered to accommodate the difference. 
    }//eomethod formatBackSpaceText

    //===================Append Calculation to records 
    private void recordCircuitCalc(Circuit circuit){
        //add calculations to the text area
        String calcRecord =""+ recordCounter; //make a string that holds the current record count
        //append the calculation results to the record text area 
        CALC_RECORD.append("Record: "+calcRecord +" "+ circuit.toString()+"\n");
        recordCounter++;//increment for the next record 
    }//eomethod recordCircuitCalc
    


    //===================Clear Button 
    private void createClearButton(){
    
        clearButton = new JButton("Clear"); //make a clear button
        ActionListener listener = new ClearCircuitListener(); //make a listener for clear button
        clearButton.addActionListener(listener);//add listener 
    }//eomethod createClearButton
    
    class ClearCircuitListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
        clearCircuit(CALC_CIRCUIT); //call the clear circuit method with circuit object 
        }
    }//EOCLASS ClearCircuitListener
    
        protected void clearCircuit(Circuit circuit){
        //change the circuit instance variables back to 0 
        circuit.setVoltage(0);
        circuit.setResistance(0);
        circuit.setAmperage(0);
        
        //update textFields with reset values from the circuit object 
        VOLT_FIELD.setText("" + circuit.getVoltage());
        OHM_FIELD.setText("" + circuit.getResistance());
        AMP_FIELD.setText("" + circuit.getAmperage()); 
        INPUT_VOLT.setSelected(true); //change the input selection back to default 
    }//eomethod clearCircuit
 
    //==================Calc Button 
    private void createCalcButton(){
    
        calcButton = new JButton("Calc");//button for calculation call 
        ActionListener listener = new CalcCircuitListener(); //make a listener for the calc button 
        calcButton.addActionListener(listener); //add listener to button 
    }//eomethod createCalcButton
    
    class CalcCircuitListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            calcCircuit(CALC_CIRCUIT); //call the calcCirucit method with the circuit object 
        }
    }//EOCLASS CalcCircuitListener
    
    protected void calcCircuit(Circuit circuit){
        
        //filter out all non-supported characters from text fields 
        //this was needed beacuse the input fields where made editable
        //needed to prevent the program from crashing on unsupported input characters 
        conditionInput(VOLT_FIELD); 
        conditionInput(AMP_FIELD); 
        conditionInput(OHM_FIELD);
        
        //catch empty fields set text to = 0 
        //empty field is one where the user has backed out all values and the 
        //passed string has a length of 0- this was creating errors during testing
        if(VOLT_FIELD.getText().length() == 0){VOLT_FIELD.setText("0");}
        if(AMP_FIELD.getText().length() ==0){AMP_FIELD.setText("0");}
        if(OHM_FIELD.getText().length() == 0){OHM_FIELD.setText("0");}
        
        
        
        //check for multiple "." in input fields
        //users are not limited to the number of times they add a dot to their 
        //inputed value- created a method to trim the string if more than one 
        //dot is found in the string. 
        //This is to prevent errors when parsing for double value 
        catchDoubleDot(VOLT_FIELD); 
        catchDoubleDot(AMP_FIELD); 
        catchDoubleDot(OHM_FIELD); 
        
        
         
        //Once the userInput strings have been validated and conditioned
        //parse the string input from fields to doubles 
        double upDateVoltage = Double.parseDouble(VOLT_FIELD.getText());  
        double upDateWattage = Double.parseDouble(OHM_FIELD.getText()); 
        double upDateAmperage = Double.parseDouble(AMP_FIELD.getText());  
        
        
        
        //update the circuit variables with parsed input
        circuit.setVoltage(upDateVoltage);
        circuit.setResistance(upDateWattage);
        circuit.setAmperage(upDateAmperage);
        
        //1st test to see that atleast two values are present 
        //otherwise  bypass calculations and reset the circuit variables 
        //this test will pass if two or three field values are present 
        //and there are no reported error 
        if ((upDateVoltage > 0 && upDateWattage > 0) ||
            (upDateVoltage > 0 && upDateAmperage > 0) ||
            (upDateWattage > 0 && upDateAmperage > 0))
        {
            
            String workingString ="";//initialize String to hold field data for program output
            //Testing the input to see what calculations are needed 
            
            //solve for V (I & R) are given 
            if (circuit.getVoltage() == 0 && circuit.getResistance() > 0 && circuit.getAmperage() > 0)
            {
                workingString = workingString + circuit.findVoltage();//set to string to calculated value
                formatFieldOutput(VOLT_FIELD, workingString); //send string to be trimmed (if needed) 
                circuit.setVoltage(Double.parseDouble(workingString));//update the circuit object 
                INPUT_VOLT.setSelected(true); //this is a indicator to show the computed field 
            }
            //solve for R (V & I) are given
            else if (circuit.getResistance() == 0 && circuit.getVoltage() > 0 && circuit.getAmperage() > 0 )
            {
                workingString = workingString + circuit.findResistance();//set to string to calculated value
                formatFieldOutput(OHM_FIELD,workingString); //send string to be trimmed (if needed) 
                circuit.setResistance(Double.parseDouble(workingString));//update the circuit object 
                INPUT_OHM.setSelected(true);//this is a indicator to show the computed field 
            }
            //solve for I (V & R) are given
            else if (circuit.getAmperage() == 0 && circuit.getVoltage() > 0 && circuit.getResistance() > 0 )
            {
                workingString = workingString + circuit.findAmperage(); //set to string to calculated value
                formatFieldOutput(AMP_FIELD,workingString); //send string to be trimmed (if needed) 
                circuit.setAmperage(Double.parseDouble(workingString));//update the circuit object 
                INPUT_AMP.setSelected(true); //this is a indicator to show the computed field 
            }
            //all three fields contain values (call the refactor menu for user to decide what value to calculate) 
            else if (circuit.getVoltage() > 0 && circuit.getResistance() > 0 && circuit.getAmperage() > 0)
            {//if all the fields contain data call the refactor menu 
                //Refactor Circuit pop-up menu 
                Object[] possibleValues = { "Voltage", "Amperage","Resistance" };
                Object selectedValue = JOptionPane.showInputDialog(null,
                "Find: ", "Refactor Circuit",
                JOptionPane.QUESTION_MESSAGE, null,
                possibleValues, possibleValues[0]);//a whole bunch of arguments for the refactor menu JOptionPane

                //test the result of the refactor menu 
                String userSelection = (String)selectedValue; //set userSelection to selected menu option
                if (userSelection.equals(possibleValues[0]))//use possibleValues array as test condition
                    //this way changes to the values only need to be set one place 
                {
                    workingString = workingString + circuit.findVoltage();//set to string to calculated value
                    formatFieldOutput(VOLT_FIELD, workingString); //send string to be trimmed (if needed) 
                    circuit.setVoltage(Double.parseDouble(workingString));//update the circuit object 
                    INPUT_VOLT.setSelected(true);//this is a indicator to show the computed field 

                }
                else if (userSelection.equals(possibleValues[1]))//use possibleValues array as test condition
                {
                    workingString = workingString + circuit.findAmperage(); //set to string to calculated value
                    formatFieldOutput(AMP_FIELD,workingString); //send string to be trimmed (if needed) 
                    circuit.setAmperage(Double.parseDouble(workingString));//update the circuit object 
                    INPUT_AMP.setSelected(true);//this is a indicator to show the computed field 
                }

                else if (userSelection.equals(possibleValues[2]))//use possibleValues array as test condition
                {
                    workingString = workingString + circuit.findResistance();//set to string to calculated value
                    formatFieldOutput(OHM_FIELD,workingString); //send string to be trimmed (if needed) 
                    circuit.setResistance(Double.parseDouble(workingString));//update the circuit object 
                    INPUT_OHM.setSelected(true);//this is a indicator to show the computed field 
                }
            }
            //on successful calculation- update record text area 
            recordCircuitCalc(circuit); //append the calculation to the record text area 
        }
        else//catch a single input or zero calculator call 
        {
            clearCircuit(circuit); //reset circuit variables to 0 on bad calc button call
        }
        
        clearButton.requestFocus(); //move focus to the clear 
    }//eomethod calcCircuit
    
    //All input returned to the fields are trimmed to a default length to prevent 
    //value over flow in the TextField area 
    private void formatFieldOutput(JTextField field, String workingString)
    {
        int  defaultTrim = displayTrimLength; //the maximum length to return formatted text
        int trimLength = workingString.length(); //set trim length to length of the string
        //check if the length is greater than default trim length 
        if (trimLength > defaultTrim )
        {//change to the default length otherwise use the string length 
            trimLength = defaultTrim; 
        }
        //format the string to be within the default length 
        String tempString =  workingString.substring(0,trimLength); 
        //remove the . from end of string if it is the last character of the string
        //this is just for making the output conform with convention
        if (tempString.endsWith("."))//check for a trimmed string that ends with a dot 
        {
            int dotTrim = -1; //amount to reduce the trimlength 
            tempString = tempString.substring(0, trimLength + dotTrim); //set trimlength to default - 1
        }
        //set the field text to the formatted string
        field.setText("" + tempString); 
    }//eomethod formatFieldOutput
    
    //find double "." in user input- if a second "." is found trim the string to length
    //this will update the field before the parse to double 
    private void catchDoubleDot(JTextField field){

    int dotCount = 0; //default number of expected dots 
    int dotPosition = 0; 
    String stringDotParse = field.getText(); 
    for (int index = 0 ; index < stringDotParse.length(); index++)
    {
        char currentChar = stringDotParse.charAt(index);
        if (currentChar == '.')//check for a dot at each character position of the String
        {
            dotCount++; //increment if a dot is found 
            if (dotCount == 2) //on the second found dot set the dotPosition (ignore any additional found dots) 
            {
                    dotPosition = index; //any additional dots will be trimmed by default 
            }
        }
    }
    if (dotCount > 1)//more than the supported number of dots found 
    {   
        if (dotPosition == 1)//Guard against special condition- if the input string starts with ".." 
        {
            stringDotParse = "0"; //if two dots are first two places in field set field back to zero 
        }
        else//just additional dots found in the user input
        {
            stringDotParse = stringDotParse.substring(0,dotPosition);//trim the string to the second found dot 
        }
    }
    //update the field text before the double parse
    field.setText(""+stringDotParse); 
}//eomethod catchDoubleDot
    
    //this is the final method to allow the text fields to be user accessible 
    //from sources other than the onscreen keypad 
    //this method will filter out all non-supported characters from the 
    //current textfield string and re-write the string with the supported 
    //characters only. 
    //This was needed to make the input section of the program 
    //safe from trying to parse bad data and crashing the program
    private void conditionInput(JTextField field){
        
        //supported characters 
        char dotChar = '.'; 
        char zeroChar = '0'; 
        char nineChar = '9'; 
        
        //have the system assign the int value for the supported characters 
        int dotINT = dotChar; //ASCII for the . sign
        int zeroINT = zeroChar; //ASCII for the 0 sign
        int nineINT = nineChar; //ASCII for the 9 sign
        
        //initialize the two needed strings 
        String workingString = field.getText(); 
        String conditionedString =""; 
        
        //loop through all the characters that are in the textField 
        for (int index = 0; index <workingString.length() ; index++)
        {
            char testChar = workingString.charAt(index); //store the character 
            int testValue =  testChar;  //get the int value of the character 
            
            //if the value is within the range, write it to the string -discard the others 
            if (testValue >= zeroINT && testValue <= nineINT || testValue == dotINT)
            {
             conditionedString = conditionedString + testChar;  
            }
        }
        

        field.setText(conditionedString);//write the conditioned string the textfield 
    }//eomethod conditionInput

    
    //===============Info\Help Button 
    /*
        Decided to add a info/help button that give the user information 
        about how the circuit calculator was designed to be used. 
        This is to assist the user if they continue to have trouble using the 
        CircuitCalc program. It all contains a email address they can 
        use to contact the programmer if they have suggestion about improving 
        the program, bug reports, or questions on the operation of the program.
    */
    private void createInfoButton(){
        
        infoButton = new JButton("Help"); //make a button for help menu
        ActionListener help = new InfoButtonListener(); //create listener for help button
        infoButton.addActionListener(help); //add listener 
    }//eomethod createInfoButton
    
    class InfoButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent ae) {
            //display a info/help pop-up message for the user 
            JOptionPane.showMessageDialog(null, "Circuit Helper 1.4 - Ohm's Law Calculator\n"
                    + "Enter two known elements of a circuit to find the third\n"
                    + "\n                       User Input Panels\n"
                    + "Select the radio button next to the field you want to update\n"
                    + "and then use the keypad to enter the values.\n"
                    + "Use the Calc button to find the missing value\n"
                    + "Use the Clear button to reset the circuit for new calculations\n"
                    + "Use the Backspace button [ << ] to remove values from the field\n"
                    + "\n                             Refactor Menu\n"
                    + "If you have an existing circuit, you can make a changes\n"
                    + "to the circuit and then use the Calc button to call the\n"
                    + "refactor menu to select what value you would like to find\n"
                    + "\n                           Records window\n"
                    + "The records window keeps track of all circuit calculations\n"
                    + "The records show the full computed value of calculated elements\n"
                    + "This allows the user to select the level of accuracy they need\n"
                    + "for their computations.\n"
                    + "The circuit's wattage is also recorded for each calculation\n"
                    + "\n                      Adjust Output Length Slider\n"
                    + "The output adjustment was added to allow the user the choice of\n"
                    + "the length of the output string that was reported in the element fields.\n"
                    + "The record area is not effected by this setting so the full length\n"
                    + "of the computed values will be available for the user if the selected\n"
                    + "output length proves to be insufficient for the desired accuracy.\n"
                    + "\n Written: G.Koehl 11/30/14\n Contact: GeorgeKoehl73@outlook.com\n");
        }//eomethod actionPerformed
    }//EOCLASS InfoButtonListener
    
   
    
    
    
}//EOCLASS
