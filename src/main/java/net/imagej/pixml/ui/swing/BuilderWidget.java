package net.imagej.pixml.ui.swing;

import org.scijava.plugin.Plugin;
import org.scijava.widget.InputWidget;

import net.imagej.pixml.Builder;

@Plugin(type = InputWidget.class)
public class BuilderWidget extends AbstractSelectConfigWidget<Builder> {

	@Override
	Class<Builder> getObjectClass() {
		return Builder.class;
	}

}
