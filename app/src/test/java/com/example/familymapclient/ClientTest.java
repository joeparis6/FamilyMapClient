package com.example.familymapclient;

import com.google.gson.internal.$Gson$Preconditions;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import RequestAndResponse.AllEventsResponse;
import RequestAndResponse.EventResponse;
import RequestAndResponse.LoginRequest;
import RequestAndResponse.LoginResponse;
import RequestAndResponse.PersonResponse;
import RequestAndResponse.RegisterRequest;
import RequestAndResponse.RegisterResponse;
import model.Event;
import model.Person;

import static org.junit.Assert.*;

public class ClientTest {


    String serverHost = "localhost";
    String serverPort = "8080";
    String userName = "JeffT";
    String userName2 = "GardnerH";
    String userName3 = "JakeC";
    String userName4 = "AlecR";
    String userName5 = "DomS";
    String userName6 = "JoeP";
    String userName7 = "IanJ";
    String userName8 = "HarryJ";
    String userName9 = "DanielS";
    String userName10 = "VictorH";
    String userName11 = "KatieP";
    String password = "portland";
    String firstName = "Jeffrey";
    String lastName = "Thompson";
    String email = "jeff123@gmail.com";
    String email2 = "gardnerh@gmail.com";
    String email3 = "jake123@gmail.com";
    String email4 = "Alec123@gmail.com";
    String email5 = "Dom@gmail.com";
    String gender = "m";
    String incorrectPassword = "seattle";
    RegisterRequest registerRequest1;
    RegisterRequest registerRequest2;
    RegisterRequest registerRequest3;
    RegisterRequest registerRequest4;
    RegisterRequest registerRequest5;
    RegisterRequest registerRequest6;
    RegisterRequest registerRequest7;
    RegisterRequest registerRequest8;
    RegisterRequest registerRequest9;
    RegisterRequest registerRequest10;
    RegisterRequest registerRequest11;
    String badToken = "falseAuthToken";
    Event fakeEvent = new Event("12345", "scooby", "123456", 100.0f,
            100.0f, "Kmart", "aisle 5", "Surfing trip", 1965);
    Person fakePerson = new Person("123456", "DwightK", "Dwight", "Schrute",
            "m", "abcde", "fghij", "klmno");

    @Before
    public void setUp(){
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        registerRequest1 = new RegisterRequest();
        registerRequest1.setUserName(userName);
        registerRequest1.setPassword(password);
        registerRequest1.setFirstName(firstName);
        registerRequest1.setLastName(lastName);
        registerRequest1.setEmail(email);
        registerRequest1.setGender(gender);

        registerRequest2 = new RegisterRequest();
        registerRequest2.setUserName(userName2);
        registerRequest2.setPassword(password);
        registerRequest2.setFirstName(firstName);
        registerRequest2.setLastName(lastName);
        registerRequest2.setPassword(password);
        registerRequest2.setEmail(email2);
        registerRequest2.setGender(gender);

        registerRequest3 = new RegisterRequest();
        registerRequest3.setUserName(userName3);
        registerRequest3.setPassword(password);
        registerRequest3.setFirstName(firstName);
        registerRequest3.setLastName(lastName);
        registerRequest3.setPassword(password);
        registerRequest3.setEmail(email3);
        registerRequest3.setGender(gender);

        registerRequest4 = new RegisterRequest();
        registerRequest4.setUserName(userName4);
        registerRequest4.setPassword(password);
        registerRequest4.setFirstName(firstName);
        registerRequest4.setLastName(lastName);
        registerRequest4.setPassword(password);
        registerRequest4.setEmail(email4);
        registerRequest4.setGender(gender);

        registerRequest5 = new RegisterRequest();
        registerRequest5.setUserName(userName5);
        registerRequest5.setPassword(password);
        registerRequest5.setFirstName(firstName);
        registerRequest5.setLastName(lastName);
        registerRequest5.setPassword(password);
        registerRequest5.setEmail(email5);
        registerRequest5.setGender(gender);

        registerRequest6 = new RegisterRequest();
        registerRequest6.setUserName(userName6);
        registerRequest6.setPassword(password);
        registerRequest6.setFirstName(firstName);
        registerRequest6.setLastName(lastName);
        registerRequest6.setPassword(password);
        registerRequest6.setEmail(email5);
        registerRequest6.setGender(gender);

        registerRequest7 = new RegisterRequest();
        registerRequest7.setUserName(userName7);
        registerRequest7.setPassword(password);
        registerRequest7.setFirstName(firstName);
        registerRequest7.setLastName(lastName);
        registerRequest7.setPassword(password);
        registerRequest7.setEmail(email5);
        registerRequest7.setGender(gender);

        registerRequest8 = new RegisterRequest();
        registerRequest8.setUserName(userName8);
        registerRequest8.setPassword(password);
        registerRequest8.setFirstName(firstName);
        registerRequest8.setLastName(lastName);
        registerRequest8.setPassword(password);
        registerRequest8.setEmail(email5);
        registerRequest8.setGender(gender);

        registerRequest9 = new RegisterRequest();
        registerRequest9.setUserName(userName9);
        registerRequest9.setPassword(password);
        registerRequest9.setFirstName(firstName);
        registerRequest9.setLastName(lastName);
        registerRequest9.setPassword(password);
        registerRequest9.setEmail(email5);
        registerRequest9.setGender(gender);

        registerRequest10 = new RegisterRequest();
        registerRequest10.setUserName(userName10);
        registerRequest10.setPassword(password);
        registerRequest10.setFirstName(firstName);
        registerRequest10.setLastName(lastName);
        registerRequest10.setPassword(password);
        registerRequest10.setEmail(email5);
        registerRequest10.setGender(gender);

        registerRequest11 = new RegisterRequest();
        registerRequest11.setUserName(userName10);
        registerRequest11.setPassword(password);
        registerRequest11.setFirstName(firstName);
        registerRequest11.setLastName(lastName);
        registerRequest11.setPassword(password);
        registerRequest11.setEmail(email5);
        registerRequest11.setGender(gender);
    }

    @Test
    public void register() {

        ServerProxy proxy = new ServerProxy();
        RegisterResponse response = proxy.register(registerRequest1);
        assertNotNull(response.getAuthToken());
        assertEquals(userName, response.getUserName());


    }

    @Test
    public void registerFail() {



        ServerProxy proxy = new ServerProxy();
        RegisterResponse response1 = proxy.register(registerRequest2);
        RegisterResponse response2 = proxy.register(registerRequest2);
        assertNull(response2.getAuthToken());
        assertNull(response2.getUserName());
    }

    @Test
    public void login() {
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        LoginRequest request = new LoginRequest();
        request.setUserName(userName);
        request.setPassword(password);

        ServerProxy proxy = new ServerProxy();
        LoginResponse response = proxy.login(request);
        assertNotNull(response.getAuthToken());
        assertNotNull(response.getUserName());
        assertNotNull(response.getPersonID());
    }

    @Test
    public void badLogin() {
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        LoginRequest request = new LoginRequest();
        request.setUserName(userName);
        request.setPassword(incorrectPassword);

        ServerProxy proxy = new ServerProxy();
        LoginResponse response = proxy.login(request);
        assertNull(response.getAuthToken());
        assertNull(response.getUserName());
        assertNull(response.getPersonID());
    }

    @Test
    public void retrievePeople() {
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        LoginRequest request = new LoginRequest();
        request.setUserName(userName);
        request.setPassword(password);

        ServerProxy proxy = new ServerProxy();
        LoginResponse response = proxy.login(request);
        String authToken = response.getAuthToken();
        PersonResponse personResponse = proxy.getPeople(authToken);
        assertNotNull(personResponse.getPersons());
    }

    @Test
    public void retrievePeopleFail() {
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        LoginRequest request = new LoginRequest();
        request.setUserName(userName);
        request.setPassword(password);

        ServerProxy proxy = new ServerProxy();
        LoginResponse response = proxy.login(request);
        String authToken = response.getAuthToken();
        PersonResponse personResponse = proxy.getPeople(badToken);
        assertNull(personResponse.getPersons());
    }

    @Test
    public void retrieveEvents() {
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        LoginRequest request = new LoginRequest();
        request.setUserName(userName);
        request.setPassword(password);

        ServerProxy proxy = new ServerProxy();
        LoginResponse response = proxy.login(request);
        String authToken = response.getAuthToken();
        AllEventsResponse eventResponse = proxy.getEvents(authToken);
        assertNotNull(eventResponse.getEvents());
    }

    @Test
    public void retrieveEventsFail() {
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        LoginRequest request = new LoginRequest();
        request.setUserName(userName);
        request.setPassword(password);

        ServerProxy proxy = new ServerProxy();
        LoginResponse response = proxy.login(request);
        String authToken = response.getAuthToken();
        AllEventsResponse eventResponse = proxy.getEvents(badToken);
        assertNull(eventResponse.getEvents());
    }

    @Test
    public void familyRelationships() {
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        LoginRequest request = new LoginRequest();
        request.setUserName(userName);
        request.setPassword(password);

        ServerProxy proxy = new ServerProxy();
        LoginResponse response = proxy.login(request);
        String authToken = response.getAuthToken();
        cache.setCurrentPersonID(response.getPersonID());
        PersonResponse personResponse = proxy.getPeople(authToken);
        AllEventsResponse eventsResponse = proxy.getEvents(authToken);
        cache.setAllEvents(eventsResponse.getEvents());
        cache.setAllPeople(personResponse.getPersons());
        cache.partitionData();

        Person Jeffrey = cache.getRootUser();
        assertTrue(Jeffrey.getFirstName().equals(firstName));
        Map<String, Person> personMap = cache.getPersonMap();
        Person Dad = personMap.get(Jeffrey.getFatherID());
        Person Mom = personMap.get(Jeffrey.getMotherID());
        Person Grandpa = personMap.get(Dad.getFatherID());
        Person Grandma = personMap.get(Dad.getMotherID());

        ArrayList<Person> immediateFamily = cache.getImmediateFamily(Dad);
        assertTrue(immediateFamily.size() == 4);
        Map<Person, String> relationMap = cache.getFamilyRelationMap();
        assertTrue(relationMap.get(Jeffrey).equals("CHILD"));
        assertTrue(relationMap.get(Mom).equals("SPOUSE"));
        assertTrue(relationMap.get(Grandpa).equals("FATHER"));
        assertTrue(relationMap.get(Grandma).equals("MOTHER"));
    }

    @Test
    public void familyRelationshipsAlternative() {
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        ServerProxy proxy = new ServerProxy();
        RegisterResponse response = proxy.register(registerRequest3);
        String authToken = response.getAuthToken();
        cache.setCurrentPersonID(response.getPersonID());
        PersonResponse personResponse = proxy.getPeople(authToken);
        AllEventsResponse eventsResponse = proxy.getEvents(authToken);
        cache.setAllEvents(eventsResponse.getEvents());
        cache.setAllPeople(personResponse.getPersons());
        cache.partitionData();

        Person Jeffrey = cache.getRootUser();
        assertTrue(Jeffrey.getFirstName().equals(firstName));
        Map<String, Person> personMap = cache.getPersonMap();
        Person Dad = personMap.get(Jeffrey.getFatherID());
        Person Mom = personMap.get(Jeffrey.getMotherID());


        ArrayList<Person> immediateFamily = cache.getImmediateFamily(Jeffrey);
        assertTrue(immediateFamily.size() == 2);
        Map<Person, String> relationMap = cache.getFamilyRelationMap();
        assertTrue(relationMap.get(Dad).equals("FATHER"));
        assertTrue(relationMap.get(Mom).equals("MOTHER"));
    }

    @Test
    public void filterFatherSideEvents() {
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        ServerProxy proxy = new ServerProxy();
        RegisterResponse response = proxy.register(registerRequest4);
        String authToken = response.getAuthToken();
        cache.setCurrentPersonID(response.getPersonID());
        PersonResponse personResponse = proxy.getPeople(authToken);
        AllEventsResponse eventsResponse = proxy.getEvents(authToken);
        cache.setAllEvents(eventsResponse.getEvents());
        cache.setAllPeople(personResponse.getPersons());
        cache.partitionData();

        cache.filterFatherSideEvents();
        ArrayList<Event> filteredEvents = cache.getAllEvents();
        Set<Person> fatherSidePeople = cache.getFatherSidePeople();
        for (Event event : filteredEvents) {
            Person associatedPerson =cache.getPersonMap().get(event.getPersonID());
            assertFalse(fatherSidePeople.contains(associatedPerson));
        }
    }

    @Test
    public void filterMotherSideEvents() {
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        ServerProxy proxy = new ServerProxy();
        RegisterResponse response = proxy.register(registerRequest5);
        String authToken = response.getAuthToken();
        cache.setCurrentPersonID(response.getPersonID());
        PersonResponse personResponse = proxy.getPeople(authToken);
        AllEventsResponse eventsResponse = proxy.getEvents(authToken);
        cache.setAllEvents(eventsResponse.getEvents());
        cache.setAllPeople(personResponse.getPersons());
        cache.partitionData();

        cache.filterMotherSideEvents();
        ArrayList<Event> filteredEvents = cache.getAllEvents();
        Set<Person> motherSidePeople = cache.getMotherSidePeople();
        for (Event event : filteredEvents) {
            Person associatedPerson =cache.getPersonMap().get(event.getPersonID());
            assertFalse(motherSidePeople.contains(associatedPerson));
        }
    }

    @Test
    public void filterMaleEvents() {
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        ServerProxy proxy = new ServerProxy();
        RegisterResponse response = proxy.register(registerRequest9);
        String authToken = response.getAuthToken();
        cache.setCurrentPersonID(response.getPersonID());
        PersonResponse personResponse = proxy.getPeople(authToken);
        AllEventsResponse eventsResponse = proxy.getEvents(authToken);
        cache.setAllEvents(eventsResponse.getEvents());
        cache.setAllPeople(personResponse.getPersons());
        cache.partitionData();

        cache.filterMaleEvents();
        ArrayList<Event> filteredEvents = cache.getAllEvents();
        Set<Person> males = cache.getAllMales();
        for (Event event : filteredEvents) {
            Person associatedPerson =cache.getPersonMap().get(event.getPersonID());
            assertFalse(males.contains(associatedPerson));
        }
    }

    @Test
    public void filterFemaleEvents() {
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        ServerProxy proxy = new ServerProxy();
        RegisterResponse response = proxy.register(registerRequest10);
        String authToken = response.getAuthToken();
        cache.setCurrentPersonID(response.getPersonID());
        PersonResponse personResponse = proxy.getPeople(authToken);
        AllEventsResponse eventsResponse = proxy.getEvents(authToken);
        cache.setAllEvents(eventsResponse.getEvents());
        cache.setAllPeople(personResponse.getPersons());
        cache.partitionData();

        cache.filterFemaleEvents();
        ArrayList<Event> filteredEvents = cache.getAllEvents();
        Set<Person> females = cache.getAllFemales();
        for (Event event : filteredEvents) {
            Person associatedPerson =cache.getPersonMap().get(event.getPersonID());
            assertFalse(females.contains(associatedPerson));
        }
    }

    @Test
    public void chronologicalEventTest() {
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        ServerProxy proxy = new ServerProxy();
        RegisterResponse response = proxy.register(registerRequest6);
        String authToken = response.getAuthToken();
        cache.setCurrentPersonID(response.getPersonID());
        PersonResponse personResponse = proxy.getPeople(authToken);
        AllEventsResponse eventsResponse = proxy.getEvents(authToken);
        cache.setAllEvents(eventsResponse.getEvents());
        cache.setAllPeople(personResponse.getPersons());
        cache.partitionData();

        for (Person person : cache.getAllPeople()) {
            cache.clearPersonData();
            cache.setSelectedPerson(person);
            cache.setPersonalEvents(person);
            ArrayList<Event> lifeStoryEvents = cache.getPersonalEvents();

            for (int i = 0; i < lifeStoryEvents.size() - 1; i++) {
                Event firstEvent = lifeStoryEvents.get(i);
                Event nextEvent = lifeStoryEvents.get(i + 1);
                assertTrue(firstEvent.getYear() <= nextEvent.getYear());
            }

        }
    }

    @Test
    public void searchTest() {
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        ServerProxy proxy = new ServerProxy();
        RegisterResponse response = proxy.register(registerRequest7);
        String authToken = response.getAuthToken();
        cache.setCurrentPersonID(response.getPersonID());
        PersonResponse personResponse = proxy.getPeople(authToken);
        AllEventsResponse eventsResponse = proxy.getEvents(authToken);
        cache.setAllEvents(eventsResponse.getEvents());
        cache.setAllPeople(personResponse.getPersons());
        cache.partitionData();

        assertTrue(cache.searchEvent("birth"));
        assertTrue(cache.searchEvent("BiRtH"));
        assertTrue(cache.searchEvent("marriage"));
        assertTrue(cache.searchEvent("MARRIAGE"));
        assertTrue(cache.searchEvent("death"));
        assertTrue(cache.searchEvent("DEAth"));
        assertTrue(cache.searchPerson("Jeffrey"));
        assertTrue(cache.searchPerson("Jeffrey Thompson"));
        assertTrue(cache.searchPerson("jeff"));
        assertTrue(cache.searchPerson("THOMPSON"));



    }

    @Test
    public void searchfakeInfoTest() {
        DataCache cache = DataCache.getInstance();
        cache.setServerHost(serverHost);
        cache.setServerPort(serverPort);
        ServerProxy proxy = new ServerProxy();
        RegisterResponse response = proxy.register(registerRequest8);
        String authToken = response.getAuthToken();
        cache.setCurrentPersonID(response.getPersonID());
        PersonResponse personResponse = proxy.getPeople(authToken);
        AllEventsResponse eventsResponse = proxy.getEvents(authToken);
        cache.setAllEvents(eventsResponse.getEvents());
        cache.setAllPeople(personResponse.getPersons());
        cache.partitionData();

        assertFalse(cache.searchEvent(fakeEvent.getEventType()));
        assertFalse(cache.searchEvent(fakeEvent.getCity()));
        assertFalse(cache.searchEvent(fakeEvent.getCountry()));
        assertFalse(cache.searchPerson(fakePerson.getFirstName()));
        assertFalse(cache.searchPerson(fakePerson.getLastName()));

    }


}
