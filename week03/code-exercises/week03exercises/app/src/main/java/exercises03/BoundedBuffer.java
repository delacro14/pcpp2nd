// For week 3
// raup@itu.dk * 18/09/2021
package exercises03;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class BoundedBuffer implements BoundedBufferInteface<String> {
    private final LinkedList<String> list; 
    private final Semaphore sem;
    
    public BoundedBuffer(int bound) {
        this.list = new LinkedList<>(); 
        sem = new Semaphore(bound);
    }

    public void insert(String element) throws InterruptedException {
        try {  
            
            sem.acquire();
            list.add(element); 
            System.out.println("add");
        } finally { 
            sem.release(); 
            System.out.println("relesed in add");
        } 
    }

    public String take() throws InterruptedException {
        try {
            sem.acquire();
            String e = list.remove();
            System.out.println("remove");
            return e;
        } finally {
            sem.release();
            System.out.println("released in take");
        }
    }
}


