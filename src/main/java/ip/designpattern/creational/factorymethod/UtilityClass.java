package ip.designpattern.creational.factorymethod;

import java.util.List;

public class UtilityClass {
	
public static void display(List<Integer> columLength, List<List<Object>> data){
		
		for(int rowIndex = 0 ; rowIndex < data.size(); rowIndex++){
			List<Object> columnData = data.get(rowIndex);
			for(int columnIndex = 0; columnIndex < columnData.size(); columnIndex++){
				Object valueObj = columnData.get(columnIndex);
				String value = null;
				if(valueObj instanceof Integer){
					value = ((Integer)valueObj).toString();
				}else if(valueObj instanceof String){
					value = ((String)valueObj).toString();
				}else if(valueObj instanceof Long){
					value = ((Long)valueObj).toString();
				}
				int length = columLength.get(columnIndex);
				if(value.length() > length){
					value = value.substring(0, length);
				}else if(value.length() < length){
					StringBuffer valueBuffer = new StringBuffer(value);
					for(int i = value.length() ; i < length; i++){
						valueBuffer.append(" ");
					}
					value = valueBuffer.toString();
				}
				System.out.print(value);
			}
			System.out.println();
		}
	}

}
