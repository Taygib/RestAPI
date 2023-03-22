package model.pojo;

public class LoginBodyModelPojo {
    //"{\n"  "    \"email\": \"eve.holt@reqres.in\",\n"  \"password\": \"pistol\"\n" }";
    String email, password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
