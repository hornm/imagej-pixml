package net.imagej.pixml.ui.swing;

import org.scijava.plugin.Plugin;
import org.scijava.widget.InputWidget;

import net.imagej.pixml.Predictor;

@Plugin(type = InputWidget.class)
public class PredictorWidget extends AbstractSelectConfigWidget<Predictor> {

	@Override
	Class<Predictor> getObjectClass() {
		return Predictor.class;
	}

}
