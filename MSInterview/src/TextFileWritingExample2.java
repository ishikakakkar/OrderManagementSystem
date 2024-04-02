import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException; /**
 * This program demonstrates how to write characters to a text file
 * using a BufferedReader for efficiency.
 * @author www.codejava.net
 *
 */

import java.util.ArrayList;

public class TextFileWritingExample2 {
    FileWriter writer;
    BufferedWriter bufferedWriter;
    public void initializer(String filename)
    {
        try{
            writer = new FileWriter(filename, false);
            bufferedWriter = new BufferedWriter(writer);
//            bufferedWriter.write("HEllo");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void write(ArrayList<Integer> list)
    {

        try {

//            bufferedWriter.write("HEllo");
            for(int i=0; i<list.size(); i++)
            {
                System.out.println(list.get(i).toString());
                bufferedWriter.write(list.get(i).toString());
                bufferedWriter.newLine();
            }
//            bufferedWriter.write("Hello World");
//            bufferedWriter.newLine();
//            bufferedWriter.write("See You Again!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void close()
    {
        try {
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {

    }
}
