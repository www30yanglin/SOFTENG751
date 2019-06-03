package com.threadQueue;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Logger;

/**
 * 构建user对象
 * */
public class UserData {

    private final Logger logger = Logger.getLogger(String.valueOf(UserData.class));

	/***
	 * 录入批量信息
	 */
	public PriorityBlockingQueue<UserModel> getAllPersons(){

        logger.info("start listPersons()");
        PriorityBlockingQueue<UserModel> persons = new PriorityBlockingQueue<>();

        persons.put(new UserModel("Paul",    23));
        persons.put(new UserModel("John",    35));
        persons.put(new UserModel("Michel",  37));
        persons.put(new UserModel("Lee",     19));
        persons.put(new UserModel("Alberto", 45));
        persons.put(new UserModel("Sergio",  29));
        persons.put(new UserModel("Raul",    34));

        logger.info("exit listPersons()");
        return persons;

    }
}
