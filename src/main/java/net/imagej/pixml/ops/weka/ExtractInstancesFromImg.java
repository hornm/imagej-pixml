package net.imagej.pixml.ops.weka;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.ops.special.function.AbstractBinaryFunctionOp;
import net.imagej.pixml.ops.ExtractInstances;
import net.imglib2.RandomAccess;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.LabelRegion;
import net.imglib2.roi.labeling.LabelRegionCursor;
import net.imglib2.roi.labeling.LabelRegions;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.util.Intervals;
import net.imglib2.view.composite.RealComposite;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

@Plugin(type = ExtractInstances.class)
public class ExtractInstancesFromImg<T extends RealType<T>, L> extends
		AbstractBinaryFunctionOp<RandomAccessibleInterval<RealComposite<T>>, RandomAccessibleInterval<LabelingType<L>>, Instances>
		implements ExtractInstances {

	@Parameter(required = false)
	private double samplingRate = 1.0;

	// TODO: class balancing

	@Override
	public Instances compute2(RandomAccessibleInterval<RealComposite<T>> featImg,
			RandomAccessibleInterval<LabelingType<L>> labeling) {
		if (!Intervals.equalDimensions(featImg, labeling)) {
			throw new IllegalArgumentException("Feature and class image dimensions don't match.");
		}

		RandomAccess<RealComposite<T>> featRA = featImg.randomAccess();
		featRA.setPosition(new long[featImg.numDimensions()]);

		// count num of features
		int numFeat = (int) StreamSupport.stream(featRA.get().spliterator(), false).count();

		LabelRegions<L> regions = new LabelRegions<>(labeling);

		Set<L> labels = regions.getExistingLabels();
		Instances instances = createInstances(numFeat,
				labels.stream().map(l -> l.toString()).collect(Collectors.toList()));
		Random rand = new Random();
		for (L label : labels) {
			LabelRegion<?> region = regions.getLabelRegion(label);
			LabelRegionCursor regCur = region.cursor();
			while (regCur.hasNext()) {
				regCur.fwd();

				if (samplingRate < 1.0 && rand.nextDouble() >= samplingRate) {
					continue;
				}

				featRA.setPosition(regCur);

				double[] featVec = new double[numFeat];
				Iterator<T> it = featRA.get().iterator();
				for (int i = 0; i < numFeat; i++) {
					featVec[i] = it.next().getRealDouble();
				}

				Instance instance = new DenseInstance(1.0, featVec);
				instance.insertAttributeAt(instance.numAttributes());
				instance.setDataset(instances);
				instance.setClassValue(label.toString());
				instances.add(instance);
			}
		}
		return instances;
	}

	private Instances createInstances(int numFeat, List<String> classLabels) {
		// build training set
		ArrayList<Attribute> attr = new ArrayList<Attribute>();
		for (int a = 0; a < numFeat; a++) {
			attr.add(new Attribute("attr" + a));
		}
		Instances instances = new Instances("data", attr, classLabels.size() * 20);

		instances.insertAttributeAt(new Attribute("class", classLabels), instances.numAttributes());
		instances.setClassIndex(instances.numAttributes() - 1);
		return instances;
	}

}
