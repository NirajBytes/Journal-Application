package net.edigest.journalApp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import net.edigest.journalApp.entity.User;
import net.edigest.journalApp.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	// Constructor injection
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();

	// find all users
	public ResponseEntity<Object> findAll() {
		List<User> list = userRepository.findAll();
		if (list.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Message","User Schema Is Empty"));
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(list);

	}

	// save the user
	public ResponseEntity<?> saveEntry(User user) {
		userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("Message","User Created"));

	}

	public ResponseEntity<?> saveNewUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER"));
		userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("Message","User Created"));

	}

	// update the user
	public ResponseEntity<Object> updateByUserName(User user,String username) {
		User founded = userRepository.findByUsername(username);
		if (founded == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Message","Nothing Exist With this username in DataBase"));
		}
		founded.setUsername(user.getUsername());
		founded.setPassword(user.getPassword());
		userRepository.save(founded);
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("Message", "Username and password updated in the database"));

	}
//	public ResponseEntity<Object> updateByUserName(User user, String username) {
//		return userRepository.findByUsername(username)
//				.map(founded -> {
//					founded.setUsername(user.getUsername());
//					founded.setPassword(user.getPassword());
//					userRepository.save(founded);
//					return ResponseEntity.status(HttpStatus.OK).body((Object) Map.of("Message", "Username and password updated in the database"));
//				})
//				.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
//						.body(Map.of("Message", "Nothing exists with this username in the database")));
//	}
//

	// find by id
	public ResponseEntity<Object> getUserEntryById(ObjectId myId) {
		Optional<User> founded = userRepository.findById(myId);
		if (founded.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Message","Nothing Exist With this ID in DataBase"));
		}
		return ResponseEntity.status(HttpStatus.FOUND).body(founded);
	}


	//delete by id
	public ResponseEntity<Object> deleteById(ObjectId myId) {
		Optional<User> founded = userRepository.findById(myId);
		if (founded.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Message","Nothing Exist With this ID in DataBase"));
		}
		userRepository.deleteById(myId);
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("Message","Deleted"));
	}

	public ResponseEntity<Object> deleteAllData() {
		userRepository.deleteAll();
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("Message","Now Users Schema Is Empty"));
	}

//	public User userName(String userName) {
//		User byUsername = userRepository.findByUsername(userName);
//		if(byUsername ==null){
//			throw new UsernameNotFoundException("User Not OFund With UserName"+userName);
//		}
//		return byUsername;
//	}
public Optional<User> userName(String userName) {
	return  Optional.ofNullable(userRepository.findByUsername(userName));
}

}
