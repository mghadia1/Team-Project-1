package applicationMain;
	
import java.sql.SQLException;
import database.Database;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class FoundationsMain extends Application {
	
public final static double WINDOW_WIDTH = 800;
public final static double WINDOW_HEIGHT = 600;

public static Database database = new Database();
private Alert databaseInUse = new Alert(AlertType.INFORMATION);

public static int activeHomePage = 0;

@Override
public void start(Stage theStage) {
	
try {
	database.connectToDatabase();
} catch (SQLException e) {
	databaseInUse.setTitle("*** ERROR ***");
	databaseInUse.setHeaderText("Database Is Already Being Used");
	databaseInUse.setContentText("Please stop the other instance and try again!");
	databaseInUse.showAndWait();
	System.exit(0);
}
	
if (database.isDatabaseEmpty()) {
		guiFirstAdmin.ViewFirstAdmin.displayFirstAdmin(theStage); 
	}
	else
		guiUserLogin.ViewUserLogin.displayUserLogin(theStage);
}

public static void main(String[] args) {
	launch(args);
}
}