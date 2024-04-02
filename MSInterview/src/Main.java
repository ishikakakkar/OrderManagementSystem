//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.


import java.util.ArrayList;
import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

//        for (int i = 1; i <= 5; i++) {
//            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
//            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
//            System.out.println("i = " + i);
//        }
        TextFileReadingExample3 readObj = new TextFileReadingExample3();
        TextFileWritingExample2 writeobj = new TextFileWritingExample2();
        readObj.pointer();
//        writeobj.initializer();
//        writeobj.write(new ArrayList<>());
        int itr=1;
        while(true)
        {
            ArrayList<Integer> list = readObj.read();

            if(list.isEmpty())
                break;
            Collections.sort(list);
            writeobj.initializer("NumbersWrite"+itr+".txt");
            writeobj.write(list);
            writeobj.close();
            itr++;
        }
        readObj.close();
        writeobj.close();

    }
}