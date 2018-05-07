import java.util.*;

public class SampleDFG {
    // parameters for reservoir sampling
    private int k;    // the number of sets sampled
    private int setIndex;
    private List<Set<Integer>> Sample;
    private int[] sampleIndex;
    private static Random random;

    // parameters for set cover
    private List<Integer> A;
    private Set<Integer> C;

    private DFG dfg1;   //dfg for pass 1
    private DFG dfg2;   //dfg for pass 2
    private List<Integer> setInP2DFG;         //keep the index of set in p2 dfg


    static{
        random = new Random(System.currentTimeMillis());
    }

    public SampleDFG(){
        this.k = 5000;
        this.setIndex = 0;
        this.Sample = new ArrayList<Set<Integer>>();
        this.sampleIndex = new int[this.k];

        this.A = new ArrayList<Integer>();
        this.C = new HashSet<Integer>();

        this.dfg1 = new DFG();
        this.dfg2 = new DFG();
        this.setInP2DFG = new ArrayList<Integer>();

    }

    // Reservoir sampling
    public void p1Add(Set<Integer> set){

        if(this.setIndex < this.k){
            this.Sample.add(set);
            this.sampleIndex[this.setIndex] = this.setIndex;
        }
        else{
            int i = random.nextInt(this.setIndex+1);
            if(i < this.k){
                this.Sample.set(i, set);
                this.sampleIndex[i] = this.setIndex;
            }
        }
        this.setIndex ++;
    }


    public void doP1DFG(){
        for(Set<Integer> set: this.Sample){
            this.dfg1.add(set);
        }
        this.dfg1.doDFG();  //perform DFG
        for(int i: this.dfg1.getA()) {
            this.A.add(this.sampleIndex[i]);
        }
        this.C.addAll(this.dfg1.getC());

        // reset the index for next pass
        this.setIndex = 0;

        System.out.print("number of sets in p1 DFG: ");
        System.out.println(this.dfg1.getSetCount());
        System.out.print("number of items in p1 DFG: ");
        System.out.println(this.dfg1.getItemCount());
        System.out.print("number of items in U in p1 DFG: ");
        System.out.println(this.dfg1.getCSize());

    }

    public void p2Add(Set<Integer> set){
        set.removeAll(this.C);
        if(set.size()>0){
            this.dfg2.add(set);
            this.setInP2DFG.add(this.setIndex);
        }
        this.setIndex ++;
    }

    public void doP2DFG(){
        this.dfg2.doDFG();  //perform DFG
        for(int i: this.dfg2.getA()) {
            this.A.add(this.setInP2DFG.get(i));
        }
        this.C.addAll(this.dfg2.getC());

        System.out.print("number of sets in p2 DFG: ");
        System.out.println(this.dfg2.getSetCount());
        System.out.print("number of items in p2 DFG: ");
        System.out.println(this.dfg2.getItemCount());
        System.out.print("number of items in U in p2 DFG: ");
        System.out.println(this.dfg2.getCSize());

    }

    public int getSetCoverSize(){
        return this.A.size();
    }

    public int getCSize(){
        return this.C.size();
    }

    public int getSetCount(){
        return this.setIndex;
    }


}
