import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class Chef {

    private final Semaphore semaphore = new Semaphore(2);

    public void cook(BlockingQueue<String> chefsQueue, BlockingQueue<String> ordersQueue) {
        while (Thread.currentThread().isInterrupted()) {
            String order = null;
            try {
                order = chefsQueue.take();
            } catch (InterruptedException e) {
                System.out.println("Кухар не зміг отримати замовлення");
            }
            try {
                semaphore.acquire();
                System.out.println("Chef is cooking the dish: " + order);
                Thread.sleep(500);
                System.out.println("Chef finished cooking: " + order);
                ordersQueue.put(order);
            } catch (InterruptedException e) {
                System.out.println("Кухар не зміг приготувати страву");
            } finally {
                semaphore.release();
            }
        }
    }
}
