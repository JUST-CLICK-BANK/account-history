package com.click.accountHistory.domain.repository;

import com.click.accountHistory.domain.mongo.CategoryDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryDocumentRepository extends MongoRepository<CategoryDocument, String> {

}
