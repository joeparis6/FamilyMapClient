package com.example.familymapclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    Switch lifeStorySwitch;
    Switch familyTreeSwitch;
    Switch spouseLineSwitch;
    Switch fatherSideSwitch;
    Switch motherSideSwitch;
    Switch maleEventSwitch;
    Switch femaleEventSwitch;
    Switch logoutSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        DataCache cache = DataCache.getInstance();
        cache.setFamilyTreeLines(true);
        cache.setSpouseLines(true);
        cache.setLifeStoryLines(true);

        lifeStorySwitch = findViewById(R.id.life_story_switch);
        familyTreeSwitch = findViewById((R.id.family_tree_switch));
        spouseLineSwitch = findViewById((R.id.spouse_switch));
        fatherSideSwitch = findViewById((R.id.father_side_switch));
        motherSideSwitch = findViewById((R.id.mother_side_switch));
        maleEventSwitch = findViewById((R.id.male_events_switch));
        femaleEventSwitch = findViewById(R.id. female_events_switch);
        logoutSwitch = findViewById((R.id.logout_switch));

        lifeStorySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cache.setLifeStoryLines(true);
                    showLifeStoryLines();
                }
                else {
                    cache.setLifeStoryLines(false);
                }
            }
        });

        familyTreeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cache.setFamilyTreeLines(true);
                    showFamilyTreeLines();
                }
                else {
                    cache.setFamilyTreeLines(false);
                }
            }
        });

        spouseLineSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cache.setSpouseLines(true);
                    showSpouseLines();
                }
                else {
                    cache.setSpouseLines(false);
                }
            }
        });

        fatherSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    filterFatherSideEvents();
                }
                else {
                    cache.setFatherSide(false);
                }
            }
        });

        motherSideSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    filterMotherSideEvents();
                }
                else {
                    cache.setMotherSide(false);
                }
            }
        });

        maleEventSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    filterMaleEvents();
                }
                else {
                    cache.setMale(false);
                }
            }
        });

        femaleEventSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    filterFemaleEvents();
                }
                else {
                    cache.setFemale(false);
                }
            }
        });

        logoutSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    logout();
                }
            }
        });

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

    public void showLifeStoryLines() {
        Toast.makeText(getApplicationContext(), "Life Story", Toast.LENGTH_SHORT).show();

    }

    public void showFamilyTreeLines() {
        Toast.makeText(getApplicationContext(), "Family Tree", Toast.LENGTH_SHORT).show();
    }

    public void showSpouseLines() {
        Toast.makeText(getApplicationContext(), "Spouse", Toast.LENGTH_SHORT).show();
    }

    public void filterFatherSideEvents() {
        Toast.makeText(getApplicationContext(), "Father Side", Toast.LENGTH_SHORT).show();
        DataCache cache = DataCache.getInstance();
        cache.setFilter(true);
        cache.setFatherSide(true);

    }

    public void filterMotherSideEvents() {
        Toast.makeText(getApplicationContext(), "Mother Side", Toast.LENGTH_SHORT).show();
        DataCache cache = DataCache.getInstance();
        cache.setFilter(true);
        cache.setMotherSide(true);
    }

    public void filterMaleEvents() {
        Toast.makeText(getApplicationContext(), "Male Events", Toast.LENGTH_SHORT).show();
        DataCache cache = DataCache.getInstance();
        cache.setFilter(true);
        cache.setMale(true);
    }

    public void filterFemaleEvents() {
        Toast.makeText(getApplicationContext(), "Female Events", Toast.LENGTH_SHORT).show();
        DataCache cache = DataCache.getInstance();
        cache.setFilter(true);
        cache.setFemale(true);
    }

    public void logout() {
        Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_SHORT).show();
        DataCache cache = DataCache.getInstance();
        cache.setFilter(true);
    }
}