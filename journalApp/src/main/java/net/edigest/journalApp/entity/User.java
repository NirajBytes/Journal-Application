package net.edigest.journalApp.entity;
import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.mongodb.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Users")
public class User {
	@Id
	private ObjectId id;
	@Indexed(unique = true) // to be the unique username
	@NonNull // not to be null
	private String username;
	@NonNull
	private String password;
	@DBRef
	private List<JournalEntry> journalEntries = new ArrayList<>();

	private List<String> roles = new ArrayList<>();
}
