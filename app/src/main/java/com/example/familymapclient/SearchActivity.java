package com.example.familymapclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.Event;
import model.Person;

public class SearchActivity extends AppCompatActivity {

    private EditText searchText;
    private ArrayList<Person> allPersons;
    private ArrayList<Event> allEvents;
    private Button searchButton;
    private ArrayList<Person> filteredPeople = new ArrayList<>();
    private ArrayList<Event> filteredEvents = new ArrayList<>();
    private static final int PERSON_VIEW_TYPE = 0;
    private static final int EVENT_VIEW_TYPE = 1;
    String searchQuery;
    SearchAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true); Do I need this?

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

        DataCache cache = DataCache.getInstance();
        allPersons = cache.getAllPeople();
        allEvents = cache.getAllEvents();
        searchButton = findViewById(R.id.button);
        searchText = (EditText) findViewById(R.id.SearchEditText);
        searchText.addTextChangedListener(searchTextWatcher);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSearchEvents(searchQuery);
                getSearchPeople(searchQuery);
                adapter = new SearchAdapter(filteredPeople, filteredEvents);
                recyclerView.setAdapter(adapter);
            }
        });



    }



    private TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            filteredPeople.clear();
            filteredEvents.clear();
            searchQuery = searchText.getText().toString().trim();
            searchButton.setEnabled(!searchQuery.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return true;
    }

    private class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {
        private final ArrayList<Person> searchPeople;
        private final ArrayList<Event> searchEvents;

        SearchAdapter(ArrayList<Person> people, ArrayList<Event> events) {
            this.searchEvents = events;
            this.searchPeople = people;
        }

        @Override
        public int getItemViewType(int position) {

            return position < searchPeople.size() ? PERSON_VIEW_TYPE : EVENT_VIEW_TYPE;
        }


        @NonNull
        @Override
        public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            if (viewType == PERSON_VIEW_TYPE) {
                view = getLayoutInflater().inflate(R.layout.recycle_person, parent, false);
            }
            else {
                view = getLayoutInflater().inflate(R.layout.recycle_event, parent, false);
            }
            return new SearchViewHolder(view, viewType);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
            if (position < searchPeople.size()) {
                holder.bind(searchPeople.get(position));
            }
            else {
                holder.bind(searchEvents.get(position - searchPeople.size()));
            }
        }

        @Override
        public int getItemCount() {
            int eventCount = 0;
            int personCount = 0;
            if (searchEvents != null) {
                eventCount = searchEvents.size();
            }
            if (searchPeople != null) {
                personCount = searchPeople.size();
            }
            return personCount + eventCount;
        }
    }

    private class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView name;
        private TextView eventInfo;
        private ImageView genderImage;
        private Person person;
        private Event event;
        private final int viewType;

        public SearchViewHolder(@NonNull View view, int viewType) {

            super(view);
            this.viewType = viewType;

            itemView.setOnClickListener(this);

            if (viewType == PERSON_VIEW_TYPE ) {
                eventInfo = null;
                name = itemView.findViewById(R.id.person_name);
                genderImage = itemView.findViewById(R.id.person_icon);
            }
            else {
                eventInfo = itemView.findViewById(R.id.event_info);
                name = itemView.findViewById((R.id.associated_name));
            }
        }

        private void bind(Person person) {
            this.person = person;
            name.setText(person.getFirstName() + " " + person.getLastName());
            setImage(person);
        }

        private void bind(Event event) {
            DataCache cache = DataCache.getInstance();
            this.event = event;
            Person person = cache.getEventPersonMap().get(event);
            eventInfo.setText(event.getEventType() + "\n" + event.getCity() + ", " +
                              event.getCountry() + "\n" + event.getYear());
            name.setText(person.getFirstName() + " " + person.getLastName());
        }

        public void setImage(Person person) {
            if (person.getGender().toLowerCase().equals("m")) {
                genderImage.setImageResource(R.drawable.male_icon);
            }
            if (person.getGender().toLowerCase().equals("f")) {
                genderImage.setImageResource(R.drawable.female_icon);
            }
        }

        @Override
        public void onClick(View v) {
            DataCache cache = DataCache.getInstance();
            if (viewType == PERSON_VIEW_TYPE) {
                cache.clearPersonData();
                cache.setSelectedPerson(person);
                cache.setPersonalEvents(person);
                openPersonActivity();
            }
            else {
                cache.setFocusEvent(event);
                openEventActivity();
            }
        }
    }

    public void getSearchPeople(String query) {

        for (Person p : allPersons) {
            String name = p.getFirstName() + " " + p.getLastName();
            name = name.toLowerCase();
            if (name.contains(query.toLowerCase())) {
                filteredPeople.add(p);
            }
        }
    }

    public void getSearchEvents(String query) {
        for (Event e : allEvents) {
            String name = e.getEventType().toLowerCase() + " " + e.getCountry() + " " + e.getCity() + " " + e.getYear();
            if (name.contains(query.toLowerCase())) {
                filteredEvents.add(e);
            }
        }
    }

    public void openPersonActivity() {
        Intent intent = new Intent(this, PersonActivity.class);

        startActivity(intent);
    }

    public void openEventActivity() {
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }

}