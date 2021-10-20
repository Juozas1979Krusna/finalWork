
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.*;
//
import java.awt.event.*;
import javax.swing.*;
import        javax.swing.JFrame;
import javax.swing.JTextField;
//
public class Accounts extends JFrame {
private JButton getAccountButton;
private JButton b01;
    
private JTextField t01;
private JTextField t02;

private JList accountNumberList;
private Connection connection;
private JTextField accountIDText,
usernameText,
passwordText,
tsText;

public String A_text;
public String B_text;
public String C_text;

public double A;
public double B;
public double C;

public Accounts() {
	try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
	} 
	catch (Exception e) {
		System.err.println("Unable to find and load driver");
		System.exit(1);
	}


}
private void buildGUI() {

	Container c = getContentPane();
	c.setLayout(new FlowLayout());
//Do Account List
	Vector v = new Vector();
	try {
		Statement statement = connection.createStatement();
//ResultSet rs = statement.executeQuery("SELECT acc_id FROM acc");
		ResultSet rs = statement.executeQuery("SELECT process FROM table001Thermo");
//		ResultSet rs = statement.executeQuery("SELECT set_id FROM table001Thermo");
		while(rs.next()) {
			v.addElement(rs.getString("process"));
//                        v.addElement(rs.getString("set_id"));
		}
		rs.close();
	} catch(SQLException e) { }
	accountNumberList = new JList(v);
	accountNumberList.setVisibleRowCount(2);
	JScrollPane accountNumberListScrollPane = new JScrollPane(accountNumberList);
//Do Get Account Button
	getAccountButton = new JButton("Select Process");

	getAccountButton.addActionListener (
		new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Statement statement = connection.createStatement();

//ResultSet rs = statement.executeQuery("SELECT * FROM table001Thermo WHERE process = " + accountNumberList.getSelectedValue());
ResultSet rs = statement.executeQuery("SELECT * FROM table001Thermo WHERE process = '" + accountNumberList.getSelectedValue()+"'");
//ResultSet rs = statement.executeQuery("SELECT * FROM table001Thermo WHERE process = " + "Fahrenheit to Celsius");
//ResultSet rs = statement.executeQuery("SELECT * FROM table001Thermo WHERE set_id = " + accountNumberList.getSelectedValue());

					if (rs.next()) {
						accountIDText.setText(rs.getString("A"));
						usernameText.setText(rs.getString("B"));
						passwordText.setText(rs.getString("C"));
						tsText.setText(rs.getString("ts"));
//activeTSText.setText(rs.getString("act_ts"));

A_text=(rs.getString("A"));
B_text = (rs.getString("B"));
C_text = (rs.getString("C"));

 A = Double.parseDouble(A_text);
 B = Double.parseDouble(B_text);
 C = Double.parseDouble(C_text);
					}
				} catch(SQLException ee) {}
			}
		}
	);

JPanel first = new JPanel();
first.add(accountNumberListScrollPane);
first.add(getAccountButton);
accountIDText = new JTextField(15);
usernameText = new JTextField(15);
passwordText = new JTextField(15);
tsText = new JTextField(15);
//activeTSText = new JTextField(15);
JPanel second = new JPanel();
second.setLayout(new GridLayout(5,1));
second.add(accountIDText);
second.add(usernameText);
second.add(passwordText);
second.add(tsText);

        t02 = new JTextField(16);
        t01 = new JTextField(16);

        b01 = new JButton("Calculate");
        b01.addActionListener (
                new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                String text = t01.getText();
                                double outDouPrint = Double.parseDouble(text);
                                //outDouPrint = (outDouPrint-32)*5/9;
                                //outDouPrint = ((outDouPrint-A)*B/C)+10000;
outDouPrint = ((outDouPrint-A)*B/C);
                                String outStrPrint=String.valueOf(outDouPrint);
//                                String s = e.getActionCommand();
                                t02.setText(outStrPrint);
                        }
                }
        );

JPanel third = new JPanel();
third.add(b01);
JPanel fourth = new JPanel();
fourth.add(t01);
fourth.add(t02);
/*A*/
//second.add(activeTSText);
c.add(first);
c.add(second);
/*V*/
c.add(third);
c.add(fourth);
/*A*/
setSize(200,200);
show();
}
public void connectToDB() {
      String JdbcURL = "jdbc:mysql://localhost:3306/thermo?" + "serverTimezone=UTC";
      String Username = "root";
      String password = "";
      Connection con = null;
try {
//connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/accounts?"+"autoReconnect=true&useSSL=false"+"user=root&password=1");
connection = DriverManager.getConnection(JdbcURL, Username, password);
//connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/accounts?user=root&password=1?autoReconnect=true&useSSL=false");

//connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/accounts?user=root&password=1");

} catch(SQLException e) {
System.out.println("Unable to connect to database");
System.exit(1);
}
}
private void displaySQLErrors(SQLException e) {
System.out.println("SQLException: " + e.getMessage());
System.out.println("SQLState:" + e.getSQLState());
System.out.println("VendorError: " + e.getErrorCode());
}
private void init() {
connectToDB();
}
public static void main(String[] args) {
	Accounts accounts = new Accounts();
	accounts.addWindowListener(
		new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		}
	);
	accounts.init();
	accounts.buildGUI();
}
}
//

