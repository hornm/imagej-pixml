package net.imagej.pixml.weka.ops;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.scijava.plugin.Plugin;

import net.imagej.ops.special.hybrid.AbstractBinaryHybridCF;
import net.imagej.pixml.ops.PixmlNamespace;
import net.imagej.pixml.ops.PixmlNamespace.Predict;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.composite.RealComposite;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.Instances;

/**
 * TODO
 * 
 * Performs a prediction by using a weka {@link Classifier} and returning a
 * distribution (i.e. as list of images).
 * 
 * @author Martin Horn
 *
 * @param <T>
 */
@Plugin(type = Predict.class)
public class WekaPredictDistr<T extends RealType<T>> extends
		AbstractBinaryHybridCF<RandomAccessibleInterval<RealComposite<T>>, AbstractClassifier, List<RandomAccessibleInterval<FloatType>>>
		implements Predict {

	@Override
	public void compute2(RandomAccessibleInterval<RealComposite<T>> featImg, AbstractClassifier classifier,
			List<RandomAccessibleInterval<FloatType>> output) {
		RandomAccess<RealComposite<T>> featRA = featImg.randomAccess();
		featRA.setPosition(new long[featImg.numDimensions()]);

		// count num of features
		int numFeat = (int) StreamSupport.stream(featRA.get().spliterator(), false).count();
		Instances instances = createInstances(numFeat, null);
	}

	@Override
	public List<RandomAccessibleInterval<FloatType>> createOutput(RandomAccessibleInterval<RealComposite<T>> input1,
			AbstractClassifier input2) {
		return null;
	}
	
	private Instances createInstances(int numFeat, List<String> classLabels) {
		// build training set
		ArrayList<Attribute> attr = new ArrayList<Attribute>();
		for (int a = 0; a < numFeat; a++) {
			attr.add(new Attribute("attr" + a));
		}
		Instances instances = new Instances("data", attr, 0);

		instances.insertAttributeAt(new Attribute("class", classLabels), instances.numAttributes());
		instances.setClassIndex(instances.numAttributes() - 1);
		return instances;
	}

}
