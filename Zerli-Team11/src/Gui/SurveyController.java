/************************************************************************** 
 * Copyright (©) Zerli System 2017-2018 - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Edo Notes <Edoono24@gmail.com>
 * 			  Tomer Arzuan <Tomerarzu@gmail.com>
 * 			  Matan Sabag <19matan@gmail.com>
 * 			  Ido Kalir <idotehila@gmail.com>
 * 			  Elinor Faddoul<elinor.faddoul@gmail.com
 **************************************************************************/
package Gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

public class SurveyController implements Initializable
{
	
	@FXML
	ComboBox cmbSelectAnswer1;
	@FXML
	ComboBox cmbSelectAnswer2;
	
	ObservableList<String> OneToTen=FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10");
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		cmbSelectAnswer1.setItems(OneToTen);
		cmbSelectAnswer2.setItems(OneToTen);
	}

}
