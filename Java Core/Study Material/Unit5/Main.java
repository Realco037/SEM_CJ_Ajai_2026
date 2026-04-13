class NumberTask implements Runnable{
    public void run(){
        for(int i=0;i<=5;i++){
            System.out.println("Number: "+i);
        
                try{
                    Thread.sleep(1000);
                }
                catch(InterruptedException e){
                    System.out.println(e);
                }
        }
    }
}

class LetterTask implements Runnable{
    public void run(){
        for(char ch='A';ch<='E';ch++){
            System.out.println("Letter: "+ ch);
        
              try{
                Thread.sleep(1000);
            }
            catch(InterruptedException e){
                System.out.println(e);
            }
        }   
    }
}

public class Main
{
	public static void main(String[] args) {
	   // NumberTask q = new NumberTask();
	   // Thread t = new Thread(q);
    Thread t1= new Thread(new NumberTask());
    Thread  t2 = new Thread(new LetterTask());
    
    t1.start();
     try{
            Thread.sleep(1000);
        }
        catch(InterruptedException e){
            System.out.println(e);
        }
    t2.start();
	}
}


// 1.
// Print Even & Odd 
// thread 1 -> even numbers
// thread 2 -> odd numbers
// 2.
// create thread using runnable 
// print your name 5 times
// 3.
// print from 10 to 1 using sleep()

// 4.
// Sum of Numbers
// Thread1-> sum 1-50
// Thread2-> sum 51-100
// 5.
// Multiplication Tables 
// Thread1 -> table of 2
// Thread2 -> table of 3

