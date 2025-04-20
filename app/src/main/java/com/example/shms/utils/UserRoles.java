package com.example.shms.utils;

import com.example.shms.data.local.entities.User;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class UserRoles {
    private static Set<String> adminPermissions = new HashSet<>(Arrays.asList(
            "MANAGE_USERS",
            "MANAGE_DOCTORS",
            "MANAGE_STAFF",
            "MANAGE_PATIENTS",
            "VIEW_ALL_RECORDS"
    ));

    private static Set<String> doctorPermissions = new HashSet<>(Arrays.asList(
            "VIEW_PATIENTS",
            "MANAGE_APPOINTMENTS",
            "MANAGE_SCHEDULES",
            "UPDATE_MEDICAL_RECORDS"
    ));

    private static Set<String> staffPermissions = new HashSet<>(Arrays.asList(
            "VIEW_APPOINTMENTS",
            "MANAGE_BASIC_INFO",
            "SCHEDULE_APPOINTMENTS"
    ));

    private static Set<String> patientPermissions = new HashSet<>(Arrays.asList(
            "VIEW_OWN_RECORDS",
            "BOOK_APPOINTMENTS",
            "UPDATE_PROFILE"
    ));

    public static boolean hasPermission(User user, String permission) {
        if (user.hasRole(Constants.ROLE_ADMIN)) {
            return adminPermissions.contains(permission);
        }

        Set<String> permissions = new HashSet<>();

        if (user.isDoctor()) {
            permissions.addAll(doctorPermissions);
        }
        if (user.isStaff()) {
            permissions.addAll(staffPermissions);
        }
        if (user.isPatient()) {
            permissions.addAll(patientPermissions);
        }

        return permissions.contains(permission);
    }
}
