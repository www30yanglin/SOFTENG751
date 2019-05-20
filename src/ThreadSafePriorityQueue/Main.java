package ThreadSafePriorityQueue;

import java.util.Comparator;
import java.util.concurrent.locks.ReentrantLock;



public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ReentrantLock tokenarrayLock=new ReentrantLock(true);
		//PipelinedPriorityQueue<Integer> defaultQueue
		// Comparator comparator2 = new Comparator();
		//Comparator<Integer> comparator = null ;
		 
		 TokenarrayElement a=new TokenarrayElement(3,1,tokenarrayLock);
		 
		 TokenarrayElement b=new TokenarrayElement(1,1,tokenarrayLock);
		 
		 if(a.isGreater(b.getValue()))
		 {
			 System.out.println("fuck");
		 }
		 else
		 {
			 System.out.println("asd");
		 }

	}

}
