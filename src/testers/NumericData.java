package testers;

import java.util.LinkedHashMap;

public class NumericData extends LinkedHashMap<String, Integer>{
	private static final long serialVersionUID = 1L;

	public NumericData(int op1, int op2) {
		this.put("op1", op1);
		this.put("op2", op2);
		this.put("result", 0);
	}
}
