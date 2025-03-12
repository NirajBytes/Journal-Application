package net.edigest.journalApp.controller;

import net.edigest.journalApp.entity.JournalEntry;
import net.edigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {


    private final JournalEntryService journalEntryService;
    public JournalEntryController(JournalEntryService journalEntryService) {
        this.journalEntryService = journalEntryService;
    }

    //to fetch the journal entries of particular user

    @GetMapping("/{username}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String username){
       return journalEntryService.getAllJournalEntriesOfUser(username);
    }


    //to create the Journal entry for particular user
    @PostMapping("/{username}")
    public ResponseEntity<?> saveEntry(@RequestBody JournalEntry journalEntry,@PathVariable String username){
           return journalEntryService.saveEntry(journalEntry,username);
    }

    //find journal entry by id
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> findById(@PathVariable ObjectId myId){
        return journalEntryService.findById(myId);
    }

    //delete by the journal entry of user
    @DeleteMapping("/id/{username}/{myJournalId}")
    public ResponseEntity<?> deleteById(@PathVariable String username,@PathVariable ObjectId myJournalId){
         return journalEntryService.deleteById(myJournalId,username);
    }

    //update by id
    @PutMapping("/id/{myJournalId}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId myJournalId
            ,@RequestBody JournalEntry newEntry){

        return journalEntryService.updateById(myJournalId,newEntry);
    }
//
//    @GetMapping("/ids")
//    public ResponseEntity<?> getEntriesId(){
//        return journalEntryService.getEntriesId();
//
//    }
}
