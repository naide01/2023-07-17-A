package it.polito.tdp.gosales;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.gosales.model.*;
import it.polito.tdp.gosales.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCercaPercorso;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<Integer> cmbAnno;

    @FXML
    private ComboBox<String> cmbColore;

    @FXML
    private ComboBox<?> cmbProdotto;

    @FXML
    private TextArea txtArchi;

    @FXML
    private TextArea txtResGrafo;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCercaPercorso(ActionEvent event) {

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResGrafo.clear();
    	String colore = this.cmbColore.getValue();
    	Integer anno = this.cmbAnno.getValue();
    	if (colore == null) {
    		this.txtResGrafo.setText("Scegliere un colore dalla tendina! \n");
    		return;
    	}
    	if (anno == null) {
    		this.txtResGrafo.setText("Scegliere un anno dalla tendina! \n");
    		return;
    	}
    	this.model.creaGrafo(anno, colore);
    	this.txtResGrafo.appendText("Grafo correttamente creato! \n");
    	this.txtResGrafo.appendText("# vertici: " + this.model.numV() + "\n");
    	this.txtResGrafo.appendText("# archi: " + this.model.numA() + "\n");
    	
    	List<Arco> archi = this.model.archi(anno, colore);
    	List<Arco> subList = new ArrayList<Arco>(archi.subList(0, 3));
    	List<String> nodiRip = new ArrayList<String>();
    	List<String> result = new ArrayList<String>();
    	for (Arco a : subList) {
    		this.txtArchi.appendText(a + "\n");
    	}
    	for (Arco a: subList) {
			if (nodiRip.contains(a.getP1().getNumber() + "")) {
				result.add(a.getP1().getNumber() + "");
				}
			nodiRip.add(a.getP1().getNumber() + "");
			if (nodiRip.contains(a.getP2().getNumber() + "")) {
				result.add(a.getP2().getNumber() + "");
			}
			nodiRip.add(a.getP2().getNumber() + "");
			}
    	this.txtArchi.appendText("Nodi ripetuti: " + result.toString() + "\n");
    	}
    

    @FXML
    void initialize() {
        assert btnCercaPercorso != null : "fx:id=\"btnCercaPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbColore != null : "fx:id=\"cmbColore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProdotto != null : "fx:id=\"cmbProdotto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtArchi != null : "fx:id=\"txtArchi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResGrafo != null : "fx:id=\"txtResGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cmbAnno.getItems().setAll(this.model.getAnno());
    	this.cmbColore.getItems().setAll(this.model.getColore());
    }

}
