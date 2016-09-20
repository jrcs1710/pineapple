package br.senai.sp.cfp132.PineappleSystems.Dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Factory {

	private static EntityManager manager;
	private static EntityManagerFactory factory;
	
	static {
		factory = Persistence
				.createEntityManagerFactory("grupo1");
	}

	public static EntityManager getManager() {
		

		if (manager == null) {
			 
			manager = factory.createEntityManager();
		}

		return manager;
	}
	
	public static void close(){
		manager.close();
		factory.close();
	}

}
