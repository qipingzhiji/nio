package com.pipe;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by zhang_htao on 2019/6/13.
 */
public class transefor {
    public static void main(String[] args) throws  Exception{
        Sender sender = new Sender();
        Receive receive = new Receive(sender);
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(sender);
        executorService.execute(receive);
        Thread.sleep(20000);
        executorService.shutdown();
        System.out.println("over......");
    }
}

class Sender implements Runnable{
    private PipedWriter pipedWriter = new PipedWriter();
    @Override
    public void run() {
        try {
            for (char a ='a'; a<='z';a++) {
                Thread.sleep(500);
                pipedWriter.write(a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PipedWriter getPipedWriter() {
        return pipedWriter;
    }
}


class Receive implements Runnable {
    private PipedReader pipedReader;
    @Override
    public void run() {
        while(true) {
            try {
                char a = (char)pipedReader.read();
                System.out.println(a + " ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Receive(Sender sender) throws  Exception{
        pipedReader = new PipedReader(sender.getPipedWriter());
    }
}
