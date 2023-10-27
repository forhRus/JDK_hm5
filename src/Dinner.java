import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Dinner extends Thread {
    private List<Philosopher> philosophers;
    private CountDownLatch finish;
    private ActionCounter counter;

    public Dinner() {
        counter = new ActionCounter();
        this.philosophers = new ArrayList<>();
        finish = new CountDownLatch(5);
        Fork fork1 = new Fork();
        Fork fork2 = new Fork();
        Fork fork3 = new Fork();
        Fork fork4 = new Fork();
        Fork fork5 = new Fork();
        philosophers.add(new Philosopher("Сократ(1)", fork1, fork2, finish, counter));
        philosophers.add(new Philosopher("Аристотель(2)", fork2, fork3, finish, counter));
        philosophers.add(new Philosopher("Диоген(3)", fork3, fork4, finish, counter));
        philosophers.add(new Philosopher("Платон(4)", fork4, fork5, finish, counter));
        philosophers.add(new Philosopher("Софокол(5)", fork5, fork1, finish, counter));
    }

    @Override
    public void run() {

        try {
            goEat();
            finish.await();
            System.out.println("Все философы поели. " + counter.getCounter() + " попыток");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void start() {
        if (this != null) {
            run();
        }
    }

    public void setFinish(CountDownLatch finish) {
        this.finish = finish;
    }

    private void goEat(){
        for (Philosopher p : philosophers) {
            p.start();
        }
    }
}