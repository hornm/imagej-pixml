package net.imagej.pixml.weka.ops;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.OpService;
import net.imagej.ops.special.hybrid.AbstractUnaryHybridCF;
import net.imagej.pixml.ops.PixmlNamespace.Predict;
import net.imglib2.Cursor;
import net.imglib2.IterableInterval;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import net.imglib2.view.composite.RealComposite;
import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;
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
public class WekaPredictDistr<T extends RealType<T>>
		extends AbstractUnaryHybridCF<IterableInterval<RealComposite<T>>, List<IterableInterval<FloatType>>>
		implements Predict {

	@Parameter
	private Classifier classifier;

	@Parameter
	private Instances instances;

	@Parameter
	private OpService ops;

	@Override
	public void compute1(IterableInterval<RealComposite<T>> featImg, List<IterableInterval<FloatType>> output) {
		
		// count num of features
		int numFeat = (int) StreamSupport.stream(featImg.firstElement().spliterator(), false).count();
		
		double[] featVec = new double[numFeat];
		Instance instance = new DenseInstance(1.0, featVec);
		instance.setDataset(instances);

		Cursor<RealComposite<T>> featCur = featImg.cursor();
		List<Cursor<FloatType>> outCur = output.stream().map(ii -> ii.cursor()).collect(Collectors.toList());
		
		while (featCur.hasNext()) {
			featCur.fwd();
			outCur.forEach(c -> c.fwd());

			int i = 0;
			for (T f : featCur.get()) {
				featVec[i++] = f.getRealDouble();
			}
			try {
				double[] distr = classifier.distributionForInstance(instance);
				for (int j = 0; j < distr.length; j++) {
					outCur.get(j).get().set((float) distr[j]);
				}
			} catch (Exception e) {
				// TODO
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public List<IterableInterval<FloatType>> createOutput(IterableInterval<RealComposite<T>> input) {
		ArrayList<IterableInterval<FloatType>> out = new ArrayList<>(instances.numClasses());
		for (int i = 0; i < instances.numClasses(); i++) {
			out.add(ops.create().<FloatType> imgFactory().create(input, new FloatType()));
		}
		return out;
	}
}
