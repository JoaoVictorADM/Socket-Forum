module ChatServidor {
	requires javafx.controls;
	requires javafx.graphics;
	requires java.sql;
	requires com.google.gson;
	
	opens application to javafx.graphics, javafx.fxml;
}
