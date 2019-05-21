package ThreadSafePriorityQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PipelinedPriorityQueue<E extends Comparable<E>> extends AbstractQueue<E> implements BlockingQueue<E> {
	
	private static int DEFAULT_LEVELS=4;//Default number of level
	
	private static int DEFAULT_NODES=15;//Default number of nodes
	
	private static int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;//Ensure the size  Overflow
	
	private AtomicInteger size;// The size of queue
	
	private AtomicInteger height;// The hight of this heap
	
	private BinaryheapNode<E>[] binaryheap;
	
	private TokenarrayElement<E>[] tokenarray;
	
	private Comparator<? super E> comparator;
	
	/*
	 * Blocking queues when queues are empty
	 */
	private final Lock notEmptyLock = new ReentrantLock();
	private final Condition notEmptyCondition = notEmptyLock.newCondition();
	/*
	 * To be consistent with the API, there are three constructors
	 */
	
	/*
	 * Default constructor
	 */
	 public PipelinedPriorityQueue() {
	        this.binaryheap=new BinaryheapNode[DEFAULT_NODES];
	        initBinaryheapNodes(0);
	        this.size = new AtomicInteger(0);
	        this.tokenarray = new TokenarrayElement[DEFAULT_LEVELS];
	        initTokenarrayElements(0);
	        this.height = SizeToNumLevels(DEFAULT_NODES);
	        this.comparator=comparator;
	    }

	    private void initTokenarrayElements(int i) {
		for( i=0; i< tokenarray.length;i++)
		{
			TokenarrayElement<E> element=new TokenarrayElement<E>(null,1,new ReentrantLock(),comparator);
			tokenarray[i]=element;
		}
		
	}

		private void initBinaryheapNodes(int i) {
		    if(i>=binaryheap.length) return;
		    
		    initBinaryheapNodes(2*i+1);//init the left child
		    initBinaryheapNodes(2*i+2);//init the right child
		    
		    int capacity=1;
		    
		    BinaryheapNode leftChild=getLeftChild(i);
		    if(leftChild!= null)
		    {
		    	capacity+=leftChild.getCapacity();
		    }
		    
		    BinaryheapNode rightChild=getRightChild(i);
		    if(rightChild!= null)
		    {
		    	capacity+=rightChild.getCapacity();
		    }
		    
		    BinaryheapNode<E> Node = new BinaryheapNode<E>(null,false, capacity, comparator);
	        binaryheap[i] = Node;
		
	}

		

		private BinaryheapNode getRightChild(int i) {
			int index=2*i+2;
			if (index>binaryheap.length)
				return null;
				else
					return binaryheap[index];
		}

		private BinaryheapNode getLeftChild(int i) {
			int index=2*i+1;
			if (index>binaryheap.length)
				return null;
				else
					return binaryheap[index];
		}

		private AtomicInteger SizeToNumLevels(int dEFAULT_NODES2) {
		int res=(int) Math.ceil(Math.log(dEFAULT_NODES2+ 1) / Math.log(2));
		return new AtomicInteger(res);
	}

		public PipelinedPriorityQueue(int initialCapacity) {
	        
	    }

	   
	    public PipelinedPriorityQueue(int initialCapacity,Comparator<? super E> comparator) {
	       
	    }
	     
	    public PipelinedPriorityQueue(Collection<? extends E> c) {

	    }
	
	 
	 @Override
	public boolean offer(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E poll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E peek() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void put(E e) throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public E take() throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int remainingCapacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		// TODO Auto-generated method stub
		return 0;
	}

}
