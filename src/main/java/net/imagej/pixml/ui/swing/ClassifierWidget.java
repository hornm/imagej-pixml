package net.imagej.pixml.ui.swing;

import org.scijava.plugin.Plugin;
import org.scijava.widget.InputWidget;

import net.imagej.pixml.Classifier;

@Plugin(type = InputWidget.class)
public class ClassifierWidget extends AbstractSelectConfigWidget<Classifier> {

	@Override
	Class<Classifier> getObjectClass() {
		return Classifier.class;
	}

}
