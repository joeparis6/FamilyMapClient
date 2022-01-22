package com.example.familymapclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import model.Event;
import model.Person;

public class PersonActivity extends AppCompatActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        ExpandableListView expandableListView = findViewById(R.id.expandable);
        DataCache cache = DataCache.getInstance();
        Person selectedPerson = cache.getSelectedPerson();
        TextView firstName = findViewById(R.id.set_first_name);
        TextView lastName = findViewById((R.id.set_last_name));
        TextView gender = findViewById(R.id.set_gender);
        firstName.setText(selectedPerson.getFirstName());
        lastName.setText(selectedPerson.getLastName());
        gender.setText(setGender(selectedPerson));
        ArrayList<Person> people = cache.getImmediateFamily(selectedPerson);
        ArrayList<Event> events = cache.getPersonalEvents();
        expandableListView.setAdapter(new ExpandableListAdapter(people, events));
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private static final int PERSON_GROUP_POSITION = 0;
        private static final int EVENT_GROUP_POSITION = 1;
        private final int GROUP_COUNT = 2;

        private final ArrayList<Person> people;
        private final ArrayList<Event> events;

        ExpandableListAdapter(ArrayList<Person> people, ArrayList<Event> events) {
            this.people = people;
            this.events = events;
        }

        @Override
        public int getGroupCount() {
            return GROUP_COUNT;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            switch(groupPosition) {
                case PERSON_GROUP_POSITION:
                    return people.size();
                case EVENT_GROUP_POSITION:
                    return events.size();
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            switch(groupPosition) {
                case PERSON_GROUP_POSITION:
                    return getString(R.string.Family);
                case EVENT_GROUP_POSITION:
                    return getString(R.string.LifeEvents);
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            switch(groupPosition) {
                case PERSON_GROUP_POSITION:
                    return people.get(childPosition);
                case EVENT_GROUP_POSITION:
                    return events.get(childPosition);
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_group, parent,false);
            }

            TextView titleView = convertView.findViewById(R.id.list_title);

            switch(groupPosition) {
                case PERSON_GROUP_POSITION:
                    titleView.setText(R.string.Family);
                    break;
                case EVENT_GROUP_POSITION:
                    titleView.setText(R.string.LifeEvents);
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View itemView;

            switch(groupPosition) {
                case PERSON_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.person_list_item, parent, false);

                    initializePersonView(itemView, childPosition);
                    break;
                case EVENT_GROUP_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.event_list_item, parent, false);
                    initializeEventView(itemView, childPosition);
                    break;
                default:
                    throw new IllegalArgumentException();
            }

            return itemView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        private void initializePersonView(View PersonItemView, int childPosition) {
            Person selectedPerson  = people.get(childPosition);
            mImageView = (ImageView) PersonItemView.findViewById(R.id.person_icon);
            TextView personName = PersonItemView.findViewById(R.id.person_name);
            TextView personRelation = PersonItemView.findViewById(R.id.person_relation);
            personName.setText(selectedPerson.getFirstName() + " " + selectedPerson.getLastName());
            personRelation.setText(getRelation(selectedPerson));
            setImage(selectedPerson);

            PersonItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DataCache cache = DataCache.getInstance();
                    cache.clearPersonData();
                    cache.setSelectedPerson(selectedPerson);
                    cache.setPersonalEvents(selectedPerson);
                    openPersonActivity();
                }
            });
        }

        private void initializeEventView(View EventItemView, int childPosition) {
            DataCache cache = DataCache.getInstance();
            Event selectedEvent = events.get(childPosition);
            TextView eventInfo = EventItemView.findViewById(R.id.event_info);
            TextView associatedPerson = EventItemView.findViewById(R.id.associated_name);

            eventInfo.setText(selectedEvent.getEventType() + " (" + selectedEvent.getYear() + ")\n"
            + selectedEvent.getCity() + ", " + selectedEvent.getCountry());
            Person person = cache.getEventPersonMap().get(selectedEvent);
            associatedPerson.setText(person.getFirstName() + " " + person.getLastName());

            EventItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    cache.setFocusEvent(selectedEvent);
                    openEventActivity();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
        return true;
    }

    public void openPersonActivity() {
        Intent intent = new Intent(this, PersonActivity.class);

        startActivity(intent);
    }

    public void openEventActivity() {
        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }

    public String getRelation(Person person) {
        DataCache cache = DataCache.getInstance();
        Map<Person, String> relationMap = cache.getFamilyRelationMap();
        String relation = relationMap.get(person);
        return relation;
    }

    public void setImage(Person person) {
        if (person.getGender().toLowerCase().equals("m")) {
            mImageView.setImageResource(R.drawable.male_icon);
        }
        if (person.getGender().toLowerCase().equals("f")) {
            mImageView.setImageResource(R.drawable.female_icon);
        }
    }

    public String setGender(Person person) {
        if (person.getGender().toLowerCase().equals("m")) {
            return "Male";
        }
        else if (person.getGender().toLowerCase().equals("f")) {
            return "Female";
        }
        else {
            return "ERROR";
        }
    }



}