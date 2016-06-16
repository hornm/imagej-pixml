package net.imagej.pixml.weka;

import java.util.List;

import net.imagej.ops.OpService;
import net.imagej.ops.special.hybrid.UnaryHybridCF;
import net.imagej.pixml.Classifier;
import net.imagej.pixml.ops.PixmlNamespace.Predict;
import net.imagej.pixml.weka.ops.ExtractInstances;
import net.imagej.pixml.weka.ops.WekaPredictDistr;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.composite.RealComposite;
import weka.core.Instances;

public class WekaClassifier implements Classifier {

	private weka.classifiers.Classifier wekaClassifier;

	// we have to keep the instances for the prediction
	private Instances instances;

	private double samplingRate;

	private OpService ops;

	WekaClassifier(weka.classifiers.Classifier wekaClassifier, double samplingRate, OpService ops) {
		this.wekaClassifier = wekaClassifier;
		this.samplingRate = samplingRate;
		this.ops = ops;

	}

	@Override
	public <T extends RealType<T>, L> void build(RandomAccessibleInterval<RealComposite<T>> features,
			RandomAccessibleInterval<LabelingType<L>> labels) {
		instances = (Instances) ops.run(ExtractInstances.class, features, labels, samplingRate);
		try {
			wekaClassifier.buildClassifier(instances);
			instances.clear();
		} catch (Exception e) {
			// TODO
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T extends RealType<T>> UnaryHybridCF<IterableInterval<T>, List<IterableInterval<FloatType>>> predictDistrOp() {
		return ops.op(WekaPredictDistr.class, IterableInterval.class, IterableInterval.class,
				wekaClassifier, instances);
	}

}
