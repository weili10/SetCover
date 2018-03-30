import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DFG {
	private List<Integer> A;
	private Set<String> C;
	private Set<String> U;
	private Map<Integer,List<IndexSetPair>> Sets;
	private int setIndex;
	private int max_k;
	private int p;
	public DFG() {
		this.A = new ArrayList<Integer>();
		this.C = new HashSet<String>();
		this.U = new HashSet<String>();
		this.Sets = new HashMap<Integer,List<IndexSetPair>>();
		this.setIndex = 0;
		this.max_k = 0;
		this.p = 2;
	}
	
	private void addToSets(int index, Set<String> set){
		int k = (int) (Math.log(set.size())/Math.log(this.p));
		IndexSetPair newSet = new IndexSetPair(index,set);
		if(this.Sets.containsKey(k)){
			List<IndexSetPair> setList = this.Sets.get(k);
			setList.add(newSet);
			this.Sets.replace(k, setList);
		}else{
			List<IndexSetPair> setList = new ArrayList<IndexSetPair>();
			setList.add(newSet);
			this.Sets.put(k,setList);
		}
		if(k > this.max_k){
			this.max_k = k;
		}
	}
	
	
	public void add(Set<String> set){
		this.addToSets(this.setIndex, set);
		this.U.addAll(set);
		this.setIndex ++;
	}
	
	public int getSetCoverSize(){
		for(int k = this.max_k; k > 0; k--){
			if(this.Sets.containsKey(k)){
				List<IndexSetPair> lisp = this.Sets.get(k);
				for(int i = 0; i< lisp.size(); i++){
					IndexSetPair isp = lisp.get(i);
					int index = isp.getIndex();
					Set<String> set = isp.getSet();
					set.removeAll(this.C);
					if(set.size() >= Math.pow(this.p,k)){
						this.A.add(index);
						this.C.addAll(set);
					}else{
						if(set.size()>0){
							this.addToSets(index, set);
						}
					}
				}
			}
		}
		
		List<IndexSetPair> lisp = this.Sets.get(0);
		for(int i = 0; i< lisp.size(); i++){
			IndexSetPair isp = lisp.get(i);
			int index = isp.getIndex();
			Set<String> set = isp.getSet();
			set.removeAll(this.C);
			if(set.size() == 1){
				this.A.add(index);
				this.C.addAll(set);
			}
		}
		return this.A.size();
	}
	
	
	
}
