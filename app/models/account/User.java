package models.account;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import play.data.validation.Email;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.modules.morphia.Model;
import play.modules.morphia.Model.AutoTimestamp;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Reference;

@SuppressWarnings("serial")
@Entity
@AutoTimestamp
public class User extends Model {
	@Required
	public String userName;

	@Required
	public String password;
	
	@Required
	public String fullName;
	
	@Phone
	public String telNum;
	
	@Email
	public String email;
	
	@Reference
	public Role role;

	public User(String userName, String password, String fullName) {
		this.userName = userName;
		this.password = password;
		this.fullName = fullName;
	}

	public static User connect(String userName, String password) {
		return User.find("byUserNameAndPassword", userName, password).first();
	}
	
	public static User getUser(String userName) {
		return User.find("byUserName", userName).first();
	}
	
	public static List<Map<String, Object>> getUsersByTerm(String userName) {
		Pattern pattern = Pattern.compile("^.*" + userName + ".*$",
				Pattern.CASE_INSENSITIVE);
		List<User> users = User.filter("userName", pattern).fetch(10);
		List<Map<String, Object>> mapUsers = new ArrayList<Map<String, Object>>();
		if (users!=null){
			for (User user: users){
				Map<String, Object> mapUser = new LinkedHashMap<String, Object>();
				mapUser.put("id", user.getId().toString());
				mapUser.put("userName", user.userName);
				mapUser.put("fullName", user.fullName);
				mapUser.put("email", user.email);
				mapUser.put("telNum", user.telNum);
				mapUsers.add(mapUser);
			}
		}
		return mapUsers;
	}
	
	public String toString() {
		return userName;
	}
}
