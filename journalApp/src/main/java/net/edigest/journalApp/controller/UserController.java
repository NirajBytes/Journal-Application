package net.edigest.journalApp.controller;

import org.springframework.web.bind.annotation.RestController;
import net.edigest.journalApp.entity.User;
import net.edigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	//find all users
	@GetMapping
	public ResponseEntity<Object> findAll() {
		return userService.findAll();
	}

	// add user
	@PostMapping
	public ResponseEntity<?> saveEntry(@RequestBody User user) {
//		return userService.saveEntry(user);
		return userService.saveNewUser(user);
	}

	//update the user
	@PutMapping("/{username}")
	public ResponseEntity<Object> updateByUserName(@RequestBody User user,@PathVariable String username) {
		return userService.updateByUserName(user,username);
	}

	//find by id
	@GetMapping("/id/{myId}")
	public ResponseEntity<Object> getUserEntryById(@PathVariable ObjectId myId) {
		return userService.getUserEntryById(myId);
	}

	// delete by id
	@DeleteMapping("/id/{myId}")
	public ResponseEntity<Object> deleteById(@PathVariable ObjectId myId) {
		return userService.deleteById(myId);
	}

	// delete all
	@DeleteMapping("/delete")
	public ResponseEntity<Object> deleteAllData() {
		return userService.deleteAllData();
	}

	@GetMapping("/username/{userName}")
	public Optional<User> userName(@PathVariable String userName) {
		return userService.userName(userName);
	}




































}
