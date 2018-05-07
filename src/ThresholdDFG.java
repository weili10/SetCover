import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ThresholdDFG {
    private List<Integer> A;
    private Set<Integer> C;
    private Map<Integer,Set<Integer>> W;    //for post process   {index:Set}
    private List<Integer> setInW;           //keep the index of set in W
    private int setIndex;
    private double threshold;

    private DFG dfg;
    private List<Integer> setInDFG;         //keep the index of set in dfg

    public ThresholdDFG() {
        this.A = new ArrayList<Integer>();
        this.C = new HashSet<Integer>();
        this.W = new HashMap<Integer,Set<Integer>>();
        this.setInW = new ArrayList<Integer>();
        this.setIndex = 0;
//		this.threshold = 0.6;
        this.threshold = 2;

        this.dfg = new DFG();
        this.setInDFG = new ArrayList<Integer>();

    }


    public void add(Set<Integer> set){

        int oldSize = set.size();
        set.removeAll(this.C);
        int newSize = set.size();
//		double x = ((double)newSize)/((double)oldSize);
        int x = newSize;
        if(x > this.threshold){
            this.A.add(this.setIndex);
            this.C.addAll(set);
        }
        else if(x > 0){
            this.W.put(this.setIndex, set);
            this.setInW.add(this.setIndex);
        }
        this.setIndex ++;

    }
    public int getSetCoverSize(){
        System.out.print("threshold t: ");
        System.out.println(this.threshold);
        System.out.print("total number of sets: ");
        System.out.println(this.setIndex);
        System.out.print("size of A before post process: ");
        System.out.println(this.A.size());
        System.out.print("number of sets in W: ");
        System.out.println(this.W.size());
        System.out.print("number of items in U before DFG: ");
        System.out.println(this.C.size());

        this.doDFG();

        System.out.print("number of sets in DFG: ");
        System.out.println(this.dfg.getSetCount());
        System.out.print("number of items in DFG: ");
        System.out.println(this.dfg.getItemCount());
        System.out.print("number of items in U in DFG: ");
        System.out.println(this.dfg.getCSize());

        System.out.print("number of items in U: ");
        System.out.println(this.C.size());

        return this.A.size();
    }

    private void doDFG(){
        // add set S with |S/C| > 0 to DFG
        for(Integer index : this.setInW){
            Set<Integer> set = this.W.get(index);
            set.removeAll(this.C);
            if(set.size() == 0){
                this.W.remove(index);
            }
            else{
                this.dfg.add(set);
                this.setInDFG.add(index);
            }
        }
        // perform DFG algorithm
        this.dfg.doDFG();

        // update A from DFG
        for(Integer index: this.dfg.getA()){
            this.A.add(this.setInDFG.get(index));
        }

        //update C from DFG
        this.C.addAll(this.dfg.getC());
    }



}

