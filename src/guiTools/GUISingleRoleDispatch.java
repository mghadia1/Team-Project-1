package guiTools;

import entityClasses.User;
import javafx.stage.Stage;

public class GUISingleRoleDispatch {

    public static void dispatchToSingleRole(Stage theStage, User user) {
        if (user.getAdminRole()) {
            guiAdminHome.ViewAdminHome.displayAdminHome(theStage, user);
        } else if (user.getNewRole1()) {
            guiRole1.ViewRole1Home.displayRole1Home(theStage, user);
        } else if (user.getNewRole2()) {
            guiRole2.ViewRole2Home.displayRole2Home(theStage, user);
        }
    }
}
