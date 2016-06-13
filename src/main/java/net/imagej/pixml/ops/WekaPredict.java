package net.imagej.pixml.ops;

import org.scijava.plugin.Plugin;

import net.imagej.ops.special.hybrid.AbstractBinaryHybridCF;
import net.imagej.pixml.ops.PixmlNamespace.Predict;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;
import weka.classifiers.AbstractClassifier;

@Plugin(type = Predict.class)
public class WekaPredict<T extends RealType<T>>
		extends AbstractBinaryHybridCF<RandomAccessibleInterval<T>, AbstractClassifier, RandomAccessibleInterval<?>>
		implements Predict {
	
	@Override
	public void compute2(RandomAccessibleInterval<T> input1, AbstractClassifier input2,
			RandomAccessibleInterval<?> output) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public RandomAccessibleInterval<?> createOutput(RandomAccessibleInterval<T> input1, AbstractClassifier input2) {
		// TODO Auto-generated method stub
		return null;
	}


}
