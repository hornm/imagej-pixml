package net.imagej.pixml.ui.swing;

import org.scijava.plugin.Plugin;
import org.scijava.widget.InputWidget;

import net.imagej.pixml.classifiers.ClassifierFactory;

@Plugin(type = InputWidget.class)
public class ClassifierFactoryWidget extends AbstractSelectConfigWidget<ClassifierFactory> {

	@Override
	Class<ClassifierFactory> getObjectClass() {
		return ClassifierFactory.class;
	}

}
