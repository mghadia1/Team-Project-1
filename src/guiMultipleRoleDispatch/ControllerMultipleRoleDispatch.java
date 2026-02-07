package guiMultipleRoleDispatch;

import entityClasses.User;
import javafx.stage.Stage;

public class ControllerMultipleRoleDispatch {

    protected static void selectAdminRole(Stage theStage, User currentUser) {
        guiAdminHome.ViewAdminHome.displayAdminHome(theStage, currentUser);
    }

    protected static void selectRole1(Stage theStage, User currentUser) {
        guiRole1.ViewRole1Home.displayRole1Home(theStage, currentUser);
    }

    protected static void selectRole2(Stage theStage, User currentUser) {
        guiRole2.ViewRole2Home.displayRole2Home(theStage, currentUser);
    }
}
