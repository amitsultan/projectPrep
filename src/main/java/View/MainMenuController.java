package View;

//import ViewModel.ViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.util.*;

/**
 * Class in charge of controlling the gui
 */
public class MainMenuController {

    public TextField tf_corpusPath;
    public TextField tf_postingPath;
    public TextField tf_OptionA;
    public TextField tf_OptionB;
    public TextField tf_saveAns;
    public CheckBox cb_stem;
    public CheckBox cb_semantic;
    public Pane p_first;
    public Pane p_second;
    public Pane p_dictionary;
    public Pane p_Query;
    public Pane p_Options;
    public Pane p_Answers;
    public TextArea c_Posting;
    public TextArea c_docsAndEnt;
    public ChoiceBox<String> ch_queryOp;
    public ChoiceBox<String> ch_queries;


    //private ViewModel viewModel;
    private boolean isUploaded;
    private String postPath;
    private boolean isStem;
    private String corpusPath;

    /**
     * Method that initializing the view model to the view
     */
//    public void initialize(ViewModel model, Stage primaryS, Scene scene){
//        //this.viewModel = model;
//        isUploaded = false;
//    }




}