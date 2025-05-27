package net.edigest.journalApp.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import net.edigest.journalApp.entity.User;
@Repository
public interface UserRepository extends MongoRepository<User,ObjectId> {
	  User findByUsername(String username);//query DSL
//	findBy is used to query generation so use it as it is

	//username U must be capital


}
