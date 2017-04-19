import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/**
 * Created by Thilukshan on 3/8/2017.
 *
 * I declare that the attached assignment is my own work in accordance
 * with Seneca Academic Policy. No part of this assignment has been
 * copied manually or electronically from any other source (including web
 * sites) or distributed to other students.
 * Name: Thilukshan Udayakumar Student ID: 108796160
 */
public class ThilukshanA2 extends JPanel implements ActionListener {

    //Text entry that stores the raw DNA sequence
    private JTextArea rawSeq;

    //Scrollbar for rawSeq text area
    private JScrollPane scrollSeq;

    //Drop down list to choose number of characters perl line
    private JComboBox numCharacters;

    //Check box to choose whether to display by groups of 10 with a space
    private JCheckBox groupBy10;

    //Radio button to toggle between upper case and lower case
    private ButtonGroup group;
    private JRadioButton upperCase;
    private JRadioButton lowerCase;

    //Button to process sequence
    private JButton processSeq;

    //Button to reset application to default
    private JButton resetApp;

    //Text area that will show the formatted sequence
    private JTextArea formattedSeq;

    //Scrollbar for formattedSeq text area
    private JScrollPane scrollFormSeq;

    //Text area that will show the base statistics
    private JTextArea baseStats;

    //Scrollbar for base stats
    private JScrollPane baseScroll;

    //Variables for input processing
    private String userInput;
    private int charValue;
    private String newString;
    private String[] charSelection = {"40", "50", "60", "70"};
    private boolean groupSelection;
    private int numA;
    private int numG;
    private int numC;
    private int numT;
    private int totalBase;
    private boolean upper;


    public ThilukshanA2() {
        //Constructor that creates a layout
        JPanel workSpace = new JPanel();
        workSpace.setLayout(new BoxLayout(workSpace, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(1800, 1000));
        workSpace.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DNA Sequence Formatter",
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP,
                new java.awt.Font("Imprint MT Shadow", 0, 14)));

        //Dimensions for spacers throughout the GUI
        Dimension minSize = new Dimension(5, 30);
        Dimension prefSize = new Dimension(5, 30);
        Dimension maxSize = new Dimension(10, 30);

        //Adds a filler to format the spacing between components and creates a label to explain to the user what is
        //required
        workSpace.add(new Box.Filler(minSize, prefSize, maxSize));
        workSpace.add(new Label("Please enter a raw DNA sequence."));
        this.add(workSpace, BorderLayout.CENTER);


        //Creates text area with scrolling capabilities
        rawSeq = new JTextArea();
        scrollSeq = new JScrollPane(rawSeq);
        scrollSeq.setPreferredSize(new Dimension(1050, 150));
        workSpace.add(scrollSeq);
        workSpace.add(new Box.Filler(minSize, prefSize, maxSize));

        //Creates drop down list with the appropriate values
        workSpace.add(new Label("Please select the number of characters per line."));
        numCharacters = new JComboBox(charSelection);

        //Sets default value for drop down menu
        numCharacters.setSelectedIndex(2);
        numCharacters.setAlignmentX(RIGHT_ALIGNMENT);
        workSpace.add(numCharacters);
        workSpace.add(new Box.Filler(minSize, prefSize, maxSize));


        //Creates a checkbox
        groupBy10 = new JCheckBox("Display sequence in groups of 10 characters separated by " +
                "a space");
        workSpace.add(groupBy10);
        workSpace.add(new Box.Filler(minSize, prefSize, maxSize));


        //Creates the upper case and lower case radio buttons to choose the type of output
        upperCase = new JRadioButton("Upper Case");
        lowerCase = new JRadioButton("Lower Case");

        //Creates a group for radio buttons so only one or the other can be selected a time
        group = new ButtonGroup();
        group.add(upperCase);
        group.add(lowerCase);
        workSpace.add(upperCase);
        workSpace.add(lowerCase);
        lowerCase.setSelected(true);
        workSpace.add(new Box.Filler(minSize, prefSize, maxSize));


        //Button used to process the raw sequence
        processSeq = new JButton("Process Sequence");
        workSpace.add(processSeq);
        workSpace.add(new Box.Filler(minSize, prefSize, maxSize));


        //Display area that will show the formatted text
        workSpace.add(new Label("Formatted Sequence:"));
        formattedSeq = new JTextArea();
        scrollFormSeq = new JScrollPane(formattedSeq);
        scrollFormSeq.setPreferredSize(new Dimension(250, 150));
        workSpace.add(scrollFormSeq);
        workSpace.add(new Box.Filler(minSize, prefSize, maxSize));


        //Display area that will show the calculated stats
        workSpace.add(new Label("Base Stats:"));
        baseStats = new JTextArea();
        baseScroll = new JScrollPane(baseStats);
        baseScroll.setPreferredSize(new Dimension(250, 150));
        workSpace.add(baseScroll);
        workSpace.add(new Box.Filler(minSize, prefSize, maxSize));


        //Button used to reset the application
        resetApp = new JButton("Reset");
        workSpace.add(resetApp);
        workSpace.add(new Box.Filler(minSize, prefSize, maxSize));


        //Setting ActionCommands and adding ActionListeners so the program will know when buttons are pressed
        numCharacters.addActionListener(this);
        upperCase.setActionCommand("upper_case");
        lowerCase.setActionCommand("lower_case");
        upperCase.addActionListener(this);
        lowerCase.addActionListener(this);
        processSeq.setActionCommand("Process_seq");
        processSeq.addActionListener(this);
        resetApp.setActionCommand("Reset_pressed");
        resetApp.addActionListener(this);

    }

    public void actionPerformed(ActionEvent e) {
        //This function will tell the GUI the actions that need to be implemented when a button is pressed

        //Gets the input sequence from the text area
        userInput = rawSeq.getText();

        //Regex prep to determine if the wrong character is present
        Pattern p = Pattern.compile("[^ACGTacgt\r\n]");
        Matcher m = p.matcher(userInput);
        charValue = numCharacters.getSelectedIndex();
        groupSelection = groupBy10.isSelected();

        //Checks to see if the upper case or lower case button was selected
        if (e.getActionCommand() == "upper_case") {
            upper = true;
        } else if (e.getActionCommand() == "lower_case") {
            upper = false;
        }

        //Checks to see if the process sequence button was pressed
        if (e.getActionCommand() == "Process_seq") {

            //If the sequence entered is not satisfactory an warning message will be shown,
            //else processing will take place
            if (m.find()) {
                JOptionPane.showMessageDialog(null, "Invalid sequence, sequence contains values other than " +
                        "a,c,g,t and newline", "Error Message", JOptionPane.ERROR_MESSAGE);
            } else {
                //Sets variable values so if process sequence is pressed again the values from the previous processing
                //are not included in calculations
                numA = 0;
                numG = 0;
                numC = 0;
                numT = 0;
                totalBase = 0;

                //Removes newline so only bases remain
                userInput = userInput.replace("\n", "").replace("\r", "");

                //Calls the necessary methods to process sequence
                formattedSeq.setText((formatSequence(userInput, charValue, groupSelection, upper)));
                baseStats.setText(countBases(userInput));
            }
        }

        /**
         * Checks to see if the reset button was pressed and will create a popup window to confirm the selection
         * If the user confirms the selection, then all components are reset. However if the user selects "No" or "Cancel"
         * nothing will happen.
         */
        if (e.getActionCommand() == "Reset_pressed") {
            Object[] options = {"Yes", "No", "Cancel"};
            int n = JOptionPane.showOptionDialog(null, "Are you sure you want to reset everything",
                    "Reset Confirmation", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[1]);

            if (n == 0) {
                rawSeq.setText("");
                numCharacters.setSelectedIndex(2);
                groupBy10.setSelected(false);
                group.clearSelection();
                formattedSeq.setText("");
                baseStats.setText("");
                upperCase.setSelected(false);
                lowerCase.setSelected(true);
                upper = false;

            }
        }

    }

    public String formatSequence(String sequence, int numChar, boolean group, boolean isUpper) {
        //Method will format sequence depending on the specifications from the user.

        //Counters to keep track of characters per group of 10 and characters per line.
        int groupCounter = 0;
        int lineCounter = 0;

        //Using StringBuilder to create new sequence so that if a large sequence needs to be processed it will not take
        //up a lot of memory
        StringBuilder newString = new StringBuilder();

        //Checks to see if groupBy10 was selected, if it was the new sequence will have a space after every 10 bases
        if (group == true) {
            for (int i = 0; i < sequence.length(); i++) {
                if (groupCounter == 10) {
                    newString.append(" ");
                    groupCounter = 0;
                }

                //If the necessary number of characters per line are reached then a newline character will be added
                //and the counter values will be reset. Otherwise each character will be added to the new String.
                if (lineCounter >= Integer.parseInt(charSelection[numChar])) {
                    newString.append("\n");
                    lineCounter = 0;
                    groupCounter = 0;
                } else {
                    newString.append(sequence.charAt(i));
                    System.out.println(i);
                    groupCounter++;
                    lineCounter++;
                }
            }
        } else {
            //If groupBy10 is not selected, the method will only keep track of characters per line

            for (int i = 0; i < sequence.length(); i++) {
                if (lineCounter == Integer.parseInt(charSelection[numChar])) {
                    newString.append("\n");
                    lineCounter = 0;
                }
                newString.append(sequence.charAt(i));
                lineCounter++;
            }
        }

        //Depending on whether the user wants upper or lower case, the appropriate action will be taken.
        if (isUpper == true) {
            return (newString.toString().toUpperCase());
        } else {
            return (newString.toString().toLowerCase());
        }
    }

    //Method to determine how many bases are present in the sequence, percentage of the bases and total bases.
    public String countBases(String sequence) {
        double percentA;
        double percentG;
        double percentT;
        double percentC;

        //Checks to see which base is present and increases the respective counter and counter for total bases
        for (int n = 0; n < sequence.length(); n++) {
            if (('a' == (sequence.charAt(n))) || ('A' == (sequence.charAt(n)))) {
                numA++;
                totalBase++;
            } else if (('g' == (sequence.charAt(n))) || ('G' == (sequence.charAt(n)))) {
                numG++;
                totalBase++;
            } else if (('c' == (sequence.charAt(n))) || ('C' == (sequence.charAt(n)))) {
                numC++;
                totalBase++;
            } else if (('t' == (sequence.charAt(n))) || ('T' == (sequence.charAt(n)))) {
                numT++;
                totalBase++;
            }
        }

        //Calculates percentage of bases
        percentA = (100 * numA / totalBase);
        percentG = (100 * numG / totalBase);
        percentT = (100 * numT / totalBase);
        percentC = (100 * numC / totalBase);

        //Returns formatted string of the stats to be printed.
        return ("Number of A: " + numA + "\nPercent A: " + percentA + "%" + "\nNumber of G: " + numG + "\nPercent G: " + percentG + "%" +
                "\nNumber of C: " + numC + "\nPercent C: " + percentC + "%" + "\nNumber of T: " + numT + "\nPercent T: " + percentT + "%" +
                "\nTotal Number of Bases: " + totalBase);


    }

}
