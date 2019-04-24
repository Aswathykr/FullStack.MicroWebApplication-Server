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

    @Test
    public void getIdTest(){
        User user = new User("Marlys",null,null,null,null);
        Long expected = 1L;
        user.setId(expected);

        Long actual =user.getId();

        Assert.assertEquals(expected,actual);
    }

    @Test
    public void getFistNameTest(){
        String expected = "Marlys";
        User user = new User(expected,null,null,null,null);

        String actual =user.getFirstName();

        Assert.assertEquals(expected,actual);
    }
    @Test
    public void getLastNameTest(){
        String expected = "Alvarado";
        User user = new User(expected,expected,null,null,null);

        String actual =user.getLastName();

        Assert.assertEquals(expected,actual);
    }
    @Test
    public void getUserNameTest(){
        String expected = "userName";
        User user = new User(expected,expected,null,expected,null);

        String actual =user.getUserName();

        Assert.assertEquals(expected,actual);
    }
    @Test
    public void getUserPasswordTest(){

        String expected = "password";
        User user = new User(expected,expected,null,null,expected);

        String actual =user.getPassword();

        Assert.assertEquals(expected,actual);
    }

}
