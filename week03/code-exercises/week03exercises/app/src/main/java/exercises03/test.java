package exercises03;

public class test {
    public static void run(int threads, int bound) {
        BoundedBuffer list = new BoundedBuffer(bound);
        for (int i = 0; i < threads; i++) {

            // insert
            new Thread(() -> {
    
                try{
                    list.insert("smth");
                } catch (InterruptedException e){
                    e.printStackTrace();
                    System.exit(-1);
                }
            }).start();
    
            // start a insert
            new Thread(() -> {
                try{
                    list.take();
                } catch (InterruptedException e){
                    e.printStackTrace();
                    System.exit(-1);
                }
            }).start();
            
        }

    }

    public static void main(String[] args) {
        run(10, 1);
    }
}
