package ip.security.authentication.srp;

public class UserClient {

	SRPClient srpClient = new SRPClient();

	public static void main(String[] args) {
		UserClient user = new UserClient();
		user.register("gkamalakanna@csc.com", "ABcd56&*");
		user.authenticate("gkamalakanna@csc.com", "ABcd56&*");
		//user.authenticate("Kamal", "KitchuMidh");
	}

	public void register(String username, String password){
		srpClient.registerUser(username, password);
	}

	public void authenticate(String username, String password){
		srpClient.authenticate(username, password);
	}

}
