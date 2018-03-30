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
	private Map<Integer,List<Integer>> SetMap;
	private List<Set<String>> Sets;
	private int setIndex;
	private int max_k;
	private int p;
	public DFG() {
		this.A = new ArrayList<Integer>();
		this.C = new HashSet<String>();
		this.U = new HashSet<String>();
		this.SetMap = new HashMap<Integer,List<Integer>>();
		this.Sets = new ArrayList<Set<String>>();
		this.setIndex = 0;
		this.max_k = 0;
		this.p = 2;
	}
	
	private void addToSetMap(int index, Set<String> set){
		int k = (int) (Math.log(set.size())/Math.log(this.p));
		
		if(this.SetMap.containsKey(k)){
			List<Integer> setIndexList = this.SetMap.get(k);
			setIndexList.add(index);
			this.SetMap.replace(k, setIndexList);
		}else{
			List<Integer> setList = new ArrayList<Integer>();
			setList.add(index);
			this.SetMap.put(k,setList);
		}
		if(k > this.max_k){
			this.max_k = k;
		}
	}
	
	
	public void add(Set<String> set){
		this.Sets.add(set);
		this.addToSetMap(this.setIndex, set);
		this.U.addAll(set);
		this.setIndex ++;
	}
	
	public int getSetCoverSize(){
		for(int k = this.max_k; k > 0; k--){
			if(this.SetMap.containsKey(k)){
				List<Integer> indexList = this.SetMap.get(k);
				for(int i = 0; i< indexList.size(); i++){
					int index = indexList.get(i);
					Set<String> set = this.Sets.get(index);
					set.removeAll(this.C);
					if(set.size() >= Math.pow(this.p,k)){
						this.A.add(index);
						this.C.addAll(set);
					}else{
						if(set.size()>0){
							this.addToSetMap(index, set);
							this.Sets.set(index, set);
						}
					}
				}
			}
		}
		
		List<Integer> indexList = this.SetMap.get(0);
		for(int i = 0; i< indexList.size(); i++){
			int index = indexList.get(i);
			Set<String> set = this.Sets.get(index);
			set.removeAll(this.C);
			if(set.size() == 1){
				this.A.add(index);
				this.C.addAll(set);
			}
		}
		return this.A.size();
	}
	
	
	
}
