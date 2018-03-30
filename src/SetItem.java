
public class SetItem implements Comparable<SetItem>{
	private int size;
	private int index;
	
	public SetItem(int size, int index) {
		super();
		this.size = size;
		this.index = index;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public int compareTo(SetItem o) {
		if(this.size < o.size){
			return 1;
		}else{
			return -1;
		}
	}
}
