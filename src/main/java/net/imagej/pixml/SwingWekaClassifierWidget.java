package net.imagej.pixml;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.scijava.plugin.Plugin;
import org.scijava.ui.swing.widget.SwingInputWidget;
import org.scijava.widget.InputWidget;
import org.scijava.widget.WidgetModel;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.gui.GenericObjectEditor;
import weka.gui.PropertyPanel;

@Plugin(type = InputWidget.class)
public class SwingWekaClassifierWidget extends SwingInputWidget<Classifier> implements WekaClassifierWidget<JPanel> {

	@Override
	public Classifier getValue() {
		return new J48();
	}
	
	@Override
	public void set(WidgetModel model) {
		super.set(model);
		
		GenericObjectEditor classifierEditor = new GenericObjectEditor();
		PropertyPanel cePanel = new PropertyPanel(classifierEditor);
		classifierEditor.setClassType(Classifier.class);
		classifierEditor.setValue(new J48());
		
		getComponent().add(cePanel);
		
		refreshWidget();
		
	}

	@Override
	protected void doRefresh() {
		// TODO Auto-generated method stub
		
	}


}
