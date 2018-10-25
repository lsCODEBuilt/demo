package com.test.Contrloller;

import com.test.common.dto.Result;
import com.test.model.Member;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@RestController
public class DemoController {

    public Result saveMember(){
        Member member = new Member();
        member.setAccountNo("11111");
        return Result.success(member);
    }

//    下面都假设任务队列没有大小限制：
//
//    如果线程数量<=核心线程数量，那么直接启动一个核心线程来执行任务，不会放入队列中。
//    如果线程数量>核心线程数，但<=最大线程数，并且任务队列是LinkedBlockingDeque的时候，超过核心线程数量的任务会放在任务队列中排队。
//    如果线程数量>核心线程数，但<=最大线程数，并且任务队列是SynchronousQueue的时候，线程池会创建新线程执行任务，这些任务也不会被放在任务队列中。这些线程属于非核心线程，在任务完成后，闲置时间达到了超时时间就会被清除。
//    如果线程数量>核心线程数，并且>最大线程数，当任务队列是LinkedBlockingDeque，会将超过核心线程的任务放在任务队列中排队。也就是当任务队列是LinkedBlockingDeque并且没有大小限制时，线程池的最大线程数设置是无效的，他的线程数最多不会超过核心线程数。
//    如果线程数量>核心线程数，并且>最大线程数，当任务队列是SynchronousQueue的时候，会因为线程池拒绝添加任务而抛出异常。
//    任务队列大小有限时
//
//    当LinkedBlockingDeque塞满时，新增的任务会直接创建新线程来执行，当创建的线程数量超过最大线程数量时会抛异常。
//    SynchronousQueue没有数量限制。因为他根本不保持这些任务，而是直接交给线程池去执行。当任务数量超过最大线程数时会直接抛异常。
//            ---------------------
//    作者：喵了个呜s
//    来源：CSDN
//    原文：https://blog.csdn.net/qq_25806863/article/details/71126867
//    版权声明：本文为博主原创文章，转载请附上博文链接！

    public static void main(String[] args){
        //ThreadPoolExecutor线程池
        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + "run");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        //核心线程数为6，最大线程数为10。超时时间为5秒
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(6,10,5, TimeUnit.SECONDS,new SynchronousQueue<>());
        //核心线程数为3，最大线程数为6。超时时间为5秒,队列是LinkedBlockingDeque
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(3,6,5, TimeUnit.SECONDS,new LinkedBlockingDeque<>());
        //核心线程数为3，最大线程数为6。超时时间为5秒,队列是SynchronousQueue
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(3,6,5, TimeUnit.SECONDS,new SynchronousQueue<>());
        //核心线程数是3，最大线程数是4，队列是LinkedBlockingDeque
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        //LinkedBlockingDeque根本不受最大线程数影响。

        //但是当LinkedBlockingDeque有大小限制时就会受最大线程数影响了
        //ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(2));
        //将队列大小设置为1
//        直接出错在第6个execute方法上。因为核心线程是3个，当加入第四个任务的时候，就把第四个放在队列中。加入第五个任务时，因为队列满了，就创建新线程执行，创建了线程4。当加入第六个线程时，也会尝试创建线程，但是因为已经达到了线程池最大线程数，所以直接抛异常了。
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 4, 5, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(1));
        try {
            executor.execute(myRunnable);
            executor.execute(myRunnable);
            executor.execute(myRunnable);
            System.out.println("---先开三个---");
            System.out.println("核心线程数" + executor.getCorePoolSize());
            System.out.println("线程池数" + executor.getPoolSize());
            System.out.println("队列任务数" + executor.getQueue().size());
            executor.execute(myRunnable);
            executor.execute(myRunnable);
            executor.execute(myRunnable);
            System.out.println("---再开三个---");
            System.out.println("核心线程数" + executor.getCorePoolSize());
            System.out.println("线程池数" + executor.getPoolSize());
            System.out.println("队列任务数" + executor.getQueue().size());
            Thread.sleep(8000);
            System.out.println("----8秒之后----");
            System.out.println("核心线程数" + executor.getCorePoolSize());
            System.out.println("线程池数" + executor.getPoolSize());
            System.out.println("队列任务数" + executor.getQueue().size());
        }catch (InterruptedException e){
            e.printStackTrace();
        }



    }
}
