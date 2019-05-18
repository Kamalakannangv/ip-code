package ip.designpattern.structural.proxy;

import java.util.List;
import java.util.Map;

public interface AddressDirectory {
	
	public List<String> getAllState();
	
	public List<String> getAllDistrict();
	
	public Map<String, List<String>> getAllStateAndDistrict();	

}
