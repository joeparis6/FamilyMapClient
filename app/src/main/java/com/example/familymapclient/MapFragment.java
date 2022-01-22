package com.example.familymapclient;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import model.Event;
import model.Person;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap map;
    private ArrayList<Event> events;
    private Map<String, Person> personMap;
    private TextView mTextView;
    private ImageView mImageView;
    private Boolean selected;
    private Marker mMarker;
    private final int fullWidth = 16;
    private int width;
    private final float HUE_RED = 0.0f;
    private final float HUE_ORANGE = 030.0f;
    private final float HUE_YELLOW = 60.0f;
    private final float HUE_GREEN = 120.0f;
    private final float HUE_CYAN = 180.0f;
    private final float HUE_AZURE = 210.0f;
    private final float HUE_BLUE = 240.0f;
    private final float HUE_VIOLET = 270.0f;
    private final float HUE_MAGENTA = 300.0f;
    private final float HUE_ROSE = 330.0f;
    private Map<String, Float> eventToColor = new HashMap<>();
    private Set<Float> usedColors = new HashSet<>();
    private ArrayList<Float> colors = new ArrayList<>();
    private ArrayList<Polyline> allLines = new ArrayList<>();
    private Person basePerson;
    private Event baseEvent;
    private Set<Person> filteredPeople = new HashSet<>();
    private boolean noOne;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initializeColors();
        selected = false;
        DataCache cache = DataCache.getInstance();
        cache.setAllEventsCopy(cache.getAllEvents());
        basePerson = cache.getRootUser();
        baseEvent = getFirstEvent(basePerson);

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        View view = layoutInflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mTextView = (TextView) view.findViewById(R.id.mapTextView);
        mImageView = (ImageView) view.findViewById(R.id.show_image);

        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selected) {
                    Toast.makeText(getContext(), "Person", Toast.LENGTH_SHORT).show();
                    openPersonActivity();
                }
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        removeAllLines();
        DataCache cache = DataCache.getInstance();
        basePerson = cache.getSelectedPerson();
        baseEvent = cache.getResumeEvent();
        if (cache.isFilter()) {
            applyFilters();
            updateMarkers();
        }
        noOne = false;
        updateLines();

    }

    public void applyFilters() {
        filteredPeople.clear();
        DataCache cache = DataCache.getInstance();
        cache.setAllEvents(cache.getAllEventsCopy());
        if (cache.isMale() && cache.isFatherSide()) {
            filterFatherSideMales();

        }
        else if (cache.isMale() && cache.isMotherSide()) {
            filterMotherSideMales();

        }
        else if (cache.isFemale() && cache.isFatherSide()) {
            filterFatherSideFemales();

        }
        else if (cache.isFemale() && cache.isMotherSide()) {
            filterFatherSideFemales();

        }
        else if (cache.isMotherSide() && cache.isFatherSide()) {
            Person rootUser = cache.getRootUser();
            Person spouse = cache.getPersonMap().get(rootUser.getSpouseID());
            ArrayList<Person> people = cache.getAllPeople();
            for (Person p : people) {
                if (p != rootUser && p != spouse) {
                    filteredPeople.add(p);
                }
            }
        }
        else if (cache.isMale() && cache.isFemale()) {
            ArrayList<Person> people = cache.getAllPeople();
            for (Person p : people) {
                filteredPeople.add(p);
            }
            noOne = true;
        }
        else if (cache.isMale()) {
            filterMales();

        }
        else if (cache.isFemale()) {
            filterFemales();

        }
        else if (cache.isFatherSide()) {
            filterFatherSide();
        }
        else if (cache.isMotherSide()) {
            filterMotherSide();
        }
        ArrayList<Event> filteredEvents = getFilteredEvents();
        cache.setAllEvents(filteredEvents);
    }

    public  void filterFatherSideMales() {
        DataCache cache = DataCache.getInstance();
        filteredPeople = cache.getFatherSideMales();
    }

    public  void filterFatherSideFemales() {
        DataCache cache = DataCache.getInstance();
        filteredPeople = cache.getFatherSideFemales();
    }

    public  void filterFatherSide() {
        DataCache cache = DataCache.getInstance();
        Set<Person> females = cache.getFatherSideFemales();
        Set<Person> males = cache.getFatherSideMales();
        filteredPeople.addAll(females);
        filteredPeople.addAll(males);

    }

    public  void filterMotherSideMales() {
        DataCache cache = DataCache.getInstance();
        filteredPeople = cache.getMotherSideMales();
    }

    public  void filterMotherSideFemales() {
        DataCache cache = DataCache.getInstance();
        filteredPeople = cache.getMotherSideFemales();
    }

    public void filterMotherSide() {
        DataCache cache = DataCache.getInstance();
        Set<Person> females = cache.getMotherSideFemales();
        Set<Person> males = cache.getMotherSideMales();
        filteredPeople.addAll(females);
        filteredPeople.addAll(males);
    }

    public void filterMales() {
        DataCache cache = DataCache.getInstance();
        filteredPeople = cache.getAllMales();

    }

    public void filterFemales() {
        DataCache cache = DataCache.getInstance();
        filteredPeople = cache.getAllFemales();
    }

    public ArrayList<Event> getFilteredEvents() {
        DataCache cache = DataCache.getInstance();
        ArrayList<Event> filteredEvents = new ArrayList<>();
        for (Event e : cache.getAllEvents()) {
            Person person = personMap.get(e.getPersonID());
            if (!filteredPeople.contains(person)) {
                filteredEvents.add(e);
            }
        }
        return filteredEvents;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        DataCache cache = DataCache.getInstance();
        events = cache.getAllEvents();
        personMap = cache.getPersonMap();
        ArrayList<Marker> markers = new ArrayList<>();
        for (Event e : events) {
            String type = e.getEventType().toLowerCase();
            Person person = personMap.get(e.getPersonID());
            LatLng location = new LatLng(e.getLatitude(), e.getLongitude());
            float hue;
            if (eventToColor.containsKey(type)) {
                hue = eventToColor.get(type);
            }
            else {
                hue = getHue();
                eventToColor.put(type, hue);
            }
            usedColors.add(hue);
            Marker marker = map.addMarker(new MarkerOptions().position(location).title(e.getEventType()));
            marker.setTag(e);
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(hue));
            markers.add(marker);
        }
        cache.setAllMarkers(markers);
        if (cache.isNewEvent()) {
            centerOnEvent();
        }

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                selected = true;
                mMarker = marker;
                removeAllLines();
                cache.setSelectedMarker(marker);
                Event event = (Event) mMarker.getTag();
                Person person = cache.getPersonMap().get(event.getPersonID());
                setImage(person);
                basePerson = person;
                baseEvent = event;
                cache.setResumeEvent(event);
                cache.clearPersonData();
                cache.setSelectedPerson(person);
                cache.setPersonalEvents(person);
                updateText(mMarker);
                updateLines();
                return false;
            }
        });
    }

    public void centerOnEvent() {
        DataCache cache = DataCache.getInstance();
        cache.setNewEvent(false);

        Event focusEvent = cache.getFocusEvent();
        Person focusPerson = cache.getPersonMap().get(focusEvent.getPersonID());
        setImage(focusPerson);
        LatLng location = new LatLng(focusEvent.getLatitude(), focusEvent.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLng(location));
        for (Marker marker : cache.getAllMarkers()) {
            Event event = (Event) marker.getTag();
            if (event.getEventID().equals(focusEvent.getEventID())) {
                updateText(marker);
                break;
            }

        }

    }

    public void updateMarkers() {
        map.clear();
        DataCache cache = DataCache.getInstance();
        events = cache.getAllEvents();
        personMap = cache.getPersonMap();
        ArrayList<Marker> markers = new ArrayList<>();
        for (Event e : events) {
            String type = e.getEventType().toLowerCase();
            Person person = personMap.get(e.getPersonID());
            LatLng location = new LatLng(e.getLatitude(), e.getLongitude());
            float hue;
            if (eventToColor.containsKey(type)) {
                hue = eventToColor.get(type);
            }
            else {
                hue = getHue();
                eventToColor.put(type, hue);
            }
            usedColors.add(hue);
            Marker marker = map.addMarker(new MarkerOptions().position(location).title(e.getEventType()));
            marker.setTag(e);
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(hue));
            markers.add(marker);
        }
        cache.setAllMarkers(markers);
    }

    public void removeAllLines() {
        for (Polyline line : allLines) {
            line.remove();
        }
    }

    public void updateLines() {
        removeAllLines();
        DataCache cache = DataCache.getInstance();
        if (cache.isLifeStoryLines() && !noOne) {
            createLifeStoryLines();
        }
        if (cache.isFamilyTreeLines()) {
            createFamilyTreeLines();
        }
        if (cache.isSpouseLines()) {
            createSpouseLines();
        }
    }

    public void createLifeStoryLines() {
        DataCache cache = DataCache.getInstance();
        ArrayList<LatLng> locations = new ArrayList<>();
        ArrayList<Event> personalEvents = cache.getPersonalEvents();
        for (Event e : personalEvents) {
            LatLng location = new LatLng(e.getLatitude(), e.getLongitude());
            locations.add(location);
        }
        for (int i = 0; i < locations.size()-1; i++) {
            Polyline storyLine = map.addPolyline(new PolylineOptions()
                    .add(locations.get(i), locations.get(i+1))
                    .width(10)
                    .color(Color.GREEN));
            allLines.add(storyLine);
        }
    }

    public void createFamilyTreeLines() {
        width = fullWidth;
        createAncestorLines(basePerson, baseEvent);
    }

    public void createAncestorLines(Person person, Event event) {
        DataCache cache = DataCache.getInstance();
        int tmpWidth = width;
        if (person.getFatherID() != null || (!cache.isFatherSide() && person == cache.getRootUser())) {
            createFatherLine(person, event);
        }
        width = tmpWidth;
        if (person.getMotherID() != null || (!cache.isMotherSide() && person == cache.getRootUser())) {
            createMotherLine(person, event);
        }
    }

    public void createFatherLine(Person person, Event event) {
        Person father = personMap.get(person.getFatherID());
        Event fatherEvent = getFirstEvent(father);
        if (fatherEvent == null) {
            return;
        }
        LatLng fatherEventLatLng = new LatLng(fatherEvent.getLatitude(), fatherEvent.getLongitude());
        LatLng eventLatLng = new LatLng(event.getLatitude(), event.getLongitude());

        Polyline fatherLine = map.addPolyline(new PolylineOptions()
                .add(eventLatLng, fatherEventLatLng)
                .width(width)
                .color(Color.BLUE));
        allLines.add(fatherLine);
        width -= 4;
        createAncestorLines(father, fatherEvent);
    }

    public void createMotherLine(Person person, Event event) {
        Person mother = personMap.get(person.getMotherID());
        Event motherEvent = getFirstEvent(mother);
        if (motherEvent == null) {
            return;
        }
        LatLng motherEventLatLng = new LatLng(motherEvent.getLatitude(), motherEvent.getLongitude());
        LatLng eventLatLng = new LatLng(event.getLatitude(), event.getLongitude());

        Polyline motherLine = map.addPolyline(new PolylineOptions()
                .add(eventLatLng, motherEventLatLng)
                .width(width)
                .color(Color.BLUE));
        allLines.add(motherLine);
        width -= 4;
        createAncestorLines(mother, motherEvent);
    }

    public void createSpouseLines() {
        Event event = baseEvent;
        Person person = personMap.get(event.getPersonID());
        Person spouse = personMap.get(person.getSpouseID());
        Event spouseEvent = getFirstEvent(spouse);
        if (spouseEvent == null) {
            return;
        }
        LatLng eventLatLng = new LatLng(event.getLatitude(), event.getLongitude());
        LatLng spouseEventLatLng = new LatLng(spouseEvent.getLatitude(), spouseEvent.getLongitude());

        Polyline spouseLine = map.addPolyline(new PolylineOptions()
                .add(eventLatLng, spouseEventLatLng)
                .width(5)
                .color(Color.RED));
        allLines.add(spouseLine);
    }

    public Event getFirstEvent(Person newPerson) {
        DataCache cache = DataCache.getInstance();
        cache.clearPersonData();
        cache.setSelectedPerson(newPerson);
        cache.setPersonalEvents(newPerson);
        ArrayList<Event> lifeEvents = cache.getPersonalEvents();

        if (lifeEvents.size() != 0 ) {
            Event event = lifeEvents.get(0);
            cache.clearPersonData();
            cache.setSelectedPerson(basePerson);
            cache.setPersonalEvents(basePerson);
            return event;
        }

        cache.clearPersonData();
        cache.setSelectedPerson(basePerson);
        cache.setPersonalEvents(basePerson);
        return null;
    }



    public void setImage(Person person) {
        if (person.getGender().toLowerCase().equals("m")) {
            mImageView.setImageResource(R.drawable.male_icon);
        }
        if (person.getGender().toLowerCase().equals("f")) {
            mImageView.setImageResource(R.drawable.female_icon);
        }
    }

    public void openPersonActivity() {
        Intent intent = new Intent(getActivity(), PersonActivity.class);

        startActivity(intent);
    }

    public float getHue() {
        for (int i = 0; i < colors.size(); i++) {
            if (!usedColors.contains(colors.get(i))) {
                return colors.get(i);
            }
        }
        usedColors.clear();
        return getHue();
    }

    private void updateText(Marker marker) {
        Event event = (Event) marker.getTag();
        String text = getEventInfo(event);
        mTextView.setText(text);
    }

    public String getEventInfo(Event event) {
        DataCache cache = DataCache.getInstance();
        Person person = cache.getPersonMap().get(event.getPersonID());
        String eventInfo = person.getFirstName() + " " + person.getLastName() + "\n";
        eventInfo += event.getEventType() + "\n" + event.getCity()
                    + ", " + event.getCountry() + "\n" + event.getYear();
        return eventInfo;
    }

    public void initializeColors() {
        colors.add(HUE_RED);
        colors.add(HUE_ORANGE);
        colors.add(HUE_YELLOW);
        colors.add(HUE_GREEN);
        colors.add(HUE_CYAN);
        colors.add(HUE_AZURE);
        colors.add(HUE_BLUE);
        colors.add(HUE_VIOLET);
        colors.add(HUE_MAGENTA);
        colors.add(HUE_ROSE);
    }

}
