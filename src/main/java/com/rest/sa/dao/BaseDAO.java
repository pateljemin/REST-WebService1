package com.rest.sa.dao;

import org.springframework.data.mongodb.core.MongoOperations;

public class BaseDAO {
	MongoOperations mongoOperations;

	public MongoOperations getMongoOperations() {
		return mongoOperations;
	}

	public void setMongoOperations(MongoOperations mongoOperations) {
		this.mongoOperations = mongoOperations;
	}

	public void saveCollection(Object data) {
		mongoOperations.save(data);
	}
}
