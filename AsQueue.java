class AsQueue{
	int row;
	int column;
	boolean samePlace;
	AsQueue next;
	public AsQueue(int row, int column, boolean samePlace){
		this.row = row;
		this.column = column;
		this.samePlace = false;
		this.next = null;
	}
}