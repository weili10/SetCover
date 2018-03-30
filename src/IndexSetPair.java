import java.util.Set;

public class IndexSetPair {
	private int index;
	private Set<String> set;
	
	public IndexSetPair(int index, Set<String> set) {
		this.index = index;
		this.set = set;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Set<String> getSet() {
		return set;
	}

	public void setSet(Set<String> set) {
		this.set = set;
	}
}
