import java.util.concurrent.*;

public class Restaurant {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> usersOrders = new ArrayBlockingQueue<>(5);
        BlockingQueue<String> chefsOrders = new ArrayBlockingQueue<>(5);
        BlockingQueue<String> preparedOrders = new ArrayBlockingQueue<>(5);
        ExecutorService executor = Executors.newFixedThreadPool(10);

        Waiter waiter = new Waiter();
        Chef chef = new Chef();

        Thread waiterThread = new Thread(() -> waiter.serve(usersOrders, chefsOrders, preparedOrders));

        Thread chefThread = new Thread(() -> chef.cook(chefsOrders, preparedOrders));

        waiterThread.start();
        chefThread.start();

        for (int i = 0; i < 10; i++) {
            int userId = i;
            executor.execute(() -> new User().orderDish(userId, usersOrders));
        }

        executor.shutdown();

        waiterThread.join();
        chefThread.join();
        waiterThread.interrupt();
        chefThread.interrupt();
    }
}
