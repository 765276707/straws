package com.gitee.pristine.core.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池管理
 * @author Pristine Xu
 *
 * <b>Note </b> 负责线程的统一调度和管理
 * <pre>
 *     1.您可以在项目中需要使用线程池的地方像这样来使用：
 *     <code>
 *         ThreadPoolManager manager = ThreadPoolManager.getInstance();
 *         manager.addTask(new Runnable() {...});
 *         manager.shutdown();
 *         while (!manager.awaitTermination(1, TimeUnit.SECONDS)) {
 *             System.out.println("thread pool had been shutdown......");
 *         }
 *     </code>
 *     2.您可以根据您的实际生产环境来对下面的参数进行合适的优化改动：
 *     -> CORE_POOL_SIZE         : 核心线程数
 *     -> MAXIMUM_POOL_SIZE      : 非核心最大线程数
 *     -> KEEP_ALIVE_TIME        : 线程闲置超时时间
 *     -> THREAD_POOL_QUEUE_SIZE : 线程等待队列容量
 * </pre>
 */
public class ChunkThreadPoolExecutor {

    private final static Logger logger = LoggerFactory.getLogger(ChunkThreadPoolExecutor.class);

    // 管理器实例（线程可见）
    private static volatile ChunkThreadPoolExecutor instance;
    // 时间格式化（毫秒级）
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    // 线程池名称
    private final static String threadPoolName = "ThreadPoolManager";

    // CPU 核心数
    private final static int CPU_CORE_NUMBER = Runtime.getRuntime().availableProcessors();
    /**
     * 线程池的核心线程数
     * <b>Note:</b> 此处的值根据实际部署的服务器类型进行合适的变动
     * <pre>CPU密集型: 线程数 = CPU核心数</pre>
     * <pre>IO 密集型: 线程数 = CPU核心数 * 2 </pre>
     */
    private static int CORE_POOL_SIZE = CPU_CORE_NUMBER * 2;
    /**
     * 线程池的非核心线程数
     * <b>Note:</b> 核心线程数 * 2
     */
    private static int MAXIMUM_POOL_SIZE = CORE_POOL_SIZE * 2;
    // 非核心线程闲置超时时间（毫秒）
    private static int KEEP_ALIVE_TIME = 1000;
    // 线程等待队列容量
    private static int THREAD_POOL_QUEUE_SIZE = 100;

    /**
     * 执行任务的线程占比阈值
     * <b>Note:</b> 占比超过阈值，就会打印预警日志
     */
    private static double activeThreadThreshold = 0.95;

    // 线程池执行器
    private ThreadPoolExecutor executor;

    // 线程等待队列
    private static ArrayBlockingQueue threadPoolQueue =  new ArrayBlockingQueue<>(THREAD_POOL_QUEUE_SIZE);

    private ChunkThreadPoolExecutor() {
    }

    /**
     * 获取管理器单例
     * @return
     */
    public static ChunkThreadPoolExecutor getInstance() {
        if (instance == null) {
            synchronized (ChunkThreadPoolExecutor.class) {
                if (instance == null) {
                    instance = new ChunkThreadPoolExecutor();
                    instance.executor = new ThreadPoolExecutor(
                        CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME,
                            TimeUnit.MILLISECONDS, threadPoolQueue, new CustomizeThreadFactory(threadPoolName)
                    );
                }
            }
        }
        return instance;
    }

    /**
     * 添加任务
     * @param task 任务
     * @return
     */
    public <T> Future<T> addTask(Callable<T> task) {
        if (task == null) {
            throw new IllegalArgumentException("无法将空的任务添加到线程池中执行.");
        }
        // 预警判断
        assertPoolFull();
        // 提交任务
        return executor.submit(task);
    }

    /**
     * 添加任务
     * @param task 任务
     */
    public void addTask(Runnable task) {
        if (task == null) {
            throw new IllegalArgumentException("无法将空的任务添加到线程池中执行.");
        }
        // 预警判断
        assertPoolFull();
        // 提交任务
        executor.execute(task);
    }

    /**
     * 添加任务集
     * @param tasks 任务集
     */
    public void addTasks(Collection<Runnable> tasks) {
        if (tasks == null) {
            throw new IllegalArgumentException("无法将空的任务组添加到线程池中执行.");
        }
        // 预警判断
        assertPoolFull();
        // 提交任务
        tasks.forEach(this::addTask);
    }

    /**
     * 批量调用任务组
     * @param tasks 任务组
     * @param <T>
     * @return
     * @throws InterruptedException
     */
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        if (tasks == null) {
            throw new IllegalArgumentException("无法将空的任务组添加到线程池中执行.");
        }
        // 预警判断
        assertPoolFull();
        // 提交任务
        return executor.invokeAll(tasks);
    }

    /**
     * 批量调用任务组
     * @param tasks 任务组
     * @param timeout 超时时间
     * @param unit 时间单位
     * @param <T>
     * @return
     * @throws InterruptedException
     */
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        if (tasks == null) {
            throw new IllegalArgumentException("无法将空的任务组添加到线程池中执行.");
        }
        // 预警判断
        assertPoolFull();
        // 提交任务
        return executor.invokeAll(tasks, timeout, unit);
    }

    /**
     * 随机调用任务组中一个任务
     * @param tasks 任务组
     * @param <T>
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks)
            throws InterruptedException, ExecutionException {
        // 预警判断
        assertPoolFull();
        // 随机调用任务
        return executor.invokeAny(tasks);
    }

    /**
     * 随机调用任务组中一个任务
     * @param tasks 任务组
     * @param timeout 超时时间
     * @param unit 时间单位
     * @param <T>
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     */
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks,
                           long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        // 预警判断
        assertPoolFull();
        // 随机调用任务
        return executor.invokeAny(tasks, timeout, unit);
    }

    /**
     * 预警线程池即将满了
     */
    public void assertPoolFull() {
        // 正在执行任务的线程
        int activeCount = executor.getActiveCount();
        // 最大非核心线程数
        int maximumPoolSize = executor.getMaximumPoolSize();
        double currentPercent = (double) activeCount/maximumPoolSize;
        if (currentPercent > activeThreadThreshold) {
            logger.warn("线程池已经超过指定阈值，当前线程池的状态：{} -> 线程池已用线程占比为 {}", threadPoolName, currentPercent);
        }
    }

    /**
     * 获取线程池中的任务数
     * @return
     */
    public long getTaskCount() {
        return executor.getTaskCount();
    }

    /**
     * 获取线程池中已执行完的任务数
     * @return
     */
    public long getCompletedTaskCount() {
        return executor.getCompletedTaskCount();
    }

    /**
     * 线程池是否已关闭
     * @return
     */
    public boolean isShutdown() {
        return executor.isShutdown();
    }

    /**
     * 关闭线程池
     * <b>Note</b> 会等待线程池中的任务都结束了才会关闭
     */
    public void shutdown() {
        executor.shutdown();
    }

    /**
     * 所有任务是否已经结束
     * @return
     */
    public boolean isTermination() {
        return executor.isTerminated();
    }

    /**
     * 阻塞等待所有任务结束时
     * @param timeout 超时时间
     * @param unit 时间单位
     * @return
     * @throws InterruptedException
     */
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return executor.awaitTermination(timeout, unit);
    }

    /**
     * 立刻关闭线程池
     */
    public void shutdownImmediately() {
        executor.shutdownNow();
    }

    /**
     * 移除任务
     * @param task 任务
     */
    public void remove(Runnable task) {
        executor.remove(task);
    }


    public void setCorePoolSize(int corePoolSize) {
        executor.setCorePoolSize(corePoolSize);
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        executor.setMaximumPoolSize(maximumPoolSize);
    }

    public void setKeepAliveTime(long keepAliveTime, TimeUnit unit) {
        executor.setKeepAliveTime(keepAliveTime, unit);
    }

    public BlockingQueue<Runnable> getQueue() {
        return executor.getQueue();
    }


    /**
     * 自定义的线程工厂类
     * <b>Note </b> 主要是为了明确的指定线程名称
     * @author xzb
     */
    static class CustomizeThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        CustomizeThreadFactory(String poolName) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = poolName + "-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
