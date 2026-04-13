interface calculator{
}
class demo2 implements  calculator{

}

public class Main {
    public static void main(String args[])
	{
    //  System.out.println(LocalDate.now());
	//  System.out.println(LocalTime.now());
    // String str ="sdfsdf";   
	// String s =str.replaceAll("\\d","");
		int balance=1000;
		int withdraw=5000;
		assert withdraw<=balance:"Insufficient Balance";
		System.out.println("Transaction Successful");
		
		
	}

}
