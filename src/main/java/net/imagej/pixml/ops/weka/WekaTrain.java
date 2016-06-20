package net.imagej.pixml.ops.weka;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.OpService;
import net.imagej.ops.special.function.AbstractBinaryFunctionOp;
import net.imagej.pixml.ops.ExtractInstances;
import net.imagej.pixml.ops.PixmlNamespace;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.composite.RealComposite;
import weka.classifiers.Classifier;
import weka.core.Instances;

@Plugin(type = PixmlNamespace.Train.class)
public class WekaTrain<T extends RealType<T>, L> extends
		AbstractBinaryFunctionOp<RandomAccessibleInterval<RealComposite<T>>, RandomAccessibleInterval<LabelingType<L>>, Classifier>
		implements PixmlNamespace.Train {

	@Parameter
	private Classifier classifier;

	@Parameter(required = false)
	private double samplingRate = 1.0;

	@Parameter
	private OpService ops;

	// TODO: class balancing

	@Override
	public Classifier compute2(RandomAccessibleInterval<RealComposite<T>> featImg,
			RandomAccessibleInterval<LabelingType<L>> classes) {

		Instances instances = (Instances) ops.run(ExtractInstances.class, featImg, classes, samplingRate);

		try {
			classifier.buildClassifier(instances);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classifier;
	}

}
