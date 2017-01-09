package application;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CSVFileReaderController implements Initializable {

	@FXML
	private Button browseBtn;
	@FXML
	private TextField selectedFilePath;

	@FXML
	private ListView<String> csvResultList;

	private Desktop desktop = Desktop.getDesktop();
	final FileChooser fileChooser = new FileChooser();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		browseBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				File file = fileChooser.showOpenDialog(new Stage());
				if (file != null) {
					System.out.println(file.getAbsolutePath());
					selectedFilePath.setText(file.getAbsolutePath());

					readFile(file);
				}
			}

			ObservableList<String> list = FXCollections.observableArrayList();

			private void readFile(File file) {
				String csvFile = file.getAbsolutePath();
				BufferedReader br = null;
				
				String line = "";
				String cvsSplitBy = ",";

				try {

					br = new BufferedReader(new FileReader(csvFile));
					while ((line = br.readLine()) != null) {

						// use comma as separator
						String[] country = line.split(cvsSplitBy);
						System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");
						String record = new String();
						for (int i = 0; i < country.length; i++) {
							record = record + " " + country[i];
						}

						list.add(record);

					}
					if (list.size() > 0) {
						csvResultList.setItems(list);
					}

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

			}
		});
	}

}
