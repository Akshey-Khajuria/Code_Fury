package com.voltx.codefury.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.voltx.codefury.entity.ProblemCounter;
import com.voltx.codefury.service.SequenceGeneratorService;

@Service
public class ProblemSequenceGeneratorService implements SequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public Long getNextSequence(String sequenceName) {

        Query query = new Query(Criteria.where("_id").is(sequenceName));
        Update update = new Update().inc("seq", 1);

        FindAndModifyOptions options = new FindAndModifyOptions()
                .returnNew(true)
                .upsert(true);

        ProblemCounter counter = mongoOperations.findAndModify(
                query,
                update,
                options,
                ProblemCounter.class
        );

        return counter.getSeq();
    }
}
