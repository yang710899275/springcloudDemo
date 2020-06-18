package com.example.springclouddemo.testinputstream;

import java.io.*;

public class testinputstream {
    public static void main(String[] args) {
        FileOutputStream fout;

        {
            try {
                fout = new FileOutputStream("E:/aaa.txt");
                BufferedOutputStream bout = new BufferedOutputStream(fout);
                DataOutputStream dout = new DataOutputStream(bout);
                dout.writeInt(3);
                dout.writeBoolean(false);
                dout.flush();
                dout.close();

                DataInputStream din = new DataInputStream(new BufferedInputStream(new FileInputStream("E:/aaa.txt")));
                System.out.println(din.readInt());
                System.out.println(din.readBoolean());
                din.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
