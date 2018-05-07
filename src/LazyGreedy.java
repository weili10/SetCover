import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class LazyGreedy {
    private List<Integer> A;
    private Set<Integer> C;
    private Set<Integer> U;
    private List<Set<Integer>> Sets;
    private PriorityQueue<SetItem> pq;  //SetItem contains the size and index of an set
    private int setIndex;

    public LazyGreedy(){
        this.A = new ArrayList<Integer>();
        this.C = new HashSet<Integer>();
        this.U = new HashSet<Integer>();
        this.Sets = new ArrayList<Set<Integer>>();
        this.pq = new PriorityQueue<SetItem>();
        this.setIndex = 0;
    }

    public void add(Set<Integer> set){
        this.Sets.add(set);
        this.U.addAll(set);
        SetItem setItem = new SetItem(set.size(),this.setIndex);
        this.pq.add(setItem);
        this.setIndex ++;
    }

    public int getSetCoverSize(){
        while(!C.equals(U)){
            SetItem setItem =  this.pq.poll();
            int uc = setItem.getSize();
            int i = setItem.getIndex();
            Set<Integer> newSet = this.Sets.get(i) ;
            newSet.removeAll(this.C);
            if(newSet.size() == uc){
                this.A.add(i);
                this.C.addAll(newSet);
            }else{
                this.Sets.set(i, newSet);
                SetItem newSetItem = new SetItem(newSet.size(),i);
                this.pq.add(newSetItem);
            }
        }
        return this.A.size();
    }
}
