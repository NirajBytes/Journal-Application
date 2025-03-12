package net.edigest.journalApp.service;

import net.edigest.journalApp.entity.JournalEntry;
import net.edigest.journalApp.entity.User;
import net.edigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class JournalEntryService {
    private final JournalEntryRepository journalEntryRepository;
    private final UserService userService;

    public JournalEntryService(JournalEntryRepository journalEntryRepository, UserService userService) {
        this.journalEntryRepository = journalEntryRepository;
        this.userService = userService;
    }


    public ResponseEntity<?> getAllJournalEntriesOfUser(String username){
        Optional<User> user = userService.userName(username);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Message","User Not Found"));
        }
        User user1 = user.get();
        List<JournalEntry> journalEntriesOfUser = user1.getJournalEntries();
        if(journalEntriesOfUser != null && !journalEntriesOfUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.FOUND).body(journalEntriesOfUser);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Message","Journal Entries For " +username+ " Is Not Found"));
    }

    @Transactional
    public ResponseEntity<?> saveEntry(JournalEntry journalEntry, String username){
        try {
            Optional<User> user = userService.userName(username);
            if(user.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Message","User For "+username+" Not Found"));
            }
            User user1=user.get();
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user1.getJournalEntries().add(saved);
            userService.saveEntry(user1);
            return ResponseEntity.status(HttpStatus.CREATED).body(journalEntry);
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
//    user.getJournalEntries().add(saved); is correct
//
//    Since getJournalEntries() returns a list, adding the saved journal entry is the correct approach.
//    setJournalEntries(saved); was incorrect
//
//    If setJournalEntries() is supposed to replace the entire list, you need to pass a List<JournalEntry>, not a single object.

//    public ResponseEntity<?> getEntry(){
//        List<JournalEntry> all = journalEntryRepository.findAll();
//        if(all != null && !all.isEmpty()){
//            return ResponseEntity.status(HttpStatus.FOUND).body(all);
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(Map.of("error", "Not Found", "message", "Nothing exists in the database"));
//
//    }

    public ResponseEntity<JournalEntry> findById(ObjectId myId) {
        Optional<JournalEntry> journalEntry = journalEntryRepository.findById(myId);
          return journalEntry
                .map(ResponseEntity::ok)
                .orElseGet(() ->ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> deleteById(ObjectId myJournalId, String username) {
        Optional<User> user = userService.userName(username);
        if(user.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Message","User Not Found"));
        }
        User user1=user.get();
        user1.getJournalEntries().removeIf(x ->x.getId().equals(myJournalId));
        userService.saveEntry(user1);
        journalEntryRepository.deleteById(myJournalId);
        return  ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("Message","Entry Deleted From User & Journal Successfully. "));
    }

    public ResponseEntity<?> updateById(ObjectId myJournalId
            , JournalEntry newEntry) {
        Optional<JournalEntry> foundJournalEntry = journalEntryRepository.findById(myJournalId);
        if(foundJournalEntry.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Message","Journal Entry not Found."));
        }
        JournalEntry oldJournalEntry = foundJournalEntry.get();
        oldJournalEntry.setTitle(newEntry.getTitle());
        oldJournalEntry.setContent(newEntry.getContent());
        journalEntryRepository.save(oldJournalEntry);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    public ResponseEntity<?> getEntriesId(){
//        List<JournalEntry> all = journalEntryRepository.findAll();
//        List<ObjectId> ids=all.stream().map(JournalEntry::getId).toList();
//        if(ids != null && !ids.isEmpty()){
//            return ResponseEntity.status(HttpStatus.FOUND).body(ids);
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(Map.of("error", "Not Found", "message", "Nothing exists in the database"));
//
//    }


}
