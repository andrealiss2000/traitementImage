package traitementImageProject;

import java.util.Map;

public class QualityEvaluator {
	
	public static double evaluate(String queryCategory, Map<Double,String> result) {
		int totalSize=result.size();
		int cpt=0;
		for (Map.Entry<Double, String> entry : result.entrySet()) {
			if(entry.getValue().contains(queryCategory)) {
				cpt++;
			}
	    }
		return (cpt/totalSize)*100;
	}

}
