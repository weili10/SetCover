import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MultiPassTest {

    public static void main(String args[]){
        double totalTime = 0;
        long startTime = 0;
        long endTime = 0;
        int passNum = 2;

        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();

        // Run the garbage collector
        runtime.gc();



//		LazyGreedy SetCover = new LazyGreedy();
//      DFG SetCover = new DFG();
//		TPP SetCover = new TPP();

        SampleDFG sDFG = new SampleDFG();

        String fileName = args[0];
        BufferedReader reader = null;
        try{
            startTime = System.currentTimeMillis();

            for(int pass = 1; pass <= passNum; pass++) {
                //open the test dataset file
                File file = new File(fileName);
                reader = new BufferedReader(new FileReader(file));
                Object tempLine = null;


                while ((tempLine = reader.readLine()) != null) {
                    //get the new set
                    String[] input = tempLine.toString().split(" ");
                    List<Integer> nums = new ArrayList<Integer>();
                    Set<Integer> set = new HashSet<Integer>();
                    for (String number : input) {
                        nums.add(Integer.parseInt(number));
                    }
                    set.addAll(nums);

                    // process the new set in different pass
                    if (pass == 1)
                        sDFG.p1Add(set);
                    else {
                        sDFG.p2Add(set);
                    }
                }
                reader.close();

                if (pass == 1) {
                    sDFG.doP1DFG();
                    System.out.print("size of A after p1 DFG: ");
                    System.out.println(sDFG.getSetCoverSize());
                }else {
                    sDFG.doP2DFG();
                }

            }

        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.print("total number of sets: ");
        System.out.println(sDFG.getSetCount());
        System.out.print("number of items in U: ");
        System.out.println(sDFG.getCSize());
        System.out.print("Size of Set Cover: ");
        System.out.println(sDFG.getSetCoverSize());


        endTime = System.currentTimeMillis();
        totalTime = endTime-startTime;

        System.out.format("run time: %d ms\n",(int)totalTime);

        // Run the garbage collector
//        runtime.gc();

        // Calculate and print the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.format("Used memory: %d bytes  /  %f MB%n",memory,(double)memory/1024/1024);
    }

}

