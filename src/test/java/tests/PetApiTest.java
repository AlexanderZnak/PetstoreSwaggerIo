package tests;

import adapters.BaseAdapter;
import com.google.gson.Gson;
import models.User;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;

public class PetApiTest {
    BaseAdapter baseAdapter = new BaseAdapter();

    @Test
    public void uploadsImage() {
        baseAdapter
                .doPostRequestUploadFile("additionalMetadata", "Hello", new File("src/test/resources/hello.txt"), "/v2/pet/2/uploadImage", 200)
                .validateResponseUploadedFile("message", "Hello", "hello.txt");
    }

    @Test
    public void getUserByUserName() {
        baseAdapter
                .doGetRequest("/v2/user/user1", 200)
                .validateResponseViaObjects("src/test/resources/user1.json");
    }

    @Test
    public void addNewPet() {
        baseAdapter
                .doPostRequestWithBody(new File("src/test/resources/pet_body.json"), "/v2/pet", 200)
                .validateResponseViaJsonPath("name", "doggie");
    }

    @Test
    public void updateExistingPet() {
        baseAdapter
                .doPutRequest(new File("src/test/resources/update_pet_body.json"), "/v2/pet", 200)
                .validateResponseViaJsonPath("name", "Ronnie");
    }

    @Test
    public void createListOfUser() {
        User user = new User(1, "Dacent", "Sasha", "Znak", "bla@yandex.by", "1234", "3753362564654", 5);
        Gson gson = new Gson();
        String[] jsonObject = {gson.toJson(user)};
        baseAdapter
                .doPostRequestWithBody(Arrays.toString(jsonObject), "/v2/user/createWithList", 200);
    }

}
