package designpatterns.adapter;

public class OperationAdaptor implements ScoreOperation {
	private QuickSort sortObj;
	private BinarySearch searchObj;

	public OperationAdaptor() {
		this.sortObj = new QuickSort();
		this.searchObj = new BinarySearch();
	}

	public int[] sort(int[] array) {
		return this.sortObj.quickSort(array);
	}

	public int search(int[] array, int key) {
		return this.searchObj.binarySearch(array, key);
	}

}
