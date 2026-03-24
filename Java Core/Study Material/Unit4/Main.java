@FunctionalInterface
interface Demo 
{
	void dis(int i);
}

// ()->{ }

public class Main {
    public static void main(String[] args)
    {
      Demo o= (i)->{
        //  public void dis(){
            System.out.println("Hiii"+i);
          // }
      };
      o.dis(23);
    }
}






















// Anonymous Inner class
// @FunctionalInterface
// interface Demo 
// {
// 	void dis();
// }

// class Main {
//     public static void main(String[] args)
//     {
//       Demo o=new Demo()
//     		  {
//     	  		public void dis()
//     	  		{
//     	  			System.out.println("I Love Java");
//     	  		}
//     		  };
//       o.dis();
//     }
// }



// @FunctionalInterface
// interface Demo 
// {
// 	void dis();
// }

// class Main {
//     public static void main(String[] args)
//     {
//       Demo o=() ->{System.out.println("I Love Java"); };
//       o.dis();
//     }
// }
