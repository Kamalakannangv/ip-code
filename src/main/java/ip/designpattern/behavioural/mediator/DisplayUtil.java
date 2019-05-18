package ip.designpattern.behavioural.mediator;

import java.util.Date;
import java.util.List;

public class DisplayUtil {

	public static void display(List<Integer> columLength, List<List<Object>> data){

		for(int rowIndex = 0 ; rowIndex < data.size(); rowIndex++){
			List<Object> columnData = data.get(rowIndex);
			for(int columnIndex = 0; columnIndex < columnData.size(); columnIndex++){
				Object valueObj = columnData.get(columnIndex);
				String value = null;
				boolean rightAlign = false;
				if(valueObj instanceof Integer){
					rightAlign = true;
					value = ((Integer)valueObj).toString();
				}else if(valueObj instanceof String){
					value = ((String)valueObj).toString();
				}else if(valueObj instanceof Long){
					value = ((Long)valueObj).toString();
				}else if(valueObj instanceof Date){
					value = ((Date)valueObj).toLocaleString();
				}else if(valueObj instanceof Double){
					rightAlign = true;
					value = String.format("%.2f", valueObj);
				}
				int length = columLength.get(columnIndex);
				if(null == value){
					value = "";
				}
				if(value.length() > length){
					value = value.substring(0, length);
				}else if(value.length() < length){
					StringBuffer valueBuffer = new StringBuffer(value);
					for(int i = value.length() ; i < length; i++){
						if(rightAlign){
							valueBuffer.insert(0, " ");
						}else{
							valueBuffer.append(" ");
						}
					}
					value = valueBuffer.toString();
				}
				System.out.print(value);
			}
			System.out.println();
		}
	}

}
