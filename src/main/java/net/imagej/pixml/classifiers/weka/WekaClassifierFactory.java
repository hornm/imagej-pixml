package net.imagej.pixml.classifiers.weka;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.OpService;
import net.imagej.pixml.classifiers.Classifier;
import net.imagej.pixml.classifiers.ClassifierFactory;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.trees.J48;

/**
 * Classifier factory that creates image classifiers that make use of weka (
 * {@link weka.classifiers.Classifier}).
 * 
 * @author Martin Horn
 */
@Plugin(type = ClassifierFactory.class)
public class WekaClassifierFactory implements ClassifierFactory {

	@Parameter
	private OpService ops;

	@Parameter(label = "Weka Classifier")
	private weka.classifiers.Classifier classifier = new J48();

	@Parameter(label = "Sampling Rate")
	private double samplingRate = 1.0;

	@Parameter(label = "Homogenize Classes")
	private boolean homogenizeClasses;

	@Override
	public String toString() {
		return "Weka Classifier";
	}

	@Override
	public Classifier createClassifier() {
		try {
			return new WekaClassifier(AbstractClassifier.makeCopy(classifier), samplingRate, ops);
		} catch (Exception e) {
			// TODO
			throw new RuntimeException(e);
		}
	}
}
