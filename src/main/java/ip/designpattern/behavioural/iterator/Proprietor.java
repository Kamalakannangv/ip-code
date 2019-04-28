package ip.designpattern.behavioural.iterator;

import java.util.List;

public class Proprietor implements ComponentAddress {
	
	private String proprietorName;
	private ComponentAddress parentCompositeAddress;

	public Proprietor(String proprietorName){
		this.proprietorName = proprietorName;
	}

	@Override
	public AddressComponentEnum getAddressComponent() {
		return AddressComponentEnum.PROPRIETOR;
	}

	@Override
	public String getAddressComponentValue() {
		return proprietorName;
	}
	
	@Override
	public ComponentAddress getParentComponentAddress() {
		return parentCompositeAddress;
	}	

	@Override
	public void setParentComponentAddress(ComponentAddress parentCompositeAddress) {
		this.parentCompositeAddress = parentCompositeAddress;
		parentCompositeAddress.getChildAddressComponents().add(this);
	}

	@Override
	public String getAddress() {
		StringBuffer address = new StringBuffer(AddressComponentEnum.PROPRIETOR.getValue() + ": " + proprietorName);
		if(getParentComponentAddress() != null){
			address.append(", " +getParentComponentAddress().getAddress());
		}
		return address.toString() ;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Proprietor){
			Proprietor proprietor = (Proprietor)obj;
			if(proprietor.proprietorName.equals(proprietorName)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return AddressComponentEnum.PROPRIETOR.getValue() + " : " + proprietorName;
	}

	@Override
	public List<ComponentAddress> getChildAddressComponents() {
		return null;
	}

	@Override
	public ComponentAddress getLeftLeafComponentAddress() {
		return this;
	}

	@Override
	public ComponentAddress getRigthSibling() {
		List<ComponentAddress> siblings = getParentComponentAddress().getChildAddressComponents();
		int siblingIndex = getRightSiblingIndex();
		if(siblings.size() > siblingIndex){
			return siblings.get(siblingIndex);
		}else{
			return null;
		}
	}

	@Override
	public boolean hasRightSibling() {
		List<ComponentAddress> siblings = getParentComponentAddress().getChildAddressComponents();
		int siblingIndex = getRightSiblingIndex();
		return siblings.size() > siblingIndex ;
	}
	
	private int getRightSiblingIndex(){
		List<ComponentAddress> siblings = getParentComponentAddress().getChildAddressComponents();
		int siblingIndex;
		for(siblingIndex = 0; siblingIndex < siblings.size(); siblingIndex++){
			if(this == siblings.get(siblingIndex)){
				break;
			}
		}
		return ++siblingIndex;
	}
	

}
