package tests;
import controllers.Controller;
import controllers.ScreenController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;


public class LoginTest extends ApplicationTest{

    private Scene scene;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        primaryStage.setTitle("Test Login");
        Scene main = new Scene(root, 300, 400);
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
    public void testLoginBtn () throws InterruptedException {
        write("amitsul",100);
        type(KeyCode.TAB);
        write("asgsag");
        clickOn("Login");
        try{
            clickOn("OK");
        }catch (Exception e){
            Assert.fail();
        }
//        moveTo(scene.lookup("#txtFieldusername"));
        doubleClickOn(scene.lookup("#txtFieldusername"));
        write("marinaBot",100);
        type(KeyCode.TAB);
        write("123456Mm!");
        clickOn("Login");
        try{
            clickOn("OK");
            Assert.fail();
        }catch (Exception e){
            Scene curr = stage.getScene();
            Node pane = curr.lookup("#chooserPane");
            if(pane == null){
                Assert.fail();
            }
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGuestBtn(){
        try {
            clickOn("Guest");
            Scene curr = stage.getScene();
            Node pane = curr.lookup("#guestPane");
            if (pane == null) {
                Assert.fail();
            }
        }
        catch (Exception e){
            Assert.fail();
        }
    }

    @Test
    public void testSignUpBtn(){
        try {
            clickOn("here");
            Scene curr = stage.getScene();
            Node pane = curr.lookup("#registerPane");
            if (pane == null) {
                Assert.fail();
            }
        }
        catch (Exception e){
            Assert.fail();
        }
    }

}
