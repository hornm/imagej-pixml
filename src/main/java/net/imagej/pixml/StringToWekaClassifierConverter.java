package net.imagej.pixml;

import org.scijava.convert.AbstractConverter;
import org.scijava.convert.Converter;
import org.scijava.plugin.Plugin;

import weka.classifiers.Classifier;

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
