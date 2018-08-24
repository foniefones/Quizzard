package com.quizzard.domain;

public class User {
    private final int id;
    private final String username;
    private final String email;
    private Statistics stats;

    public User(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;

    }

    public String getStats() {
        int percent =  (int) (stats.getCorrect() * 100f / (stats.getWrong() + stats.getCorrect()));

        return "Procent: " + percent + "%";
    }


    public String getCorrect() {
        return "Rätt: " + stats.getCorrect();
    }

    public String getWrong() {
        return "Fel: " + stats.getWrong();
    }


    public void setStats(Statistics stats) {
        this.stats = stats;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
