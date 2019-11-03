package com.roha.example.spock.demo.model

class User {
    Long id
    private String name
    private String email
    private String password
    private String phone

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
