package com.gmail.yauhenizhukovich.reslivtestapp.repository.impl;

import com.gmail.yauhenizhukovich.reslivtestapp.repository.CityRepository;
import com.gmail.yauhenizhukovich.reslivtestapp.repository.model.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.invoke.MethodHandles;

@Repository
public class CityRepositoryImpl implements CityRepository {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void persist(City city) {
        entityManager.persist(city);
    }

    @Override
    public City getCityById(Long id) {
        return entityManager.find(City.class, id);
    }

    @Override
    public void remove(City city) {
        entityManager.remove(city);
    }

    @Override
    public City getCityByName(String name) {
        String queryString =
                "FROM City e WHERE e.name=:name";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("name", name);
        try {
            Object result = query.getSingleResult();
            return (City) result;
        } catch (NoResultException e) {
            logger.info("The user was searching for a city with name \"" + "\"");
            return null;
        }
    }
}