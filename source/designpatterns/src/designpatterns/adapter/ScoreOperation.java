package designpatterns.adapter;

public interface ScoreOperation {
	public int[] sort(int[] array);

	public int search(int[] array, int key);
}
