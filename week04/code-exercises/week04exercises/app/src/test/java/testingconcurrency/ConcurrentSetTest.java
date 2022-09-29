package testingconcurrency;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
// TODO: Very likely you need to expand the list of imports

public class ConcurrentSetTest {

    // Variable with set under test
    private ConcurrentIntegerSet set;
    private volatile int counter;

    CyclicBarrier barrier;
    private final static ExecutorService pool = Executors.newCachedThreadPool();


    // TODO: Very likely you should add more variables here
    private synchronized void increaseCount() {
        this.counter++;
    }


    // Uncomment the appropriate line below to choose the class to
    // test
    // Remember that @BeforeEach is executed before each test
    @BeforeEach
    public void initialize() {
        // init set
        set = new ConcurrentIntegerSetBuggy();
        counter = 0;
        // set = new ConcurrentIntegerSetSync();
        // set = new ConcurrentIntegerSetLibrary();
    }

    //    @Test
    @RepeatedTest(7000)
    @DisplayName("Add Function")
//    @Disabled
//    @MethodSource("argsGeneration")
    public void addConcurrentIntegerSetBuggyTest() {
        int nrThreads = 64;
        int N = 20;
        barrier = new CyclicBarrier(nrThreads + 1);
        for (int i = 0; i < nrThreads; i++) {
            pool.execute(new ConcurrentIntegerBuggyAdd(N));
        }
        try {
            barrier.await();
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        assertTrue(N == counter, "set.size() == " + counter + "," +
                " but we expected " + N);
    }

    @RepeatedTest(7000)
    @DisplayName("Add Function")
//    @MethodSource("argsGeneration")
//    @Disabled
    public void removeConcurrentIntegerSetBuggyTest() {
        int nrThreads = 64;
        int N = 24;
        for (int i= 0; i < nrThreads; i++) {
            set.add(i);
        }
        barrier = new CyclicBarrier(nrThreads + 1);
        for (int i = 0; i < nrThreads; i++) {
            pool.execute(new ConcurrentIntegerBuggyRemove(N));
        }
        try {
            barrier.await();
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        assertTrue(nrThreads - N == nrThreads-counter, "set.size() == " + set.size() + "," +
                " but we expected " + (nrThreads-counter));
    }
    // TODO: Define your tests below
    public class ConcurrentIntegerBuggyAdd extends Thread {

        private final int N;

        public ConcurrentIntegerBuggyAdd(int N) {
            this.N = N;
        }

        public void run() {
            try {
                barrier.await();
                for (int i = 0; i < N; i++) {
                    if(set.add(i)) {
                        increaseCount();
                    }
                }
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public class ConcurrentIntegerBuggyRemove extends Thread {

        private final int N;

        public ConcurrentIntegerBuggyRemove(int N) {
            this.N = N;
        }

        public void run() {
            try {
                barrier.await();
                for (int i = 0; i < N; i++) {
                    if (set.remove(i)){
                        increaseCount();
                    }
                }
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<Arguments> argsGeneration() {

        // Max number of increments
        final int I = 50_000;
        final int iInit = 10_000;
        final int iIncrement = 10_000;

        // Max exponent number of threads (2^J)
        final int J = 6;
        final int jInit = 1;
        final int jIncrement = 1;

        // List to add each parameters entry
        List<Arguments> list = new ArrayList<Arguments>();

        // Loop to generate each parameter entry
        // (2^j, i) for j \in {100,200,...,J} and j \in {1,..,I}
        for (int i = iInit; i <= I; i += iIncrement) {
            for (int j = jInit; j < J; j += jIncrement) {
                list.add(Arguments.of((int) Math.pow(2, j), i));
            }
        }

        return list;
    }
}
