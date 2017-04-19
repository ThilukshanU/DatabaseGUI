import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


/**
 * Created by Thilukshan on 3/22/2017.
 *
 * I declare that the attached assignment is my own work in accordance with Seneca Academic
 * Policy. No part of this assignment has been copied manually or electronically from any other
 * source (including web sites) or distributed to other students.
 * Name: Thilukshan Udayakumar Student ID: 108796160
 */
public class ThilukshanA3 extends JPanel implements ActionListener {

    //JPanel which contains the contents of the GUI
    private JPanel workSpace;

    //Variables that are used for spacers throughout the GUI
    private Dimension minSize;
    private Dimension prefSize;
    private Dimension maxSize;

    //Label for instructions
    private JLabel instructions;

    //Button Group that lists the data tables
    private ButtonGroup group;

    //Button to load the table
    private JButton loadTable;

    //JTable to store retrieved data in table format
    private JTable table;

    //ScrollPane that contains and displays the JTable
    private JScrollPane displayTableScroll;

    //Variables for processing
    private String tableName;
    private String selectedTableName;
    private Vector data;
    private Vector columnName;

    //Represents SQL connection
    java.sql.Connection connection = null;

    //Represents a SQL query
    Statement statement = null;

    //Represents rows returned by a query
    ResultSet resultSet = null;


    public ThilukshanA3() {

        //Constructor that creates the layout for the GUI
        workSpace = new JPanel();
        workSpace.setLayout(new BoxLayout(workSpace, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(1800, 1000));
        workSpace.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Database Table Displayer",
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP,
                new java.awt.Font("Imprint MT Shadow", 0, 14)));
        this.add(workSpace, BorderLayout.CENTER);


        //Dimensions for spacers throughout the GUI
        minSize = new Dimension(5, 30);
        prefSize = new Dimension(5, 30);
        maxSize = new Dimension(10, 30);

        workSpace.add(new Box.Filler(minSize, prefSize, maxSize));
        workSpace.add(new Box.Filler(minSize, prefSize, maxSize));
        instructions = new JLabel("Please select the table you wish to view");
        instructions.setAlignmentX(CENTER_ALIGNMENT);
        workSpace.add(instructions);
        workSpace.add(new Box.Filler(minSize, prefSize, maxSize));

        //Checks to see if JAR file was included
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Check to see if the JAR file was included.");
        }

        //Sets up login credentials
        String username = "bif";
        String password = "bif812";
        String databaseURI = "jdbc:mysql://localhost/biftest?user=" + username + "&password=" + password;

        //Flag that represents database connectivity
        boolean connected = false;

        //Attempt to login to database
        try {
            connection = DriverManager.getConnection(databaseURI, username, password);
            statement = connection.createStatement();
            connected = true;
        } catch (SQLException e) {
            e.printStackTrace();
            connected = false;
        }

        //Following section will be activated only if the connection to the database is successful
        if (connected == true) {
            System.out.println("Successfully connected to the database");
            group = new ButtonGroup();
            try {
                //Retrieves a list of all table names apart of the database and creates a group of radiobuttons
                String[] types = {"TABLE"};
                resultSet = connection.getMetaData().getTables(null, null, null, types);
                //Will loop through the table names and create a new radiobutton until there are no more table names left
                while (resultSet.next()) {
                    tableName = resultSet.getString("TABLE_NAME");
                    final JRadioButton tableButton = new JRadioButton(tableName);
                    tableButton.setAlignmentX(CENTER_ALIGNMENT);
                    group.add(tableButton);

                    //When each radiobutton is created dynamically an action listener is also created dynamically
                    //This actionlistener will notify the program what to do when a button is selected
                    tableButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //Will notify the program which table was selected
                            selectedTableName = tableButton.getText();
                        }
                    });
                    workSpace.add(tableButton);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //Sets up load table button
        workSpace.add(new Box.Filler(minSize, prefSize, maxSize));
        loadTable = new JButton("Load Table");
        loadTable.setAlignmentX(CENTER_ALIGNMENT);
        workSpace.add(loadTable);
        workSpace.add(new Box.Filler(minSize, prefSize, maxSize));

        //Sets up scrollpane
        displayTableScroll = new JScrollPane();
        displayTableScroll.setPreferredSize(new Dimension(800, 500));
        workSpace.add(displayTableScroll);
        workSpace.add(new Box.Filler(minSize, prefSize, maxSize));

        loadTable.addActionListener(this);
        loadTable.setActionCommand("load_table");

    }

    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand() == "load_table") {
            try {
                //Initializes variables used for storing table data
                data = new Vector();
                columnName = new Vector();
                table = new JTable();

                //SQL query to retrieve all the data from the selected table
                resultSet = statement.executeQuery("SELECT * FROM " + selectedTableName + ";");

                //Gets the number of columns in the table and retrieves the column names
                int columnCount = resultSet.getMetaData().getColumnCount();
                for(int i = 1; i <= columnCount; i++) {
                    columnName.addElement(resultSet.getMetaData().getColumnName(i));
                }

                //Retrieves the data stored in the columns of the table
                while (resultSet.next()) {
                    Vector rowData = new Vector(columnCount);
                    for (int n = 1; n <= columnCount; n++) {
                        rowData.addElement(resultSet.getObject(n));
                    }
                    //Stores collected data in a vector
                    data.addElement(rowData);
                }

                //Creates table using the vectors created which have the appropriate information in them
                table = new JTable(data, columnName);
                table.setPreferredScrollableViewportSize(new Dimension(300, 80));

                //Sets table in the scrollpane
                displayTableScroll.getViewport().add(table);
                table.setFillsViewportHeight(true);

                //Refreshes the scrollpane
                displayTableScroll.repaint();
            } catch (SQLException error) {
                error.printStackTrace();
            }

        }
    }
}
