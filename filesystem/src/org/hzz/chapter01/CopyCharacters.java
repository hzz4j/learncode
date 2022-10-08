package org.hzz.chapter01;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CopyCharacters {
    public static void main(String[] args) throws IOException {
        FileReader fileReader = null;
        FileWriter fileWriter = null;

        try{
            fileReader = new FileReader("xanadu.txt");
            fileWriter = new FileWriter("characteroutput.txt");
            // in CopyCharacters.java, the int variable holds a character value in its last 16 bits;
            // in CopyBytes.java, the int variable holds a byte value in its last 8 bits.
            int c;
            while((c = fileReader.read()) != -1){
                fileWriter.write(c);
            }
        }finally {
            if(fileReader != null){
                fileReader.close();
            }

            if(fileWriter != null){
                fileWriter.close();
            }
        }
    }
}
