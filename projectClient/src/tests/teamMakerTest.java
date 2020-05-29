package tests;

import controllers.Controller;
import controllers.DefaultController;
import controllers.ScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.EmptyNodeQueryException;
import users.User;

import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class teamMakerTest extends ApplicationTest {
    private Scene scene;
    private Stage stage;
    User user;

    @Override
    public void start(Stage primaryStage) throws Exception{
        user = new User(2,"Dor","Elkabetz","dore","dor931992");
        DefaultController.setUser(user);
        Parent root = FXMLLoader.load(getClass().getResource("/view/TeamMaker.fxml"));
        primaryStage.setTitle("Test Football Association Screen");
        Scene main = new Scene(root, 600, 400);
        primaryStage.setScene(main);
        this.scene = main;
        this.stage = primaryStage;
        primaryStage.show();
        ScreenController screenController = new ScreenController(primaryStage);
        Controller.initController(screenController);
    }

    @Before
    public void setUp () throws Exception {

    }

    @After
    public void tearDown () throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void testBackBtn(){
        try{
            clickOn("Back");
            Scene curr = stage.getScene();
            Node pane = curr.lookup("#AssociationPane");
            if (pane == null) {
                Assert.fail();
            }
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void makeTeamBtn(){
        try {
            ChoiceBox box = (ChoiceBox) scene.lookup("#stadiumNameTxt");
            if (box.getItems().isEmpty()) {
                Assert.fail();
            }
            clickOn("Make");
            Node errorPane= lookup("#error").query();
            if (errorPane == null) {
                Assert.fail();
            }
            try{
                clickOn("OK");
            }catch (Exception e){
                Assert.fail();
            }
            clickOn("#teamNameTxt");
            write("maccabi23",100);
            type(KeyCode.TAB);
            write("Haifa");
            String role = (String) box.getItems().get(0);
            clickOn("#stadiumNameTxt");
            clickOn(role);
            clickOn("#ownerIDTxt");
            write("2");
            clickOn("Make");
            errorPane= lookup("#error").query();
            if (errorPane == null) {
                Assert.fail();
            }
            try{
                clickOn("OK");
            }catch (Exception e){
                Assert.fail();
            }
            doubleClickOn("#teamNameTxt");
            write("maccabi",100);
            type(KeyCode.TAB);
            write("Haifa234");
            doubleClickOn("#ownerIDTxt");
            write("2");
            clickOn("Make");
            errorPane= lookup("#error").query();
            if (errorPane == null) {
                Assert.fail();
            }
            try{
                clickOn("OK");
            }catch (Exception e){
                Assert.fail();
            }
            doubleClickOn("#teamNameTxt");
            write("maccabi",100);
            type(KeyCode.TAB);
            write("Haifa");
            doubleClickOn("#ownerIDTxt");
            write("sd");
            clickOn("Make");
            errorPane= lookup("#error").query();
            if (errorPane == null) {
                Assert.fail();
            }
            try{
                clickOn("OK");
            }catch (Exception e){
                Assert.fail();
            }
            doubleClickOn("#ownerIDTxt");
            write("2");
            clickOn("Make");
            try {
                lookup("#Confirm").query();
                try{
                    clickOn("OK");
                }catch (Exception e1){
                    Assert.fail();
                }
            }catch(EmptyNodeQueryException e){
            }
            try{
                clickOn("OK");
            }catch (Exception e){
                Assert.fail();
            }
            clickOn("Make");
            try {
                lookup("#Confirm").query();
                try{
                    clickOn("OK");
                }catch (Exception e1){
                    Assert.fail();
                }
            }catch(EmptyNodeQueryException e){
            }
            errorPane= lookup("#error").query();
            if (errorPane == null) {
                Assert.fail();
            }
            try{
                clickOn("OK");
            }catch (Exception e){
                Assert.fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
