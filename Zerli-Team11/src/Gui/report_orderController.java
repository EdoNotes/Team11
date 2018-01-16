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
import java.awt.Label;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TreeMap;

import Entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class report_orderController {

	public TreeMap<String, String> directory = new TreeMap<String, String>();
	public String type="";
	@FXML
	BarChart<?,?> BarC;
	@FXML
	CategoryAxis x;
	@FXML
	NumberAxis y;



	public void setdirectory(TreeMap<String, String> d,String type)
	{
		this.directory=d;
		this.type=type;
	}
	
	public void load_dir(TreeMap<String, String> directory)
	{this.directory=directory;
	x.setLabel(this.type);
	
	 XYChart.Series set1 = new XYChart.Series<>();

	 for (String key: directory.keySet())
	 {System.out.println(key+ directory.get(key));
	 set1.getData().add(new XYChart.Data(key,Float.parseFloat(directory.get(key)))); 
	 }
	 BarC.getData().addAll(set1);
	 
	}

}
