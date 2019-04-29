package ip.corejava.enumordering;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EnumOrdering {
	
	enum ITEM{
		FIRST_ITEM(5), SECOND_ITEM(3), THRID_ITEM(1), FOURTH_ITEM(2), FIFTH_ITEM(4);
		
		private final int rank;

		private ITEM(int rank){
			this.rank = rank;
		}
		public int getRank(){
			return rank;
		}
	}
	
	class ItemComparator implements Comparator<ITEM>{
		@Override
		public int compare(ITEM o1, ITEM o2) {
			return o1.getRank() - o2.getRank();
		}
	}
	
	public static void main(String[] args) {
		EnumOrdering enumOrdering = new EnumOrdering();
		List<ITEM> enumList = Arrays.asList(ITEM.values());
		System.out.println("Default Ordering: " + enumList);
		Collections.sort(enumList, enumOrdering.new ItemComparator());
		System.out.println("Custom Ordering : " + enumList);
		
		System.out.println("\nIs SECOND_ITEM greater than THRID_ITEM? " + (ITEM.SECOND_ITEM.compareTo(ITEM.THRID_ITEM) > 0));
	
	}
	
}



