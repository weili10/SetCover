import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {

    public static void main(String args[]){
        double totalTime = 0;
        long startTime = 0;
        long endTime = 0;

        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();

        // Run the garbage collector
        runtime.gc();

//		LazyGreedy SetCover = new LazyGreedy();
//		DFG SetCover = new DFG();
        ThresholdDFG SetCover = new ThresholdDFG();

        String fileName = args[0];
        BufferedReader reader = null;
        try{
            //open the test dataset file
            File file = new File(fileName);
            reader = new BufferedReader(new FileReader(file));
            Object tempLine = null;

            startTime = System.currentTimeMillis();

            while((tempLine = reader.readLine())!=null){
                //process the new set
                String[] input = tempLine.toString().split(" ");
                List<Integer> nums = new ArrayList<Integer>();
                Set<Integer> set = new HashSet<Integer>();
                for(String number: input) {
                    nums.add(Integer.parseInt(number));
                }
                set.addAll(nums);
                SetCover.add(set);
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
        }



        int setCoverSize = SetCover.getSetCoverSize();
        System.out.print("Size of Set Cover: ");
        System.out.println(setCoverSize);


        endTime = System.currentTimeMillis();
        totalTime = endTime-startTime;

        System.out.format("run time: %d ms\n",(int)totalTime);

//        // Get the Java runtime
//        Runtime runtime = Runtime.getRuntime();

        // Run the garbage collector
        runtime.gc();


        // Calculate and print the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.format("Used memory: %d bytes  /  %f MB%n",memory,(double)memory/1024/1024);
    }

}
