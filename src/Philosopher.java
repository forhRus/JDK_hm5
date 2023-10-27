import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread {
    private String name;
    private boolean state;
    private Fork forkLeft;
    private Fork forkRight;
    private int count;
    private CountDownLatch finish;
    private ActionCounter counter;

    public Philosopher(String name, Fork forkLeft, Fork forkRight, CountDownLatch finish, ActionCounter counter) {
        this.counter = counter;
        this.name = name;
        this.finish = finish;
        this.state = false;
        this.forkLeft = forkLeft;
        this.forkRight = forkRight;
        this.count = 0;
    }

    @Override
    public void run() {
        while (count != 3) {
           tryEat();
        }
        System.out.printf("%s: Я покушал %d раза!\n", name, count);
        finish.countDown();

    }

    private void tryEat(){
        counter.incCounter();
        if (state) {
            getLeftFork();
        } else {
            System.out.printf("%s: Мне надо подумать!\n", name);
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            state = !state;
        }
    }

    private void getLeftFork() {
        if (forkLeft.isFree()) {
            forkLeft.setFree(false);
            System.out.printf("%s: Я взял левую вилку!\n", name);
            getRightFork();
            System.out.printf("%s: Я положил левую вилку!\n", name);
            forkLeft.setFree(true);
        }else {
            System.out.printf("%s: Я не могу взять левую вилку!\n", name);
        }
    }

    private  void getRightFork() {
        if (forkRight.isFree()) {
            forkRight.setFree(false);
            System.out.printf("%s: Я взял правую вилку!\n", name);
            System.out.printf("%s: Я кушаю в %d раз\n", name, ++count);
            System.out.printf("%s: Я положил правую вилку!\n", name);
            state = !state;
            forkRight.setFree(true);
        } else {
            System.out.printf("%s: Ах, я хотел поесть\n", name);
//            try {
//                sleep(100);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            forkLeft.setFree(true);
        }
    }
}