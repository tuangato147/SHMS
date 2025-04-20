package com.example.shms.data.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "doctors")
public class Doctor {
    @PrimaryKey
    private int id;
    private String name;
    private String specialization;
    // getters and setters
}
