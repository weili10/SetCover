import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Test {

	public static void main(String args[]){
//		LazyGreedy lg = new LazyGreedy();
		DFG dfg = new DFG();
		
		String fileName = args[0];
		BufferedReader reader = null;
    	try{
    		//open the test dataset file
            File file = new File(fileName);
            reader = new BufferedReader(new FileReader(file));
            Object tempLine = null;
            while((tempLine = reader.readLine())!=null){
                //process the new set
                String[] input = tempLine.toString().split(" ");
                Set<String> set = new HashSet<String>(Arrays.asList(input));
//                lg.add(set);
                dfg.add(set);
            }
            reader.close();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
//    	System.out.println(lg.getSetCoverSize());
    	System.out.println(dfg.getSetCoverSize());
	}

}
