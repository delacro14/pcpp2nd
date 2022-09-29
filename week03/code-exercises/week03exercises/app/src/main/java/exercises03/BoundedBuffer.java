// For week 3
// raup@itu.dk * 18/09/2021
package exercises03;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class BoundedBuffer implements BoundedBufferInteface<String> {
    private final Semaphore availableItems, availableSpaces, semaphore;
    private final LinkedList<String> items;
    public BoundedBuffer(int capacity) {
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
        semaphore = new Semaphore(1);
        items = new LinkedList<>(); ;
    }
    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }
    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }
    public void insert(String elem)throws InterruptedException {
        System.out.println("Insert: starts");
        availableSpaces.acquire();
        System.out.println("Insert: takes availableSpace");
        semaphore.acquire();
        System.out.println("Insert: adding element");
        items.add(elem);
        semaphore.release();
        System.out.println("Insert: releasing availableItems");
        availableItems.release();
        System.out.println("Insert: finished");
    }
    public String take() throws InterruptedException {
        System.out.println("take: starts");
        availableItems.acquire();
        System.out.println("take: takes availableItems");
        semaphore.acquire();
        System.out.println("take: removing element");
        String item = items.remove();
        semaphore.release();
        System.out.println("take: releasing availableSpace");
        availableSpaces.release();
        System.out.println("take: finished");
        return item;
    }
}


