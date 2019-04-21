package com.phoenixvideos.phoenixapp.models;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.phoenixvideos.phoenixapp.model.User;
import com.phoenixvideos.phoenixapp.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.Entity;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;



public class UserTest {
    @Test
    public void testClassSignatureAnnotations() {
        Assert.assertTrue(User.class.isAnnotationPresent(Entity.class));
    }
    @Test
    public void testCreateJson() throws JsonProcessingException {
        ObjectMapper jsonWriter = new ObjectMapper();
        User user = new User();
        String json = jsonWriter.writeValueAsString(user);
    }
}
