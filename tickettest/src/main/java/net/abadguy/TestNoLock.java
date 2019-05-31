package net.abadguy;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestNoLock {

    private int count = 200;

    private Lock lock=new ReentrantLock();

    @Test
    public void ticketTest() throws InterruptedException {
        TicketRunnable ticketRunnable=new TicketRunnable();

        Thread t1=new Thread(ticketRunnable,"窗口A");
        Thread t2=new Thread(ticketRunnable,"窗口B");
        Thread t3=new Thread(ticketRunnable,"窗口C");
        Thread t4=new Thread(ticketRunnable,"窗口D");



        t1.start();
        t2.start();
        t3.start();
        t4.start();

        Thread.currentThread().join();
    }


    public class TicketRunnable implements Runnable{

        public void run() {
            while (count>0){
               if(count>0){
                   lock.lock();
                   System.out.println(Thread.currentThread().getName()+"售出"+(count--)+"张票");
                   lock.unlock();
                   try {
                       Thread.sleep(200);
                   }catch (Exception e){

                   }
               }
            }
        }
    }
}
