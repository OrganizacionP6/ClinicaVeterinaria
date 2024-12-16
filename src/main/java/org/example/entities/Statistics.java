package org.example.entities;

public class Statistics {
    private long appointments;
    private long pets;
    private long guardians;
    
    public Statistics(long appointments, long pets, long guardians) {
        this.appointments = appointments;
        this.pets = pets;
        this.guardians = guardians;
    }
    
    public long getAppointments() {
        return appointments;
    }
    
    public void setAppointments(long appointments) {
        this.appointments = appointments;
    }
    
    public long getPets() {
        return pets;
    }
    
    public void setPets(long pets) {
        this.pets = pets;
    }
    
    public long getGuardians() {
        return guardians;
    }
    
    public void setGuardians(long guardians) {
        this.guardians = guardians;
    }
}
