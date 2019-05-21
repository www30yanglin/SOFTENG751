package ThreadSafePriorityQueue;

import java.util.concurrent.BlockingQueue;
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
	 * To be consistent with the API, there are four constructors
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
	        this.height = sizeToLevels(DEFAULT_NODES);
	        this.comparator=null;
	    }

	   
      /*
       * PriorityBlockingQueue with the specified initial capacity
       */
		public PipelinedPriorityQueue(int initialCapacity) {
	        if(initialCapacity<=0) throw new  IllegalArgumentException("ERROR:The capacity is smaller than 0!!!");
	        int levels= sizeToLevels(initialCapacity).get();
	        this.binaryheap=new BinaryheapNode[initialCapacity];
	        initBinaryheapNodes(0);
	        this.size = new AtomicInteger(0);
	        this.tokenarray = new TokenarrayElement[levels];
	        initTokenarrayElements(0);
	        this.height = sizeToLevels(initialCapacity);
	        this.comparator=null;
	        
	    }

	   
	    public PipelinedPriorityQueue(int initialCapacity,Comparator<? super E> comparator) {
	    	 if(initialCapacity<=0) throw new  IllegalArgumentException("ERROR:The capacity is smaller than 0!!!");
		        int levels= sizeToLevels(initialCapacity).get();
		        this.binaryheap=new BinaryheapNode[initialCapacity];
		        initBinaryheapNodes(0);
		        this.size = new AtomicInteger(0);
		        this.tokenarray = new TokenarrayElement[levels];
		        initTokenarrayElements(0);
		        this.height = sizeToLevels(initialCapacity);
		        this.comparator=comparator;
	    }
	     
	    public PipelinedPriorityQueue(Collection<? extends E> c) {
                if(c== null)throw new IllegalArgumentException("ERROR:Collection is null!!!");
                int levels=sizeToLevels(c.size()).get();
                this.binaryheap=new BinaryheapNode[c.size()];
		        initBinaryheapNodes(0);
		        this.size = new AtomicInteger(0);
		        this.tokenarray = new TokenarrayElement[levels];
		        initTokenarrayElements(0);
		        this.height = sizeToLevels(c.size());
		        this.comparator=comparator;
                
	    }
	public boolean add(E e) {
	   return offer(e);
	}
	 
	 @Override
	public boolean offer(E e) {
		 if (e == null)
	            throw new NullPointerException();
		 tokenarray[0].lock();
		 if (tokenarray.length > 1) 
			tokenarray[1].lock();
	        tokenarray[0].setValue(e);
	        tokenarray[0].setPosition(0);
		 
	        if (binaryheap[0].getCapacity() < 1) 
	        {
	            tryGrow(binaryheap.length);
	        }
	        
	        int levels =0;
	        
	        while(levels<tokenarray.length)
	        {
	        	boolean result = localEnqueue(levels);
	        }
		return true;
	}

	private boolean localEnqueue(int levels) {
		
		return false;
	}


	private void tryGrow(int oldCap) {
         int length=tokenarray.length;
           
         for(int i=2;i<length;i++)
         {
        	 tokenarray[i].lock();
         }
         
         int newCap=oldCap+((oldCap<64)?(oldCap+2):(oldCap>>1));// grow faster if small
         if (newCap - MAX_ARRAY_SIZE > 0) {
         int Cap= oldCap+1;
         if(Cap<0||Cap>MAX_ARRAY_SIZE)
        	 throw new OutOfMemoryError();
         newCap=MAX_ARRAY_SIZE;
         }
         BinaryheapNode<E>[] oldBinaryArray = binaryheap;
         binaryheap = new BinaryheapNode[newCap];
         
         
         initBinaryheapNodes(0);
         
         for (int i = 0; i < oldCap; i++) {
             binaryheap[i] = oldBinaryArray[i];
         }
         
         updateCaps(0);
         
         this.height=sizeToLevels(newCap);
         
         @SuppressWarnings("unchecked")
		TokenarrayElement<E>[] token = new TokenarrayElement [height.get()];
         for (int i = 0; i < tokenarray.length; i++) {
             token[i] = tokenarray[i];
         }
         
         for (int i = tokenarray.length; i < height.get(); i++) {
             token[i] = new TokenarrayElement<E>(null, 1,new ReentrantLock(true),comparator);
         }
         
         for (int i = 2; i < length; i++) {
             tokenarray[i].unlock();
         }
	}


	private void updateCaps(int i) {
		if (i < 0 || i >= binaryheap.length) return;
        updateCaps(2*1+1);
        updateCaps(2*i+2);
        
        int cap = binaryheap[i].getActive() ? 0 : 1;

        BinaryheapNode<E> leftChild = getLeftChild(i);
        if (leftChild != null) 
        {
            cap += leftChild.getCapacity();
        }

        BinaryheapNode<E> rightChild = getRightChild(i);
        
        if (rightChild != null) 
        {
            cap += rightChild.getCapacity();
        }

        binaryheap[i].setCapacity(cap);
		
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
	 private void initTokenarrayElements(int i) {
			for( i=0; i< tokenarray.length;i++)
			{
				TokenarrayElement<E> element=new TokenarrayElement<E>(null,1,new ReentrantLock(true),comparator);
				tokenarray[i]=element;
			}
			
		}

			private void initBinaryheapNodes(int i) {
			    if(i>=binaryheap.length) return;
			    
			    initBinaryheapNodes(2*i+1);//init the left child
			    initBinaryheapNodes(2*i+2);//init the right child
			    
			    int capacity=1;
			    
			    BinaryheapNode<E> leftChild=getLeftChild(i);
			    if(leftChild!= null)
			    {
			    	capacity+=leftChild.getCapacity();
			    }
			    
			    BinaryheapNode<E> rightChild=getRightChild(i);
			    if(rightChild!= null)
			    {
			    	capacity+=rightChild.getCapacity();
			    }
			    
			    BinaryheapNode<E> Node = new BinaryheapNode<E>(null,false, capacity, comparator);
		        binaryheap[i] = Node;
			
		}

			

			private BinaryheapNode<E> getRightChild(int i) {
				int index=2*i+2;
				if (index>binaryheap.length)
					return null;
					else
						return binaryheap[index];
			}

			private BinaryheapNode<E> getLeftChild(int i) {
				int index=2*i+1;
				if (index>binaryheap.length)
					return null;
					else
						return binaryheap[index];
			}

			private AtomicInteger sizeToLevels(int dEFAULT_NODES2) {
			int res=(int) Math.ceil(Math.log(dEFAULT_NODES2+ 1) / Math.log(2));
			return new AtomicInteger(res);
		}
}
