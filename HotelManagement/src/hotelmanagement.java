/*
 *Tuna Bolukbasi
 * 
 * 6/30/2020
 * 
 * hotelmanagement is a program which allows the user to add customers of the hotel and manipulate
 * their information using modifying, deleting, sorting, saving and loading methods to do so. 
 * It utilizes an arraylist to store customer information unlike the other GUI I created, which used a 
 * 1D array to do so. With this, came the modification of elements that may appear similar to the contactmanagerv3
 * methods, however they have all been modified to utilize an arraylist
 * 
 * Java version 1.8
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;


	public class hotelmanagement extends JFrame implements ActionListener {
		
		//declaring printwriter variable to print/save into file
		
		PrintWriter out;
		
		//declaring boolean to see if user has typed correct login or not
		
		boolean box;
		
		//to add and remove from content pane
		Container container;
		
		//declaring input variable
		
		static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		//declaring int row and column for pinpointing arrays in view
		
		int row = 0;
		int column = 0;
		
		//string used for storing array information in view into variable
		
		String modify;
		
		//declaring arraylists of type customer to store information
	
		static ArrayList<Customer> customer = new ArrayList<Customer>();
	
		JTextField firsttext, lasttext, roomtext, phonetext, daystext, user, pass, jobtext, emailtext, searchtext;
	
		//adding JLabel
		
		JLabel label = new JLabel();
		
		//adding panel to add items to
		
		JPanel panel;
		
		//adding items to add to panel
		
		JTable table;
		JButton button;
	
		/*
		 * constructor that adds reception and admin to menubar and the items of those options
		 */
		
		public hotelmanagement() {
		
			//setting menu, menubar and item variables
			JMenu menu;
			JMenuBar bar;
			JMenuItem item;
			bar = new JMenuBar();
			
			//setting the programs menubar as variable bar
			setJMenuBar(bar);
			
			//adding reception menu option to the bar
			
			menu = new JMenu("Reception");
			bar.add(menu);
			
			//adding options to reception
			
			item = new JMenuItem("Add Customer");
			item.addActionListener(this);
			menu.add(item);
			
			item = new JMenuItem("View Customer");
			item.addActionListener(this);
			menu.add(item);
		
			item = new JMenuItem("Save Customer");
			item.addActionListener(this);
			menu.add(item);
			
			item = new JMenuItem("Load Customer");
			item.addActionListener(this);
			menu.add(item);
			
			item = new JMenuItem("Search Customer");
			item.addActionListener(this);
			menu.add(item);
			
			item = new JMenuItem("Sort Contact");
			item.addActionListener(this);
			menu.add(item);
			
			panel = new JPanel();
			
			//making application background black (learned from tutorial by John Gizdich)
			
			panel.setBackground(Color.BLACK);
			add(panel);
			
			//adding username and password labels (learned how to change JLabel color from fredosaurus.com)
			
			label = new JLabel("Admin Username: ");
			label.setForeground(Color.WHITE);
			
			user = new JTextField(20);
			
			panel.add(label);
			panel.add(user);
	
			
			label = new JLabel("Password: ");
			label.setForeground(Color.WHITE);
			panel.add(label);
		
			pass = new JTextField(20);
			panel.add(pass);
			
			button = new JButton("Login");
			button.addActionListener(this);
			panel.add(button, BorderLayout.SOUTH);
	}
		

		/*
		 * main method creates class object window, sets window size, title, exit when pressing x button
		 *  and visibility
		 */
		
		public static void main(String[] args) throws IOException {
			hotelmanagement window = new hotelmanagement();
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setTitle("Hotel Management Software");
			window.setSize(650,550);
			window.setVisible(true);
		}

		
		/*
		 * actionperformed method calls methods depending on what user has clicked
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
				
			
			
				String event = e.getActionCommand();
				
				
				
				if (event.contentEquals("Quit"))
				{
					System.exit(0);
				}
				
				else if (event.contentEquals("Login"))
				{
					if (checklogin()) {
						JOptionPane.showMessageDialog(panel, "You have successfully logged in. You "
								+ " now have access to all methods.");
					}
				}
				if (checklogin()) {
				
				if (event.contentEquals("View Customer"))
				{
					viewcustomer();
				}
					
				if (event.contentEquals("Add Customer"))
				{
					try {
						addcustomer();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (event.contentEquals("Add"))
				{
					customertoarray();
				}
				if (event.contentEquals("Modify"))
				{
					if (row < customer.size()) {
						modify();
					} else {
						JOptionPane.showMessageDialog(panel, "Please select an existing contact.");
					}
				}
				if (event.contentEquals("Modify Contact")) {
					modifycustomer();
				}
				if (event.contentEquals("Delete Contact"))
				{
					if (row < customer.size()) {
						delete();
					} else {
						JOptionPane.showMessageDialog(panel, "Please select an existing contact.");
					}
				}
				if (event.contentEquals("Save Customer")) {
					try {
						save();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(panel, "Customer info has been saved");
				}
				if (event.contentEquals("Load Customer")) {
					try {
						load();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(panel, "Customer info has been loaded");
					viewcustomer();
				}
				if (event.contentEquals("Search Customer")) {
					search();
					
				}
				
				if (event.contentEquals("Search")) {
					searchcontact();
				}
				if (event.contentEquals("Sort Contact")) {
					sortcontact();
				}
				if (event.contentEquals("A-Z Sorting")) {
					azsorting();
				}
				if (event.contentEquals("Z-A Sorting")) {
					zasorting();
				}
				}
				
		}
		
		/*
		 * zasorting is a method which uses bubble sort to reverse alphabetically sort them
		 */
		private void zasorting() {
			// TODO Auto-generated method stub
			
			//declaring Customer variables for swapping
			
			Customer tempcustomer;
			
			//declaring boolean swapped 
			
			boolean swapped = false;
			
			while (!swapped)
			{
				//setting swapped to true so that once array is completely sorted, exits while loop
				swapped = true;
				
				//for loop that loops through all values of array
				
				for (int i = 1; i < customer.size(); i++)
				{
					if (customer.get(i - 1).getfirst().compareTo(customer.get(i).getfirst()) < 0)
					{
						
						//setting temp variables equal to arraylist values for swapping
						
						tempcustomer = customer.get(i - 1);
						
						//swapping values
						
						customer.set(i - 1, customer.get(i));
						
						customer.set(i, tempcustomer);
						
						//setting swapped back to false to check if array has to be sorted again
						swapped = false;
					}
				}
			}
		}

		/*
		 * zasorting is a method which uses bubble sort to reverse alphabetically sort them
		 */
		
		private void azsorting() {
			// TODO Auto-generated method stub
			
			//declaring Customer variables for swapping
			
			Customer tempcustomer;
			
			//declaring boolean swapped 
			
			boolean swapped = false;
			
			while (!swapped)
			{
				//setting swapped to true so that once array is completely sorted, exits while loop
				swapped = true;
				
				//for loop that loops through all values of array
				
				for (int i = 1; i < customer.size(); i++)
				{
					if (customer.get(i - 1).getfirst().compareTo(customer.get(i).getfirst()) > 0)
					{
						
						//setting temp variables equal to arraylist values for swapping
						
						tempcustomer = customer.get(i - 1);
						
						//swapping values
						
						customer.set(i - 1, customer.get(i));
						
						customer.set(i, tempcustomer);
						
						//setting swapped back to false to check if array has to be sorted again
						swapped = false;
					}
				}
			}
			
			
			
		}


		/*
		 * sort contact prompts user to click a button depending on which sort they want
		 */
		
		private void sortcontact() {
			// TODO Auto-generated method stub
			
			container = getContentPane();
			
			if (panel != null) {
				container.remove(panel);
			}
			
			panel = new JPanel();
			
			//adding background color to label
			
			panel.setBackground(Color.BLACK);
			add(panel);
			
			//adding az and za sorting buttons to panel
			
			button = new JButton("A-Z Sorting");
			button.addActionListener(this);
			
			panel.add(button);
			
			button = new JButton("Z-A Sorting");
			button.addActionListener(this);
			
			panel.add(button);
			
			//addomg panel to container
			
			container.add(panel);
			
			validate();
			
		}

		/*
		 * search is a method which takes user input of the first name they want to search
		 */
		private void search() {
			// TODO Auto-generated method stub
			
			container = getContentPane();
			
			if (panel != null) {
				container.remove(panel);
			}
			
			//initializing new layout for main panel
			
			panel = new JPanel();
			//setting background color of panel
			panel.setBackground(Color.BLACK);
			
			
			label = new JLabel("Type First Name Of Customer To Search:");
			label.setForeground(Color.WHITE);
			//adding panel to label
			
			panel.add(label);
			searchtext = new JTextField(20);
			button = new JButton("Search");
			button.addActionListener(this);
			panel.add(searchtext);
			panel.add(button);
			
			container.add(panel);
			
			validate();
		}
		
		/*
		 * searchcontact checks if the inputted name is the same with any first names of
		 * customer arraylist. Uses sequential search to do so
		 */
		
		private void searchcontact() {
			
			//boolean that checks if search was found or not
			
			boolean found = false;
			
			//for loop that loops through all elements
			
			for (int i = 0; i < customer.size(); i++)
			{
				if (searchtext.getText().compareTo(customer.get(i).getfirst()) == 0)
				{
					JOptionPane.showMessageDialog(panel, "Customer has been found at index"
							+ " # " + i);
					
					found = true;
				}
			}
			
			if (!found)
			{
				JOptionPane.showMessageDialog(panel, "No Contact of the name " + searchtext.getText() + 
						" has been found");
			}
		}


		private void save() throws IOException {
			// TODO Auto-generated method stub
			out = new PrintWriter(new FileWriter("customers.txt"));
			
			//printing format and counter
			
			out.println("FORMAT:\nfirstname\nlastname\nphone\nroom#\ndays of stay");
			out.println(customer.size());
			
			//for loop that loops through all values
			for (int i = 0; i < customer.size(); i++)
			{
				out.println(customer.get(i).getfirst());
				out.println(customer.get(i).getlast());
				out.println(customer.get(i).getphone());
				out.println(customer.get(i).roomnum());
				out.println(customer.get(i).getdaysofstay());
			}
			
			//closing file
			out.close();
		}
		
		/*
		 * load is a method which reads input from the files adds them to the arraylist customer
		 */

		private void load() throws IOException {
			// TODO Auto-generated method stub
			
			//declaring string variables for storing into array
			
			String first, last, phone, rooms, days;
			
			in = new BufferedReader(new FileReader("customers.txt"));
			
			//reading format
			
			in.readLine();
			in.readLine();
			in.readLine();
			in.readLine();
			in.readLine();
			in.readLine();
			
			//reading counter
			
			int x = Integer.valueOf(in.readLine()).intValue();
			
			//for loop that loops through all elements
			
			for (int i = 0; i < x; i++) {
				
				//storing lines in file into temp string variables
				
				first = in.readLine();
				last = in.readLine();
				phone = in.readLine();
				rooms = in.readLine();
				days = in.readLine();
				
				//storing strings into contact type arraylist
				
				customer.add(new Customer(first, last, phone, rooms, days));
			}
			//closing file
				in.close();
		}


		private void delete() {
			// TODO Auto-generated method stub
			
			customer.remove(row);
			viewcustomer();
			
			
		}
		
		/*
		 * modify method is the same as the add method however it uses methods from customer class and array
		 * to fill the respective text
		 */

		private void modify() {
			// TODO Auto-generated method stub
			//clearing panel before adding anything new onto it
			container = getContentPane();
			
			//clearing panel before adding new content to it
			
			if (panel != null) 
			{
				container.remove(panel);
			}
			
			//initializing new layout for main panel
			
			panel = new JPanel();
			
			//adding textfields and labels for respective customer fields
			
			label = new JLabel("First name: ");
			panel.add(label);
			
			firsttext = new JTextField(20);
			panel.add(firsttext);
			
			label = new JLabel("Last name: ");
			panel.add(label);
			
			lasttext = new JTextField(20);
			panel.add(lasttext);
			
			label = new JLabel("Phone #: ");
			panel.add(label);
			
			phonetext = new JTextField(20);
			panel.add(phonetext);
			
			label = new JLabel("Room #: ");
			panel.add(label);
			
			roomtext = new JTextField(20);
			panel.add(roomtext);
			
			label = new JLabel("Days of stay: ");
			panel.add(label);
			
			daystext = new JTextField(20);
			panel.add(daystext);
			
			button = new JButton("Modify Contact");
			button.addActionListener(this);
			panel.add(button);
			
			firsttext.setText(customer.get(row).getfirst());
			lasttext.setText(customer.get(row).getlast());
			phonetext.setText(customer.get(row).getphone());
			roomtext.setText(customer.get(row).roomnum());
			daystext.setText(customer.get(row).getdaysofstay());
			
			container.add(panel);
			
			validate();
		}
		
		/*
		 * modifycustomer is a method which sets modified text as the new field in the customer arraylist
		 */
		
		private void modifycustomer()
		{
			customer.set(row, new Customer(firsttext.getText(), lasttext.getText(), phonetext.getText(), 
					roomtext.getText(), daystext.getText()));
			viewcustomer();
		}

		
		/*
		 * viewcustomer is a method which takes fields from customer arraylist
		 * and displays them in a table with a scroll. 
		 * 
		 * NOTE: I re-used this from my ContactManagerv3 code however, I modified for the purpose of this project.
		 * I changed it such that it becomes compatible with arraylists instead of using 1d arrays.
		 */

		private void viewcustomer()
		{
			
			//declaring new JPanel 
			
			JPanel panel2;
			
			panel2 = new JPanel();
			
			//initializing layout
			
			panel2.setLayout(new BorderLayout());
			
			container = getContentPane();
			
			if (panel != null) {
				container.remove(panel);
			}
			
			//initializing new layout for main panel
			
			panel = new JPanel();
			
			panel.setLayout(new BorderLayout());
			
			//adding button
			button = new JButton("Delete Contact");
			button.addActionListener(this);
			panel2.add(button, BorderLayout.EAST);
			
			//adding label to instruct user on how to modify contact
			
			button = new JButton("Modify");
			button.addActionListener(this);
			panel2.add(button, BorderLayout.WEST);
			
			//adding label to instruct user on how to modify
			
			JLabel L2 = new JLabel("To modify contact, click once on contact and then click modify button");
			panel2.add(L2, BorderLayout.CENTER);
			
			//adding bottom panel to main panel
			
			panel.add(panel2, BorderLayout.SOUTH);
			
			//Declaring String array to display each field of contact
			String[] fields = {"First name", "Last name", "Phone #", "Room #", "Days of Stay"};
			
			//declaring 2D array to store customer arraylist
			
			String[][] customerinfo = new String[100][5];
			
			//for loop for storing arraylist into 2D array
			
			for (int i = 0; i < customer.size(); i++) {
				
				//storing arraylist into respective sections of row inside customerinfo
				
				customerinfo[i][0] = customer.get(i).getfirst();
				customerinfo[i][1] = customer.get(i).getlast();
				customerinfo[i][2] = customer.get(i).getphone();
				customerinfo[i][3] = customer.get(i).roomnum();
				customerinfo[i][4] = customer.get(i).getdaysofstay();
			}
			
			//creating new table (Some code/concepts used from bluejaymenu such as mouse listener to get row/columns)
			
			table = new JTable(customerinfo, fields);
			
			//using mouse listener code from BlueJayMenu example
			
			table.addMouseListener(new MouseAdapter () {
				public void mouseClicked (MouseEvent e) {
					
					//setting int row and column as selected info to use for modifying and deleting
					row = table.getSelectedRow();
					column = table.getSelectedColumn();
					
					//setting string equal to string in box that was edited by user for modifying
					modify = (String) table.getValueAt(row, column);
					System.out.println(modify);
					System.out.println(row + "\n" + column);
				}
			});
			JScrollPane scroll = new JScrollPane(table);
			panel.add(scroll);
			container.add(panel);
			
			//displaying state
			validate();
			
		}
		
		/*
		 * checklogin is a method which checks the information that has been entered into admin login
		 * returns true if username is admin and password is admin
		 * returns false if anything else
		 */

		private boolean checklogin() 
		{
			
			if (user.getText().compareTo("admin") == 0 && pass.getText().compareTo("admin") == 0)
			{
				return true;
			}
			
			else
			{
				JOptionPane.showMessageDialog(panel, "Either username and password has been entered in"
						+ "correctly or not typed at all.\nUsername = admin\nPassword = admin");
				return false;
			}
			
		}
		
		/*
		 * addcustomer is a method which prompts users to enter all fields of customer
		 * and stores it into customer arraylist
		 */
		
		public void addcustomer() throws IOException
		{
			//clearing panel before adding anything new onto it
			container = getContentPane();
			
			//clearing panel before adding new content to it
			
			if (panel != null) 
			{
				container.remove(panel);
			}
			
			//initializing new layout for main panel
			
			panel = new JPanel();
			
			//adding textfields and labels for respective customer fields
			
			label = new JLabel("First name: ");
			panel.add(label);
			
			firsttext = new JTextField(20);
			panel.add(firsttext);
			
			label = new JLabel("Last name: ");
			panel.add(label);
			
			lasttext = new JTextField(20);
			panel.add(lasttext);
			
			label = new JLabel("Phone #: ");
			panel.add(label);
			
			phonetext = new JTextField(20);
			panel.add(phonetext);
			
			label = new JLabel("Room #: ");
			panel.add(label);
			
			roomtext = new JTextField(20);
			panel.add(roomtext);
			
			label = new JLabel("Days of stay: ");
			panel.add(label);
			
			daystext = new JTextField(20);
			panel.add(daystext);
			
			button = new JButton("Add");
			button.addActionListener(this);
			panel.add(button);
			
			container.add(panel);
			
			validate();
			
		}
		
		/*
		 * customertoarray is a method which takes input from addcustomer method and stores it inside of 
		 * customer arraylist
		 */
		
		public void customertoarray()
		{
			String first, last, phone, room, daysofstay;
			
			//getting text input from addcustomer method and adding it to the customer arraylist
			
			first = firsttext.getText();
			last = lasttext.getText();
			phone = phonetext.getText();
			room = roomtext.getText();
			daysofstay = daystext.getText();
			
			//adding to customer arraylist
			customer.add(new Customer(first, last, phone, room, daysofstay));
			
			viewcustomer();
		}

}
