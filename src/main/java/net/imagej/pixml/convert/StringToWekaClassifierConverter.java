package net.imagej.pixml.convert;

import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.plugin.Plugin;
import org.scijava.prefs.PrefService;

import weka.classifiers.Classifier;

/**
 * Needed to restore a weka classifier from a string, e.g. stored in the imagej
 * preferences via the {@link PrefService}. This, e.g., helps to keep the
 * settings of configuration dialogs. However, not useful yet, since the
 * {@link PrefService} doesn't use converters (from Object to String) to store
 * arbitrary objects (it uses {@link Object#toString()} instead).
 */
@Plugin(type = Converter.class)
public class StringToWekaClassifierConverter extends AbstractConverter<String, Classifier>
		implements Converter<String, Classifier> {

	@Override
	public <T> T convert(Object src, Class<T> dest) {
		return null;
	}

	@Override
	public Class<Classifier> getOutputType() {
		return Classifier.class;
	}

	@Override
	public Class<String> getInputType() {
		return String.class;
	}

}
