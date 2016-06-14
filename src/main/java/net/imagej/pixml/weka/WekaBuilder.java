package net.imagej.pixml.weka;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.OpService;
import net.imagej.ops.special.function.BinaryFunctionOp;
import net.imagej.pixml.Builder;
import net.imagej.pixml.weka.ops.WekaTrain;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.composite.RealComposite;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;

@Plugin(type = Builder.class)
public class WekaBuilder implements Builder<AbstractClassifier> {

	@Parameter
	private OpService ops;

	@Parameter(label = "Weka Classifier")
	private Classifier classifier = new J48();

	@Parameter(label = "Sampling Rate")
	private double samplingRate = 1.0;

	@Parameter(label = "Homogenize Classes")
	private boolean homogenizeClasses;
	
	@Override
	public String toString() {
		return "Weka Classifier Builder";
	}
	
	@Override
	public Class<AbstractClassifier> getModelClass() {
		return AbstractClassifier.class;
	}

	@Override
	public <T extends RealType<T>, L> BinaryFunctionOp<RandomAccessibleInterval<RealComposite<T>>, RandomAccessibleInterval<LabelingType<L>>, AbstractClassifier> buildOp() {
		return (BinaryFunctionOp<RandomAccessibleInterval<RealComposite<T>>, RandomAccessibleInterval<LabelingType<L>>, AbstractClassifier>) ops
				.op(WekaTrain.class, RandomAccessibleInterval.class, RandomAccessibleInterval.class, classifier,
						samplingRate);
	}

}
