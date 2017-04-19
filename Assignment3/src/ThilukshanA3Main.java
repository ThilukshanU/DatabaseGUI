import javax.swing.*;

/**
 * Created by Thilukshan on 3/22/2017.
 */
public class ThilukshanA3Main {
    public static void main(String[] args){

        //Set the look and feel of the GUI
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
         We're going to use the runnable class that is related to MultiThreading
         MultiThreading is technically outside the scope of the course. However, you can reuse this code for your
         assignment.
         */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            //Override the run method inherited from Runnable
            public void run(){
                createAndShowGUI();
            }
        });

    }

    /**
     * This method instantiates and sets up the JPanel which is ThilukshanA2
     */
    private static void createAndShowGUI(){
        //create a JFrame that is going to be the outer container for the GUI
        JFrame frame = new JFrame();
        JTabbedPane tabbedPane = new JTabbedPane();

        //specify what happens to the program when the user closes the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //instantiate and set up the content pane (JPanel - ThilukshanA2)
        JComponent myPanelA2 = new ThilukshanA2();
        JComponent myPanelA3 = new ThilukshanA3();

        tabbedPane.addTab("Sequence Editor", myPanelA2);
        tabbedPane.addTab("Database Connector", myPanelA3);

        //associate myPanel (has the content) with the JFrame container
        frame.setContentPane(tabbedPane);

        //make the GUI visible
        frame.pack();
        frame.setVisible(true);
    }
}
