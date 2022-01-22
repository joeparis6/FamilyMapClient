package com.example.familymapclient;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import model.AuthToken;
import model.Event;
import model.Person;
import model.User;

public class DataCache {

    private static DataCache instance;

    private String authToken;
    private String serverHost;
    private String serverPort;
    private String displayName;
    private String currentPersonID;
    private Person rootUser;
    private final Set<Person> immediateFamilyMales = new HashSet<>();
    private final Set<Person> immediateFamilyFemales = new HashSet<>();
    private final Set<Person> fatherSideMales = new HashSet<>();
    private final Set<Person> fatherSideFemales = new HashSet<>();
    private final Set<Person> motherSideMales = new HashSet<>();
    private final Set<Person> motherSideFemales = new HashSet<>();
    private final Set<Person> allMales = new HashSet<>();
    private final Set<Person> allFemales = new HashSet<>();
    private final Map<String, Person> personMap = new HashMap<>();
    private final Map<String, Event> eventMap = new HashMap<>();
    private final ArrayList<Person> immediateFamily = new ArrayList<>();
    private ArrayList<Event> personalEvents = new ArrayList<>();
    private ArrayList<Person> allPeople;
    private ArrayList<Event> allEvents;
    private ArrayList<Event> allEventsCopy;
    private Marker selectedMarker;
    private ArrayList<Marker> allMarkers;
    private boolean spouseLines;
    private boolean familyTreeLines;
    private boolean lifeStoryLines;
    private boolean filter;
    private boolean fatherSide;
    private boolean motherSide;
    private boolean male;
    private boolean female;
    private boolean newEvent;
    private Event resumeEvent;

    public Person getRootUser() {
        return rootUser;
    }

    public void setRootUser(Person rootUser) {
        this.rootUser = rootUser;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    private Boolean loggedIn;

    public Person getSelectedPerson() {
        return selectedPerson;
    }

    private Person selectedPerson;

    private final Map<Event, Person> eventPersonMap = new HashMap<>();

    public Map<Person, String> getFamilyRelationMap() {
        return familyRelationMap;
    }

    private final Map<Person, String> familyRelationMap = new HashMap<>();

    public ArrayList<Person> getImmediateFam() {
        return immediateFamily;
    }

    public Set<Person> getFatherSideMales() {
        return fatherSideMales;
    }

    public Set<Person> getFatherSideFemales() {
        return fatherSideFemales;
    }

    public Set<Person> getMotherSideMales() {
        return motherSideMales;
    }

    public Set<Person> getMotherSideFemales() {
        return motherSideFemales;
    }

    public Set<Person> getAllMales() {
        return allMales;
    }

    public Set<Person> getAllFemales() {
        return allFemales;
    }

    public Event getFocusEvent() {
        return focusEvent;
    }

    public void setFocusEvent(Event focusEvent) {
        this.focusEvent = focusEvent;
    }

    private Event focusEvent;

    public Event getResumeEvent() {
        return resumeEvent;
    }

    public void setResumeEvent(Event resumeEvent) {
        this.resumeEvent = resumeEvent;
    }

    public boolean isNewEvent() {
        return newEvent;
    }

    public void setNewEvent(boolean newEvent) {
        this.newEvent = newEvent;
    }

    public ArrayList<Event> getAllEventsCopy() {
        return allEventsCopy;
    }

    public void setAllEventsCopy(ArrayList<Event> allEventsCopy) {
        this.allEventsCopy = allEventsCopy;
    }

    public boolean isFatherSide() {
        return fatherSide;
    }

    public void setFatherSide(boolean fatherSide) {
        this.fatherSide = fatherSide;
    }

    public boolean isMotherSide() {
        return motherSide;
    }

    public void setMotherSide(boolean motherSide) {
        this.motherSide = motherSide;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public boolean isFemale() {
        return female;
    }

    public void setFemale(boolean female) {
        this.female = female;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public void setAllMarkers(ArrayList<Marker> markers) {
        this.allMarkers = markers;
    }

    public ArrayList<Marker> getAllMarkers() {
        return allMarkers;
    }

    public boolean isSpouseLines() {
        return spouseLines;
    }

    public void setSpouseLines(boolean spouseLines) {
        this.spouseLines = spouseLines;
    }

    public boolean isFamilyTreeLines() {
        return familyTreeLines;
    }

    public void setFamilyTreeLines(boolean familyTreeLines) {
        this.familyTreeLines = familyTreeLines;
    }

    public boolean isLifeStoryLines() {
        return lifeStoryLines;
    }

    public void setLifeStoryLines(boolean lifeStoryLines) {
        this.lifeStoryLines = lifeStoryLines;
    }


    public Marker getSelectedMarker() {
        return selectedMarker;
    }

    public void setSelectedMarker(Marker selectedMarker) {
        this.selectedMarker = selectedMarker;
    }

    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    private final Set<String> eventTypes = new HashSet<>();

    public Map<Event, Person> getEventPersonMap() {
        return eventPersonMap;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public ArrayList<Person> getAllPeople() {
        return allPeople;
    }

    public void setAllPeople(ArrayList<Person> allPeople) {
        this.allPeople = allPeople;
    }

    public ArrayList<Event> getAllEvents() {
        return allEvents;
    }

    public void setAllEvents(ArrayList<Event> allEvents) {
        this.allEvents = allEvents;
    }

    public void setAuthToken(String authToken) { this.authToken = authToken; }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getServerHost() {
        return serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }


    public Map<String, Person> getPersonMap() {
        return personMap;
    }

    public static DataCache getInstance() {
        if (instance == null) {
            instance = new DataCache();
        }

        return instance;
    }

    private DataCache() {

    }

    public void setCurrentPersonID(String currentPersonID) {
        this.currentPersonID = currentPersonID;
    }

    public void setName() {

        for (int i = 0; i <allPeople.size(); i++) {
            if (allPeople.get(i).getPersonID().equals(currentPersonID)) {
                displayName = allPeople.get(i).getFirstName() + " " + allPeople.get(i).getLastName();
            }
        }

    }

    public void partitionData() {
        partitionPeople();
        partitionEvents();
    }

    public void partitionPeople() {
        for (Person p : allPeople) {
            personMap.put(p.getPersonID(), p);
        }
        Person person = personMap.get(currentPersonID);
        setRootUser(person);
        if (person.getFatherID() != null) {
            fatherSideMales.add(personMap.get(person.getFatherID()));
            allMales.add(personMap.get((person.getFatherID())));
            sortFatherSide(personMap.get(person.getFatherID()));
        }
        if (person.getMotherID() != null) {
            motherSideFemales.add(personMap.get(person.getMotherID()));
            allFemales.add(personMap.get((person.getMotherID())));
            sortMotherSide(personMap.get(person.getMotherID()));
        }
        sortImmediateFamily(person);
    }

    public void partitionEvents() {
        for (Event e : allEvents) {
            eventMap.put(e.getEventID(), e);
            eventTypes.add(e.getEventType());
            eventPersonMap.put(e, personMap.get(e.getPersonID()));
        }
    }

    public void sortFatherSide(Person person) {

        if (person.getFatherID() != null) {
            Person father = personMap.get(person.getFatherID());
            fatherSideMales.add(father);
            allMales.add(father);
            sortFatherSide(father);
        }
        if (person.getMotherID() != null) {
            Person mother = personMap.get(person.getMotherID());
            fatherSideFemales.add(mother);
            allFemales.add(mother);
            sortFatherSide(mother);
        }
    }

    public void sortMotherSide(Person person) {
        if (person.getFatherID() != null) {
            Person father = personMap.get(person.getFatherID());
            motherSideMales.add(father);
            allMales.add(father);
            sortMotherSide(father);
        }
        if (person.getMotherID() != null) {
            Person mother = personMap.get(person.getMotherID());
            motherSideFemales.add(mother);
            allFemales.add(mother);
            sortMotherSide(mother);
        }
    }

    public void sortImmediateFamily(Person person) {
        for (Person p : allPeople) {
            if ((p.getFatherID() != null && p.getFatherID().equals(person.getPersonID())) ||
                    (p.getMotherID() != null && p.getMotherID().equals(person.getPersonID()))) {
                addImmediateFamilyMember(p);
            }
        }
        if (person.getSpouseID() != null) {
            addImmediateFamilyMember(personMap.get(person.getSpouseID()));
        }
        if (person.getFatherID() != null) {
            immediateFamilyMales.add(personMap.get(person.getFatherID()));
        }
        if (person.getMotherID() != null) {
            immediateFamilyFemales.add(personMap.get(person.getMotherID()));
        }

    }

    public void addImmediateFamilyMember(Person person) {
        if (person.getGender() == "m") {
            immediateFamilyMales.add(person);
        }
        else {
            immediateFamilyFemales.add(person);
        }
    }

    public ArrayList<Person> getImmediateFamily(Person selectedPerson) {
        for (Person relative: allPeople) {
            String relativeID = relative.getPersonID();
            if (relativeID.equals(selectedPerson.getFatherID())) {
                immediateFamily.add(relative);
                familyRelationMap.put(relative, "FATHER");
            }
            if (relativeID.equals(selectedPerson.getMotherID())) {
                immediateFamily.add(relative);
                familyRelationMap.put(relative, "MOTHER");
            }
            if (relativeID.equals(selectedPerson.getSpouseID())) {
                immediateFamily.add(relative);
                familyRelationMap.put(relative, "SPOUSE");
            }
            if ((relative.getFatherID() != null && relative.getFatherID().equals(selectedPerson.getPersonID())) ||
                    (relative.getMotherID() != null && relative.getMotherID().equals(selectedPerson.getPersonID()))) {
                immediateFamily.add(relative);
                familyRelationMap.put(relative, "CHILD");
            }
        }
        ArrayList sortedImmediateFamily = getSortedImmediateFamily(immediateFamily);
        return sortedImmediateFamily;
    }

    public ArrayList<Person> getSortedImmediateFamily(ArrayList<Person> immediateFamily) {
        ArrayList<Person> sortedImmediateFamily = new ArrayList();
        for (Person p : immediateFamily) {
            if (familyRelationMap.get(p).equals("FATHER")) {
                sortedImmediateFamily.add(p);
                break;
            }
        }
        for (Person p : immediateFamily) {
            if (familyRelationMap.get(p).equals("MOTHER")) {
                sortedImmediateFamily.add(p);
                break;
            }
        }
        for (Person p : immediateFamily) {
            if (familyRelationMap.get(p).equals("SPOUSE")) {
                sortedImmediateFamily.add(p);
                break;
            }
        }
        for (Person p : immediateFamily) {
            if (familyRelationMap.get(p).equals("CHILD")) {
                sortedImmediateFamily.add(p);
            }
        }
        return sortedImmediateFamily;
    }

    public void setPersonalEvents(Person selectedPerson) {
        String personID = selectedPerson.getPersonID();
        String spouseID = selectedPerson.getSpouseID();
        for (Event e : allEvents) {
            if (e.getPersonID().equals(personID)) {
                personalEvents.add(e);
            }
            if (e.getPersonID().equals(spouseID)) {
                String type = e.getEventType().toLowerCase();
                if (type.equals("marriage")) {
                    personalEvents.add(e);
                }
            }
        }
        sortPersonalEvents();
    }

    public void sortPersonalEvents() {
        Set<Integer> years = new TreeSet<Integer>();
        ArrayList<Event> sortedEvents = new ArrayList<>();
        for (Event e : personalEvents) {
            years.add(e.getYear());
        }
        for (int year : years) {
            for (Event e : personalEvents) {
                if (e.getYear() == year) {
                    sortedEvents.add(e);
                }
            }
        }
        personalEvents = sortedEvents;

    }

    public ArrayList<Event> getPersonalEvents() {
        return personalEvents;
    }

    public void clearPersonData() {
        personalEvents.clear();
        immediateFamily.clear();
        familyRelationMap.clear();
    }

    public void filterMotherSideEvents() {
        Set<Person> motherSide = new HashSet<>();
        motherSide.addAll(getMotherSideFemales());
        motherSide.addAll(getMotherSideMales());
        ArrayList<Event> newEvents = new ArrayList<>();
        for (Event e : allEvents) {
            Person person = personMap.get(e.getPersonID());
            if (!motherSide.contains(person)) {
                newEvents.add(e);
            }
        }
        setAllEvents(newEvents);
    }

    public void filterFatherSideEvents() {
        Set<Person> fatherSide = new HashSet<>();
        fatherSide.addAll(getFatherSideFemales());
        fatherSide.addAll(getFatherSideMales());
        ArrayList<Event> newEvents = new ArrayList<>();
        for (Event e : allEvents) {
            Person person = personMap.get(e.getPersonID());
            if (!fatherSide.contains(person)) {
                newEvents.add(e);
            }
        }
        setAllEvents(newEvents);
    }

    public Set<Person> getFatherSidePeople() {
        Set<Person> fatherSidePeople = new HashSet<>();
        fatherSidePeople.addAll(fatherSideMales);
        fatherSidePeople.addAll(fatherSideFemales);
        return fatherSidePeople;
    }

    public Set<Person> getMotherSidePeople() {
        Set<Person> motherSidePeople = new HashSet<>();
        motherSidePeople.addAll(motherSideMales);
        motherSidePeople.addAll(motherSideFemales);
        return motherSidePeople;
    }

    public void filterMaleEvents() {

        ArrayList<Event> newEvents = new ArrayList<>();
        for (Event event : allEvents) {
            Person person = personMap.get(event.getPersonID());
            if (!allMales.contains(person)) {
                newEvents.add(event);
            }
        }
        setAllEvents(newEvents);
    }

    public void filterFemaleEvents() {
        ArrayList<Event> newEvents = new ArrayList<>();
        for (Event event : allEvents) {
            Person person = personMap.get(event.getPersonID());
            if (!allFemales.contains(person)) {
                newEvents.add(event);
            }
        }
        setAllEvents(newEvents);
    }


    public boolean searchEvent(String query) {
        for (Event event : allEvents) {
            String eventInfo = event.getEventType() + " " + event.getCity() + " "
                    + event.getCountry() + " " + event.getYear();
            if (eventInfo.toLowerCase().contains(query.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean searchPerson(String query) {
        for (Person person : allPeople) {
            String personName = person.getFirstName() + " " + person.getLastName();
            if (personName.toLowerCase().contains(query.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}

