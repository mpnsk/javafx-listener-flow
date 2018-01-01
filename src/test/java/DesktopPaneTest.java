import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static javafx.scene.input.KeyCode.ENTER;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.hasChildren;
import static org.testfx.matcher.base.NodeMatchers.hasText;

public class DesktopPaneTest extends ApplicationTest {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

//    @Test
//    public void should_drag_file_into_trashcan() {
//        // given:
//        rightClickOn("#desktop").moveTo("New").clickOn("Text Document");
//        write("myTextfile.txt").push(ENTER);
//
//        // when:
//        drag(".file").dropTo("#trash-can");
//
//        // then:
//        verifyThat("#desktop", hasChildren(0, ".file"));
//    }

    @Test
    public void click_move_click_Test() {
        verifyThat(".label", hasText("stage1_init"));
        clickOn("#circle");
        verifyThat(".label", hasText("stage2_create_line"));
        moveTo("#rectangle");
        verifyThat(".label", hasText("stage3_enter_rectangle"));
        moveTo(0, 0);
        verifyThat(".label", hasText("stage2_create_line"));
    }
}