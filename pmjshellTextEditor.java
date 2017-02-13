// This text editor is developed by Pratik Mallya
// A javafx programe for PM_JSHELL text editor
    
import javafx.scene.control.*; 
import javafx.scene.layout.*;
import javafx.scene.web.HTMLEditor;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.application.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.*;
import java.util.*;
import java.io.*;
import java.lang.*;

// *******************************class for creating new tab begins ************************************
class textEditorUtilities
{

	
public void createNewWindow(String filePath,boolean isFileToBeOpened,TextArea text)
{
	try {
if(!isFileToBeOpened)
{
Runtime.getRuntime().exec("javac pmjshellTextEditor.java");
Runtime.getRuntime().exec("java pmjshellTextEditor");	
}	
else
{
	BufferedReader br = new BufferedReader(new FileReader(filePath));
    String line=new String();
	String textAreaContent=new String();
	textAreaContent="";
	line="";
	/*if(text!=null)
	{
		Runtime.getRuntime().exec("javac pmjshellTextEditor.java");
Runtime.getRuntime().exec("java pmjshellTextEditor");	
		
    while ((line=br.readLine()) != null) {
		if(textAreaContent.equals(""))
			textAreaContent=textAreaContent+line;
		else
			textAreaContent=textAreaContent+"\n"+line;
        text.setText(textAreaContent);
    }
		
	}
	else
	{*/
	
    while ((line=br.readLine()) != null) {
		if(textAreaContent.equals(""))
			textAreaContent=textAreaContent+line;
		else
			textAreaContent=textAreaContent+"\n"+line;
        text.setText(textAreaContent);
    }
	//}
	textAreaContent="";
	line="";
}
	}
	catch(Exception e){}
}

public File openOrSaveAsFile(boolean isOpenFile,Stage stage)
{
FileChooser fileChooser = new FileChooser();
if(isOpenFile)
{
fileChooser.setTitle("Open a file");
return fileChooser.showOpenDialog(stage);
}
else
{
fileChooser.setTitle("Save a file");
return fileChooser.showSaveDialog(stage);
}
}


public void saveFile(String filePath,TextArea text)
{
try {
File file=new File(filePath);
	file.createNewFile();
	 BufferedWriter bf = new BufferedWriter(new FileWriter(file));
            bf.write(text.getText());
            bf.flush();
            bf.close();
	
}
catch(Exception e) {}
}
	
public void changeStageTitle(String filePath,Stage stage)
{
try {
	stage.setTitle("PM-JSHELL Editor ( "+filePath+")");
	
}	
	catch(Exception e) {}

}
	


public void changeFontSize(TextArea textArea,String size)
{
try {
	textArea.setStyle("-fx-font-size:"+size+"px;");
}
catch(Exception e) {}	
}
	
	public void replaceText(TextArea text,String type)
	{
	try {
		    if (text.getText().length()>0)
			{
				if(type.equals("Replace"))
				{
				Label response=new Label();
			Stage replaceDialogStage = new Stage();
           replaceDialogStage.setTitle("PM-JSHELL Editor Replace");
			
			Label findLabel=new Label("Find:");
			Label replaceLabel=new Label("Replace with:");
			TextField findTextField=new TextField();
			TextField replaceTextField=new TextField();
			
			Button replaceFirstButton=new Button("Replace First");
			Button replaceAllButton=new Button("Replace All");
			
			GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 10, 10, 10));
			
			grid.add(findLabel, 0, 0);
            grid.add(findTextField, 1, 0);
            grid.add(replaceLabel, 0, 1);
            grid.add(replaceTextField, 1, 1);
			grid.add(replaceFirstButton, 0, 2);
			grid.add(replaceAllButton, 1, 2);
			grid.add(response, 1,3 );
			
		Scene replaceDailogScene=new Scene(grid,320,150);
		replaceDialogStage.setScene(replaceDailogScene);
		replaceDialogStage.show();
			
			
			replaceFirstButton.setOnAction((ae) ->
										   { if(text.getText().contains(findTextField.getText()))
										   {
										       response.setText("Word/text found");
											   String textToBeReplaced=new String();
		                                        String replacedBy=new String();
			                                 textToBeReplaced=findTextField.getText();
											replacedBy= replaceTextField.getText();
											text.setText(text.getText().replaceFirst(textToBeReplaced,replacedBy));   
											}
											else
											response.setText("Sorry, word/text not found");
										   }
			
			);
			
			replaceAllButton.setOnAction((ae) ->
										   {
											   if(text.getText().contains(findTextField.getText()))
										   {
										         response.setText("Word/text found");
											   String textToBeReplaced=new String();
		                                        String replacedBy=new String();
			                                 textToBeReplaced=findTextField.getText();
											replacedBy= replaceTextField.getText();
											text.setText(text.getText().replaceAll(textToBeReplaced,replacedBy));   
											}
											else
											response.setText("Sorry, word/text not found");
										   }
			
			);
			
			
			
				}
				
				
					
			}
		
		
	}
		catch(Exception e) {}
	}
	
	public boolean saveBeforeClosing(TextArea text,Stage stage)
	{
		boolean done=false;
		try {
			 
		Stage saveBeforeClosingDialog=new Stage();
		saveBeforeClosingDialog.setTitle("PM-JSHELL Alert !!");
		GridPane grid=new GridPane();
		Scene saveBeforeClosingScene=new Scene(grid,270,100);
		 grid.setHgap(4);
            grid.setVgap(10);
            grid.setPadding(new Insets(8, 8, 8, 8));
		Label saveBeforeClosingLabel=new Label("Save changes ?");
		Button buttonYes=new Button("Yes");
		Button buttonNo=new Button("No");
		grid.add(saveBeforeClosingLabel,0,0);
		grid.add(buttonYes,0,1);
		grid.add(buttonNo,1,1);
		saveBeforeClosingDialog.setScene(saveBeforeClosingScene);
		
		buttonNo.setOnAction((ae)->
							  {
								 
								  Platform.exit();
								  
							  });
		
		
		if(!stage.getTitle().contains("(")&&text.getText().length()>0)
		{
			saveBeforeClosingDialog.show();
			buttonYes.setOnAction((ae)->
							  {
								saveFile(openOrSaveAsFile(false,stage).getAbsolutePath(),text);
								
								  Platform.exit();
								  
							  }
		
		);
		}
		
		else if(stage.getTitle().contains("("))
		{
			String filePath=stage.getTitle().substring(18,stage.getTitle().length()-1);
			BufferedReader br = new BufferedReader(new FileReader(filePath));
    String line=new String();
	String textAreaContent=new String();
			 while ((line=br.readLine()) != null) {
		if(textAreaContent.equals(""))
			textAreaContent=textAreaContent+line;
		else
			textAreaContent=textAreaContent+"\n"+line;
        
    }
			
		if(textAreaContent.length()!=text.getText().length())
		{
		saveBeforeClosingDialog.show();	
			buttonYes.setOnAction((ae)->
							  {
								saveFile(stage.getTitle().substring(18,stage.getTitle().length()-1),text);
								  
								  Platform.exit();
								  
							  } );
		}
		}
		else
		{
		Platform.exit(); 
		}
			
		}
		catch(Exception e) {}
		done=true;
		
		
			return done;
			
  }
  
public void changeFontStyle(TextArea text,String weight)
{
try {
if(weight.contains("bold"))
text.setStyle("-fx-font-weight:"+weight+";");
	else if(weight.contains("italic"))
		text.setStyle("-fx-font-style:"+weight+";");
		else
		text.setStyle("-fx-text-decoration:"+weight+";");

}
catch(Exception e)
{}
}
	

public void settingPreference(TextArea text)
{
Stage settingPreferenceStage=new Stage();
settingPreferenceStage.setTitle("PM-JSHELL Preferences");

GridPane grid =new GridPane();
	 grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(10, 10, 10, 10));

Scene settingPreferenceScene=new Scene(grid,300,100);

Label fontNameLabel=new Label("Font :");
Label fontStyleLabel=new Label("Font style :");

	
	RadioButton fontStyleBold=new RadioButton("Bold");
	RadioButton fontStyleItalic=new RadioButton("Italic");
	
	
 
 grid.add(fontStyleLabel,0,0);
 grid.add(fontStyleBold,1,0);
 grid.add(fontStyleItalic,1,1);

 
 settingPreferenceStage.setScene(settingPreferenceScene);
 settingPreferenceStage.show();
	
	EventHandler<ActionEvent>FontStyleEventhandler  = new EventHandler<ActionEvent>() {
         public void handle(ActionEvent ae) 
        {
			 String selectedStyle=((RadioButton)ae.getTarget()).getText();
			 
			 
		changeFontStyle(text,selectedStyle.toLowerCase());
		}
	};
 
	fontStyleBold.setOnAction(FontStyleEventhandler);
	fontStyleItalic.setOnAction(FontStyleEventhandler);
   
	
 
	

	
	
}
		
}
							 
							


// *******************************class for creating new tab ends **************************************

public class pmjshellTextEditor extends Application
{
textEditorUtilities pmjshellTextEditorUtilities=new textEditorUtilities();
	 boolean isSaved=false;
	File fileName=new File("");
	 TextArea textEditor = new TextArea();
	 TextArea lineNumberArea=new TextArea();
	 
	int lineNo=1;
// ****************************************class body begins ****************************************************
	
// ***************************************main() begins *********************************************************
public static void main(String args[]) 
{
launch(args);
}	
// ***************************************main() ends ***********************************************************

	
public void start(Stage textEditorStage) // starting application
{
	lineNumberArea.setVisible(false);
    textEditorStage.setTitle("PM-JSHELL Editor"); //setting stage title
	FlowPane root=new FlowPane();
	BorderPane pmjshell=new BorderPane();
	
	Scene textEditorScene=new Scene(pmjshell,750,520); //creating scene
	
	textEditorStage.setScene(textEditorScene); //setting scene on stage
	
	// ****************************************setting menu bar begins ******************************************
	
	MenuBar textEditorMenuBar=new MenuBar(); // creating menubar object
        
      Menu menuFile=new Menu("File"); // creating menu objcet file  
	  Menu menuEdit=new Menu("Edit"); // creating menu objcet edit 
      Menu menuTool=new Menu("Tools"); // creating menu objcet tools 
      Menu menuSetting=new Menu("Settings"); // creating menu objcet setting
      Menu menuHelp=new Menu("Help ?"); // creating menu objcet setting
    
      MenuItem menuFileNew=new MenuItem("New"); //creating menuitem new for file menu
      MenuItem menuFileOpen=new MenuItem("Open"); //creating menuitem open for file menu
      MenuItem menuFileSave=new MenuItem("Save"); //creating menuitem save for file menu
      MenuItem menuFileSaveAs=new MenuItem("Save As"); //creating menuitem saveas for file menu
      MenuItem menuFileExit=new MenuItem("Exit"); //creating menuitem Exit for file menu
     
      MenuItem menuEditCut=new MenuItem("Cut"); //creating menuitem cut for edit menu
      MenuItem menuEditCopy=new MenuItem("Copy"); //creating menuitem copy for edit menu
      MenuItem menuEditPaste=new MenuItem("Paste"); //creating menuitem paste for edit menu
      MenuItem menuEditSelectAll=new MenuItem("Select All"); //creating menuitem selectall for edit menu
      
    
	  MenuItem menuToolReplace=new MenuItem("Replace"); //creating menuitem replace for tools menu
      
      MenuItem menuSettingPreference=new MenuItem("Preferences..."); //creating menuitem preference for setting menu
      
      
    
     menuSetting.getItems().add(menuSettingPreference); //adding menuitems to setting menu
    menuTool.getItems().addAll(menuToolReplace); //adding menuitems to search menu
    menuEdit.getItems().addAll(menuEditCut,menuEditCopy,menuEditPaste,menuEditSelectAll);//adding menuitems to edit menu
    menuFile.getItems().addAll(menuFileNew,menuFileOpen,menuFileSave,menuFileSaveAs,new SeparatorMenuItem(),menuFileExit); //adding menuitems to file menu
    
    textEditorMenuBar.getMenus().addAll(menuFile,menuEdit,menuTool,menuSetting,menuHelp); // adding menus to menubar
    
	//pmjshell.getChildren().add(textEditorMenuBar); // adding menubar to scene
	pmjshell.setTop(textEditorMenuBar); // setting menu bar on top
       
      EventHandler<ActionEvent> menuItemEventHandler = new EventHandler<ActionEvent>() {
         public void handle(ActionEvent ae) 
        {
        String selectedItem = ((MenuItem)ae.getTarget()).getText();
       
			 
        if(selectedItem.equals("Exit")) 
		{
			boolean status=false;
		 status=pmjshellTextEditorUtilities.saveBeforeClosing(textEditor,textEditorStage);	
        if(textEditor.getText().length()==0)
			Platform.exit();
		}
        else if(selectedItem.equals("New"))
      pmjshellTextEditorUtilities.createNewWindow("",false,textEditor);
        else if(selectedItem.equals("Open"))
		{
        fileName=pmjshellTextEditorUtilities.openOrSaveAsFile(true,textEditorStage);
			pmjshellTextEditorUtilities.createNewWindow(fileName.getAbsolutePath(),true,textEditor);
			pmjshellTextEditorUtilities.changeStageTitle(fileName.getAbsolutePath(),textEditorStage);
		}
			else if(selectedItem.equals("Save As"))
		{
			 fileName=pmjshellTextEditorUtilities.openOrSaveAsFile(false,textEditorStage);
			pmjshellTextEditorUtilities.saveFile(fileName.getAbsolutePath(),textEditor);
		isSaved=true;
				pmjshellTextEditorUtilities.changeStageTitle(fileName.getAbsolutePath(),textEditorStage);
		}
			else if(selectedItem.equals("Save"))
			{
			if(isSaved)
			pmjshellTextEditorUtilities.saveFile(fileName.getAbsolutePath(),textEditor);
			else
			{
			fileName=pmjshellTextEditorUtilities.openOrSaveAsFile(false,textEditorStage);
			pmjshellTextEditorUtilities.saveFile(fileName.getAbsolutePath(),textEditor);
		isSaved=true;	
			}
			pmjshellTextEditorUtilities.changeStageTitle(fileName.getAbsolutePath(),textEditorStage);		
			}
         else if(selectedItem.equals("Cut"))
         textEditor.cut();
         else if(selectedItem.equals("Copy"))
         textEditor.copy();
         else if(selectedItem.equals("Paste"))
         textEditor.paste();
         else if(selectedItem.equals("Select All"))
         textEditor.selectAll();
			 
	     else if(selectedItem.equals("Find"))
		 pmjshellTextEditorUtilities.replaceText(textEditor,selectedItem);	
	     else if(selectedItem.equals("Replace"))
		pmjshellTextEditorUtilities.replaceText(textEditor,selectedItem);
			 else if(selectedItem.equals("Preferences..."))
			 pmjshellTextEditorUtilities.settingPreference(textEditor);
			 else {}
		
         
			 
       }
       };
	
	

         menuFileOpen.setOnAction(menuItemEventHandler);
         menuFileNew.setOnAction(menuItemEventHandler);
         menuFileSave.setOnAction(menuItemEventHandler);
         menuFileSaveAs.setOnAction(menuItemEventHandler);
         menuFileExit.setOnAction(menuItemEventHandler);
         menuEditCut.setOnAction(menuItemEventHandler);
         menuEditCopy.setOnAction(menuItemEventHandler);
         menuEditPaste.setOnAction(menuItemEventHandler);
         menuEditSelectAll.setOnAction(menuItemEventHandler);
        
	     menuToolReplace.setOnAction(menuItemEventHandler);
         menuSettingPreference.setOnAction(menuItemEventHandler);
         
    
	// ****************************************setting menu bar ends ********************************************
    
    
    // ****************************************setting toolbar menu begins ****************************************
     ToolBar textEditorToolBar=new ToolBar();
	
	Button toolBarButtonNew=new Button("+");
	  Button toolBarButtonSave=new Button("Save");
	 Button toolBarButtonUndo=new Button("Undo");
	 Button toolBarButtonRedo=new Button("Redo");
	
	
	
	Label comboBoxLabelFontSize=new Label("Font Size:");
	ObservableList<String> comboBoxFontSizeList = FXCollections.observableArrayList("11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30");
	ComboBox<String>toolBarComboBoxFontSize=new ComboBox<String>(comboBoxFontSizeList);
	toolBarComboBoxFontSize.setValue("12");
	toolBarComboBoxFontSize.setPrefWidth(65);
	
	toolBarComboBoxFontSize.setEditable(true);
	textEditorToolBar.getItems().addAll(new Separator(),toolBarButtonNew,toolBarButtonSave,toolBarButtonUndo,toolBarButtonRedo,new Separator(),comboBoxLabelFontSize,toolBarComboBoxFontSize);
    
	pmjshell.setBottom(textEditorToolBar);
	
	EventHandler<ActionEvent> toolBarEventHandler = new EventHandler<ActionEvent>() {
         public void handle(ActionEvent ae) 
        {
        String selectedTool = ((Button)ae.getTarget()).getText();
		if(selectedTool.equals("+"))
		 pmjshellTextEditorUtilities.createNewWindow("",false,textEditor);
		else if(selectedTool.equals("Save"))
		{
		if(isSaved)
			pmjshellTextEditorUtilities.saveFile(fileName.getAbsolutePath(),textEditor);
			else
			{
			fileName=pmjshellTextEditorUtilities.openOrSaveAsFile(false,textEditorStage);
			pmjshellTextEditorUtilities.saveFile(fileName.getAbsolutePath(),textEditor);
		isSaved=true;	
			}
			pmjshellTextEditorUtilities.changeStageTitle(fileName.getAbsolutePath(),textEditorStage);			
		}
			else if(selectedTool.equals("Undo"))
				textEditor.undo();
			else if(selectedTool.equals("Redo"))
				textEditor.redo();
			
			 else {}
		 }
	};
	
	
	
	EventHandler<ActionEvent> toolBarFontSizeEventHandler = new EventHandler<ActionEvent>() {
         public void handle(ActionEvent ae) 
        {
			 String selectedFontSize=toolBarComboBoxFontSize.getValue();
			 int val=Integer.parseInt(selectedFontSize);
			 if(val>=11&&val<=40)
			 pmjshellTextEditorUtilities.changeFontSize(textEditor,selectedFontSize);
			 
		 }
	};
	
	
	toolBarButtonNew.setOnAction(toolBarEventHandler);
	toolBarButtonSave.setOnAction(toolBarEventHandler);
	toolBarButtonUndo.setOnAction(toolBarEventHandler);
	toolBarButtonRedo.setOnAction(toolBarEventHandler);
	
	toolBarComboBoxFontSize.setOnAction(toolBarFontSizeEventHandler);
	
    // ****************************************setting toolbar menu ends ******************************************
	
	
   // **************************************** creating tab pane begins *****************************************
	
	 pmjshell.setCenter(textEditor);
	/* lineNumberArea.setPrefWidth(55);
	 pmjshell.setLeft(lineNumberArea);
	 
	  lineNumberArea.setText(String.valueOf(lineNo)+"\n");
	  
	  textEditor.setOnKeyPressed(new EventHandler<KeyEvent>() 
{
    @Override
    public void handle(KeyEvent keyEvent) 
    {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
           lineNumberArea.setText(lineNumberArea.getText()+String.valueOf(++lineNo)+"\n"); 
        }
    }
});
lineNumberArea.setEditable(false);*/
 /*textEditor.setOnKeyPressed(new EventHandler<KeyEvent>() {
        public void handle(KeyEvent backspaceEvent) {
            if (backspaceEvent.getCode() == KeyCode.BACK_SPACE) {
				lineNumberArea.setEditable(true);
                lineNumberArea.getText().replaceFirst(String.valueOf(lineNumberArea.getText().split("\n").length),"");
				lineNumberArea.setEditable(false);
            }
        }
    });*/

	
	
   // **************************************** creating tab pne ends ********************************************
	
	Platform.runLater(new Runnable() {
    @Override
    public void run() {
        textEditor.requestFocus();
    }
});
	
       textEditorStage.show(); // displaying stage
	Platform.setImplicitExit(false);
	 textEditorStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          public void handle(WindowEvent event) {
			
			  
			  
             event.consume();
				Platform.setImplicitExit(pmjshellTextEditorUtilities.saveBeforeClosing(textEditor,textEditorStage));
			               
			 
               

			  
          }
      }); 
}


// ****************************************class body ends ******************************************************
}
// *****************************************programme ends *******************************************************
