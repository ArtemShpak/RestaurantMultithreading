import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

public class Waiter {
    public void serve(BlockingQueue<String> usersOrders, BlockingQueue<String> chefsQueue, BlockingQueue<String> ordersQueue) {
        String order = null;
        String preparedOrder = null;
        while (Thread.currentThread().isInterrupted()) {
            try {
                order = usersOrders.take();
                System.out.println("Waiter received order: " + order);
            } catch (InterruptedException e) {
                System.out.println("Офіціант не зміг отримати замовлення");
                Thread.currentThread().interrupt();
            }
            try {
                chefsQueue.put(order);
                System.out.println("Waiter sent order to chef: " + order);
            } catch (InterruptedException e) {
                System.out.println("Офіціант не зміг відправити замовлення кухарю");
                Thread.currentThread().interrupt();
            }

            try {
                preparedOrder = ordersQueue.take();
            } catch (InterruptedException e) {
                System.out.println("Офіціант не зміг отримати готову страву");
                Thread.currentThread().interrupt();
            }
            System.out.println("Waiter served order: " + preparedOrder);
        }
    }
}
