package com.test.demo;

public class EarlyNotify {

    private static String lockObject = "lockObject";
    private static boolean isWait = true;

    public static void main(String[] args){

        Thread waitThread = new Thread(new WaitThread(lockObject),"111");
        Thread notifyThread = new Thread(new NotifyThread(lockObject),"222");

        notifyThread.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitThread.start();

    }


    static class WaitThread implements Runnable{

        private String lock;

        public WaitThread(String lock){
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock){
                try {
                    while (isWait){
                        System.out.println(Thread.currentThread().getName()+"进入代码库");
                        System.out.println(Thread.currentThread().getName()+"开始等待");
                        lock.wait();
                        System.out.println(Thread.currentThread().getName()+"结束等待");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }


    static class NotifyThread implements Runnable{

        private String lock;

        public NotifyThread(String lock){
            this.lock = lock;
        }

        @Override
        public synchronized void run() {
            synchronized (lock){

                System.out.println(Thread.currentThread().getName()+"进入代码块");
                System.out.println(Thread.currentThread().getName()+"开始唤醒");
                lock.notify();
                isWait =false;
                System.out.println(Thread.currentThread().getName()+"结束唤醒");

            }
        }
    }


}
