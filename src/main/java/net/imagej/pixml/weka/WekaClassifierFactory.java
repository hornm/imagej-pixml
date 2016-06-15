package net.imagej.pixml.weka;

import java.util.List;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.OpService;
import net.imagej.ops.special.function.BinaryFunctionOp;
import net.imagej.ops.special.hybrid.BinaryHybridCF;
import net.imagej.pixml.Classifier;
import net.imagej.pixml.ClassifierFactory;
import net.imagej.pixml.weka.ops.WekaTrain;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.composite.RealComposite;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.trees.J48;

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
