import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DFG {
    private List<Integer> A;
    private Set<Integer> C;
    private Map<Integer,List<Integer>> SetMap;  //{k:[index i for p^k < |Si| < p^(k+1)]}
    private List<Set<Integer>> Sets;
    private int setIndex;
    private int itemCount;
    private int max_k;
    private double p;

    public DFG() {
        this.A = new ArrayList<Integer>();
        this.C = new HashSet<Integer>();
        this.SetMap = new HashMap<Integer,List<Integer>>();
        this.Sets = new ArrayList<Set<Integer>>();
        this.setIndex = 0;
        this.itemCount = 0;
        this.max_k = 0;
        this.p = 1.1;
    }

    private void updateSetMap(int index, int setSize){
        int k = (int) (Math.log(setSize)/Math.log(this.p));

        if(this.SetMap.containsKey(k)){
            List<Integer> setIndexList = this.SetMap.get(k);
            setIndexList.add(index);
            this.SetMap.put(k, setIndexList);
        }else{
            List<Integer> setList = new ArrayList<Integer>();
            setList.add(index);
            this.SetMap.put(k,setList);
        }
        if(k > this.max_k){
            this.max_k = k;
        }
    }

    public void add(Set<Integer> set){
        this.Sets.add(set);
        this.updateSetMap(this.setIndex, set.size());
        this.setIndex ++;
        this.itemCount += set.size();
    }


    public void doDFG(){
        for(int k = this.max_k; k > 0; k--){
            if(this.SetMap.containsKey(k)){
                List<Integer> indexList = this.SetMap.get(k);
                for(int i = 0; i< indexList.size(); i++){
                    int index = indexList.get(i);
                    Set<Integer> set = this.Sets.get(index);
                    set.removeAll(this.C);
                    if(set.size() >= Math.pow(this.p,k)){
                        this.A.add(index);
                        this.C.addAll(set);
                    }else{
                        if(set.size()>0){
                            this.updateSetMap(index, set.size());
                            this.Sets.set(index, set);
                        }
                    }
                }
            }
        }

        if(this.SetMap.containsKey(0)){
            List<Integer> indexList = this.SetMap.get(0);
            for(int i = 0; i< indexList.size(); i++){
                int index = indexList.get(i);
                Set<Integer> set = this.Sets.get(index);
                set.removeAll(this.C);
                if(set.size() == 1){
                    this.A.add(index);
                    this.C.addAll(set);
                }
            }
        }


    }

    public int getSetCoverSize(){
        this.doDFG();

        System.out.print("number of sets in DFG: ");
        System.out.println(this.setIndex);
        System.out.print("number of items in DFG: ");
        System.out.println(this.itemCount);
        System.out.print("number of items in U: ");
        System.out.println(this.C.size());
        return this.A.size();
    }

    public int getCSize(){
        return this.C.size();
    }

    public List<Integer> getA(){
        return this.A;
    }

    public Set<Integer> getC(){
        return this.C;
    }



    public int getSetCount(){
        return this.setIndex;
    }

    public int getItemCount(){
        return this.itemCount;
    }
}
