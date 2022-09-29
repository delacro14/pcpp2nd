package exercises03;

public class testPerson {
    public static void run(int threads) {
        for (int i = 0; i < threads; i++) {

            // insert
            new Thread(() -> {
    
                try{
                    Person p = new Person();
                    System.out.println("Thread 1" + "     " + p.getId());
                } catch (Exception e){
                    e.printStackTrace();
                    System.exit(-1);
                }
            }).start();
    
            // start a insert
            new Thread(() -> {
                try{
                    Person p1 = new Person();
                    System.out.println("Thread 2" + "     " + p1.getId());
                } catch (Exception e){
                    e.printStackTrace();
                    System.exit(-1);
                }
            }).start();
            
        }

    }

    public static void main(String[] args) {
        run(18);
    }
}
