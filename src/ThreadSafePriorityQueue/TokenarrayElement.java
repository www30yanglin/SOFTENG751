package ThreadSafePriorityQueue;

import java.util.concurrent.locks.ReentrantLock;
import java.util.Comparator;

public class TokenarrayElement<E> {

	private E value;// The value of element
    
	private int position;// The index of element in binary heap
	
	private Comparator<? super E> comparator;// Used to compare priorities
	
	private final ReentrantLock tokenarrayLock;//The lock that control the concurrent access in the Tokenarray
	
	/*
	 * constructor
	 * 
	 * @param value                The value of the element 
	 * @param position             The index of the element in binary heap
	 * @param tokenarrayLock       The lock that control the concurrent access in the Tokenarray 
	 * 
	 */

	 public TokenarrayElement(E value, int position,Comparator<? super E> comparator,ReentrantLock tokenarrayLock) {
             this.value = value;
             this.position = position;
             this.tokenarrayLock = tokenarrayLock;
     }
	 
	 
	 /*
	  * The methods about value
	  */
	 public E getValue()
	 {
		 return value;
	 }
	 
	 public void setValue(E value)
	 {
		 this.value =value;
	 }
	 
	 
	 /*
	  * The methods about position
	  */
	 public int getPostion()
	 {
		 return position;
	 }
	 
	 public void setPostion(int ostision)
	 {
		 this.position =position;
	 }
	 
	 
	 /*
	  * The methods about lock
	  */
	 public void lock()
	 {
		 tokenarrayLock.lock();
	 }
	 
	 public void unlock()
	 {
		 tokenarrayLock.lock();
	 }
	 
	 public ReentrantLock getLock()
	 {
		 return this.tokenarrayLock;
	 }
	 
	 public boolean isGreater(E o) {
	        int result;
	        if (comparator != null) {
	            result = comparator.compare(o, value);
	        } else {
	            result = ((Comparable<? super E>) o).compareTo(this.value);
	        }

	        return (result > 0);

	
	}
}
