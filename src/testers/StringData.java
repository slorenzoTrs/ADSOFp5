package testers;

public class StringData {
	private String word;
	private String result;
	private int times;
	
	public StringData(String word, String result, int times) {
		this.word = word;
		this.result = result;
		this.times = times;
	}
	
	public NumericData toNumericData() {
		NumericData nmd = new NumericData(times, 0);
		return nmd;
	}
	
	public void replicate() {
		this.times--;
		this.result += this.word;
	}
	
	public void setTimes(int times) {
		this.times = times;
	}
	
	public int times() {
		return times;
	}
	
	@Override
	public String toString() {
		return "word: " + word + ", times: " + times + ", result: " + result;
	}
}
