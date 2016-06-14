package net.imagej.pixml.weka;

import java.util.List;

import org.scijava.plugin.Plugin;

import net.imagej.ops.special.hybrid.BinaryHybridCF;
import net.imagej.pixml.Predictor;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.real.FloatType;
import weka.classifiers.AbstractClassifier;

@Plugin(type = Predictor.class)
public class WekaPredictor implements Predictor<AbstractClassifier> {

	@Override
	public <T extends RealType<T>> BinaryHybridCF<RandomAccessibleInterval<T>, AbstractClassifier, List<RandomAccessibleInterval<FloatType>>> predictDistrOp() {
		// TODO Auto-generated method stub
		return null;
	}

}
