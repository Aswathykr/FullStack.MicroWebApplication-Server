package com.phoenixvideos.phoenixapp.service;
import com.phoenixvideos.phoenixapp.model.User;
import com.phoenixvideos.phoenixapp.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//import org.junit.jupiter.api.extension.ExtendWith;

//@ExtendWith(SpringExtension.class)
@SpringBootTest

public class UserServiceTest {
    private UserService service;
    private UserRepository repository;

    @Before
    public void before() {
        repository = mock(UserRepository.class);
        service = new UserService(repository);
    }

    @Test
    public void createTest()  {
        User user = new User();
        long idGiven = 1L;
        user.setId(1L);
//
        when(repository.save(user)).thenReturn(user);
        when(repository.findById(idGiven)).thenReturn(Optional.of(user));
        User actual = service.create(user);
        long actualId = actual.getId();
        User actual2 = service.show(idGiven);
        long actualId2 = actual2.getId();

       Assert.assertEquals(actualId,idGiven);

       Assert.assertEquals(actualId2,idGiven);
    }

    @Test
    public void deleteTest()  {
        User user = new User();
        long idGiven = 1L;
        user.setId(1L);

        when(repository.save(user)).thenReturn(user);
//
        //Test User's been created
        when(repository.findById(idGiven)).thenReturn(Optional.of(user));
        long expected = service.show(idGiven).getId();
        Assert.assertEquals(expected,idGiven);


        //Test User has been deleted
        when(repository.findById(idGiven)).thenReturn(Optional.empty());

        boolean actual = service.delete(idGiven);
        Assert.assertTrue(actual);

    }

    @Test(expected = NoSuchElementException.class)
    public void deleteExceptionTest(){
        User user = new User();
        long idGiven = 1L;
        user.setId(1L);
        when(repository.save(user)).thenReturn(user);

        when(repository.findById(idGiven)).thenReturn(Optional.empty());
        service.delete(idGiven);

        long expected2 = service.show(idGiven).getId();
        Assert.assertEquals(expected2,idGiven);
    }

    @Test
    public void UpdateTest()  {
        //CreateUser
        User user = new User();
        long idGiven = 1L;
        user.setId(idGiven);
        String lastName = "Smith";
        user.setLastName(lastName);
        //When
        when(repository.findById(idGiven)).thenReturn(Optional.of(user));
        long expected = service.show(idGiven).getId();
        String lastNameSaved = service.show(idGiven).getLastName();
        //Test user was created
        Assert.assertEquals(expected,idGiven);
        Assert.assertEquals(lastNameSaved,lastName);

        //Update user
        User newUser = new User();
        newUser.setLastName("Lovato");
        User actual = service.update(idGiven,newUser);
        long idKept = actual.getId();


        Assert.assertEquals(idKept,idGiven);
        Assert.assertNotEquals(lastName,actual.getLastName());
    }
    @Test
    public void indexTest(){
        User user = new User();
        User user2 = new User();
        user.setId(1L);
        user2.setId(2L);
        List<User> iterable = new ArrayList<>();
        iterable.add(user);
        iterable.add(user2);

        when(repository.findAll()).thenReturn(iterable);
        when(repository.findById(2L)).thenReturn(Optional.of(user2));

        Iterable<User> actual = service.index();
        User expected = service.show(2L);
        Assert.assertEquals(actual,iterable);

        Assert.assertEquals(expected.getId(),user2.getId());


    }
//
//    @Before
//    public void SetUp() {
//    service = new UserService(repository);


//
//    @Test
//    public void testCreate1(){
//        User user = new User();
//        user.setUserName("Marlys");
//
//        when(repository.save(any(User.class))).thenReturn(new User());
//
//        User created = service.create(user);
//
//        Assert.assertEquals(user.getFirstName(),created.getFirstName());
//
//    }
//    @MockBean
//    private UserController controller;
//    @MockBean
//    private AmazonS3ClientService amazonS3ClientService;
//    @MockBean
//    private VideoService videoService;
//    @MockBean
//    private VideoRepository videoRepository;

//    @Before
//    public void setup() {
//        this.service = new UserService(repository);
//        this.controller = new UserController(service);
//        this.videoService = new VideoService(videoRepository, repository, amazonS3ClientService);
//    }

    //When the method you are testing return something
    @Test
    public void testCreate() {
        //Given
        User user = new User();
        // 1. create mock
        UserRepository mockRepo = mock(UserRepository.class);
        // 2. what do you want the mock to do
        // here we're setting up the data to return
        long expectedId = 1;
        User userToReturn = new User();
        userToReturn.setId(expectedId);

        //then we say when the method save get called with the
        //person then return the person we set up above
        when(mockRepo.save(user)).thenReturn(userToReturn);
        // 3. create the class you're testing
        UserService service = new UserService(mockRepo);
        // 4. call the method under test
        User actual = service.create(user);
        long actualId = actual.getId();

//         5. verify the result
//         Because we said on step two that when our mock
//         get call with save, then it will return a person
//         with id 14.
        Assert.assertEquals(expectedId, actualId);
    }

}

