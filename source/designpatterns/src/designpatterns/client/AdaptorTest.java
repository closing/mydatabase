package designpatterns.client;

import designpatterns.adapter.Adaptor;
import designpatterns.adapter.OperationAdaptor;
import designpatterns.adapter.ScoreOperation;
import designpatterns.adapter.Source;
import designpatterns.adapter.SourceSub1;
import designpatterns.adapter.SourceSub2;
import designpatterns.adapter.Targetable;
import designpatterns.adapter.Wrapper;

public class AdaptorTest {
	public static void main(String[] args) {
		Targetable target = new Adaptor();
		target.Method1();
		target.Method2();

		Wrapper wrapper = new Wrapper(new Source());
		wrapper.Method1();
		wrapper.Method2();

		SourceSub1 source1 = new SourceSub1();
		SourceSub2 source2 = new SourceSub2();
		source1.Method1();
		source1.Method2();
		source2.Method1();
		source2.Method2();

		ScoreOperation operation;
		operation = new OperationAdaptor();
		int[] scores = { 87, 76, 50, 69, 90, 91, 88, 96 };
		int[] result;
		int score;
		System.out.println("sorted:");
		result = operation.sort(scores);
		for (int i : result) {
			System.out.println(i + ",");
		}
		System.out.println("");

		System.out.println("Search 90:");
		score = operation.search(result, 90);
		if (score != -1) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}
		System.out.println("Search 92:");
		score = operation.search(result, 92);
		if (score != -1) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}

	}
}
