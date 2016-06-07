package net.imagej.pixml;

import javax.swing.JPanel;

import org.scijava.plugin.Plugin;
import org.scijava.ui.swing.widget.SwingInputWidget;
import org.scijava.widget.InputWidget;
import org.scijava.widget.WidgetModel;

import weka.classifiers.Classifier;
import weka.gui.GenericObjectEditor;
import weka.gui.PropertyPanel;

@Plugin(type = InputWidget.class)
public class SwingWekaClassifierWidget extends SwingInputWidget<Classifier> implements WekaClassifierWidget<JPanel> {

	GenericObjectEditor classifierEditor = new GenericObjectEditor();

	@Override
	public Classifier getValue() {
		return (Classifier) classifierEditor.getValue();
	}

	@Override
	public void set(WidgetModel model) {
		super.set(model);

		PropertyPanel cePanel = new PropertyPanel(classifierEditor);
		classifierEditor.setClassType(Classifier.class);
		getComponent().add(cePanel);
		refreshWidget();
	}

	@Override
	protected void doRefresh() {
		classifierEditor.setValue((Classifier) get().getValue());
	}

	@Override
	public boolean supports(final WidgetModel model) {
		return super.supports(model) && model.isType(Classifier.class);
	}

}
