
public class Customer {

	String firstname, lastname, phone, roomnum, daysofstay;
	
	public Customer(String firstname, String lastname, String phone, String roomnum, String daysofstay)
	{
		//setting class variables equal to input parameters of constructor
		
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.roomnum = roomnum;
		this.daysofstay = daysofstay;
	}
	
	
	public String getfirst()
	{
		return firstname;
	}
	
	/*
	 * getlast is a string that returns class variable lastname
	 */
	
	public String getlast()
	{
		return lastname;
	}
	
	/*
	 * getphone is a method that returns phone class variable
	 */
	
	public String getphone()
	{
		return phone;
	}
	
	/*
	 * getdaysofstay is a method that returns daysofstay class variable
	 */
	
	public String getdaysofstay() 
	{
		return daysofstay;
	}
	
	/*
	 * roomnum is a method that returns roomnum class variable
	 */
	
	public String roomnum()
	{
		return roomnum;
	}
	
}
