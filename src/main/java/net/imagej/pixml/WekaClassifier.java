package net.imagej.pixml;

import java.util.List;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.OpService;
import net.imagej.ops.special.function.BinaryFunctionOp;
import net.imagej.ops.special.hybrid.BinaryHybridCF;
import net.imagej.pixml.command.WekaClassifierConfig;
import net.imagej.pixml.ops.WekaTrain;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.composite.RealComposite;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.trees.J48;

@Plugin(type = Classifier.class)
public class WekaClassifier implements Classifier<AbstractClassifier>, Configurable<WekaClassifierConfig> {

	@Parameter
	private OpService opService;

	private weka.classifiers.Classifier wekaClassifier = new J48();

	private double samplingRate = 0.1;

	@Override
	public String toString() {
		return "Weka Classifier";
	}

	@Override
	public Class<WekaClassifierConfig> getConfigCommandClass() {
		return WekaClassifierConfig.class;
	}

	@Override
	public void configure(WekaClassifierConfig classifierConfig) {
		wekaClassifier = classifierConfig.getClassifier();
		samplingRate = classifierConfig.getSamplingRate();
	}

	@Override
	public <T extends RealType<T>, L> BinaryFunctionOp<RandomAccessibleInterval<RealComposite<T>>, RandomAccessibleInterval<LabelingType<L>>, AbstractClassifier> trainOp() {
		return (BinaryFunctionOp<RandomAccessibleInterval<RealComposite<T>>, RandomAccessibleInterval<LabelingType<L>>, AbstractClassifier>) opService
				.op(WekaTrain.class, RandomAccessibleInterval.class, RandomAccessibleInterval.class, wekaClassifier,
						samplingRate);
	}
	
	@Override
	public <T extends RealType<T>> BinaryHybridCF<RandomAccessibleInterval<T>, AbstractClassifier, List<RandomAccessibleInterval<FloatType>>> predictDistrOp() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean canHandle(Object model) {
		return model instanceof weka.classifiers.Classifier;
	}

}
