
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public class HolderTest {
    private static final ConcurrentMap<String, Holder<Object>> cachedInstances        = new ConcurrentHashMap<>();
    private static Executor executor = command -> {
        new Thread(command).start();
    };
    private static CountDownLatch latch = new CountDownLatch(1);
    private static String[] names = {"hzz","Q10Viking"};
    public static void main(String[] args) {
        for (int i = 0; i < 6; i++) {
            final int finalI = i;
            executor.execute(() -> {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Holder<Object> holder = getOrCreateHolder(names[finalI %2]);
                Object instance = holder.get();
                if (instance == null) {
                    synchronized (holder) {
                        instance = holder.get();
                        if (instance == null) {
                            instance = new String(names[finalI %2] +" Object");
                            System.out.println(Thread.currentThread().getName()+": create "+instance);
                            holder.set(instance);
                        }
                    }
                }
                System.out.println(Thread.currentThread().getName()+": print "+ instance);
            });
        }
        latch.countDown();
    }

    private static Holder<Object> getOrCreateHolder(String name) {
       Holder<Object> holder = cachedInstances.get(name);
        if (holder == null) {
            cachedInstances.putIfAbsent(name, new Holder<>());
            holder = cachedInstances.get(name);
        }
        return holder;
    }
}
