package net.imagej.pixml.weka;

import java.util.List;

import net.imagej.ops.OpService;
import net.imagej.ops.special.hybrid.UnaryHybridCF;
import net.imagej.pixml.Classifier;
import net.imagej.pixml.weka.ops.WekaTrain;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.composite.RealComposite;

public class WekaClassifier implements Classifier {

	private weka.classifiers.Classifier wekaClassifier;
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
		ops.run(WekaTrain.class, features, labels, wekaClassifier,
				samplingRate);
	}

	@Override
	public <T extends RealType<T>> UnaryHybridCF<RandomAccessibleInterval<T>, List<RandomAccessibleInterval<FloatType>>> predictDistrOp() {
		// TODO Auto-generated method stub
		return null;
	}

}
