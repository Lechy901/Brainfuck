
public class DepthCounter {
	private int depth = 0;
	
	public void increment() {
		depth++;
	}
	
	public void decrement() throws Exception {
		if (--depth < 0)
			throw new Exception("unpaired");
	}
	
	public boolean isZero() {
		return depth == 0;
	}
}
