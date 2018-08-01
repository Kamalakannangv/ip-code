package ip.designpattern.structural.composite;

import java.util.Date;
import java.util.List;

public class DisplayUtil {
	
public static String getFormattedString(List<Integer> columLength, List<List<Object>> data){
	
	StringBuffer strBuffer = new StringBuffer();
			
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
				}else if(valueObj instanceof Date){
					value = ((Date)valueObj).toLocaleString();
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
						valueBuffer.append(" ");
					}
					value = valueBuffer.toString();
				}
				strBuffer.append(value);
			}
		}
		return strBuffer.toString();
	}

}
