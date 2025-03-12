package net.edigest.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import net.edigest.journalApp.entity.JournalEntry;
@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry,ObjectId> {
}
