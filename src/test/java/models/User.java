package models;

import com.google.gson.annotations.Expose;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    int id;
    @Expose
    String username;
    @Expose
    String firstName;
    @Expose
    String lastName;
    @Expose
    String email;
    @Expose
    String password;
    @Expose
    String phone;
    @Expose
    int userStatus;

}
