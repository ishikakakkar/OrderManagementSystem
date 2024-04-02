import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException; /**
 * This program demonstrates how to read characters from a text file
 * using a BufferedReader for efficiency.
 * @author www.codejava.net
 *
 */
import java.util.ArrayList;


public class TextFileReadingExample3 {

//    static ArrayList<ArrayList<Integer>> listOfList= new ArrayList<>();
    FileReader reader;
    BufferedReader bufferedReader;

    public void pointer()
    {
        try {
            this.reader = new FileReader("C:\\Users\\Ishika\\Downloads\\order management system\\MSInterview\\src\\Numbers.txt");
            this.bufferedReader = new BufferedReader(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Integer> read()
    {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            String line;
            int chunk=6;
            int i=0;
            while (bufferedReader!=null && (line = bufferedReader.readLine()) != null && chunk>0) {
                list.add(Integer.parseInt(line));
//                System.out.println(line);
//                System.out.println(list.get(i));
                i++;
                chunk--;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void close()
    {
        try{
            if(reader!=null)
                reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
    }

}
