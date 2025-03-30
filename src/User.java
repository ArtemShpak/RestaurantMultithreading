import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class User {
    private static final Semaphore semaphore = new Semaphore(2);

    public void orderDish(int id, BlockingQueue<String> queue) {
        try {
            semaphore.acquire();
            String dishName = "Dish" + id;
            System.out.println("User " + id + " ordered " + dishName);
            queue.put(dishName);
        } catch (InterruptedException e) {
            System.out.println("Проблема клієнта з замовлення страви");
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }
}
