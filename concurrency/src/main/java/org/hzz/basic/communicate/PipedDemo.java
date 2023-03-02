package org.hzz.basic.communicate;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

public class PipedDemo {
    public static void main(String[] args) throws IOException {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        // 将输出流和输入流进行连接，否则在使用时会抛出IOException
        out.connect(in);

        Thread printThread = new Thread(new Printer(in));
        printThread.start();

        int r = 0;
        try{
            while((r = System.in.read()) != -1){
                out.write(r);
            }
        }finally {
            out.close();
        }

    }


    static class Printer implements Runnable{
        private final PipedReader in;
        public Printer(PipedReader in){
            this.in = in;
        }
        @Override
        public void run() {
            int r = 0;
            try {
                while(((r = in.read()) != -1)) {
                    System.out.print((char)r);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
