package net.imagej.pixml;

import java.util.Optional;

import org.scijava.options.OptionsService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import com.jcraft.jsch.jce.Random;

import net.imagej.ops.OpMatchingService;
import net.imagej.ops.OpRef;
import net.imagej.ops.OpService;
import net.imagej.ops.special.function.BinaryFunctionOp;
import net.imagej.ops.special.hybrid.BinaryHybridCF;
import net.imagej.pixml.command.WekaClassifierConfig;
import net.imagej.pixml.ops.WekaTrain;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.composite.RealComposite;
import weka.classifiers.AbstractClassifier;

@Plugin(type = Classifier.class)
public class WekaClassifier implements Classifier<AbstractClassifier>, Configurable<WekaClassifierConfig> {

	@Parameter
	private OpService opService;

	private WekaClassifierConfig classifierConfig;

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
		this.classifierConfig = classifierConfig;
	}

	@Override
	public <T extends RealType<T>, L> BinaryFunctionOp<RandomAccessibleInterval<RealComposite<T>>, ImgLabeling<L, ? extends RealType<?>>, AbstractClassifier> trainOp() {
		return (BinaryFunctionOp<RandomAccessibleInterval<RealComposite<T>>, ImgLabeling<L, ? extends RealType<?>>, AbstractClassifier>) opService
				.op(WekaTrain.class, Classifier.class, RandomAccessibleInterval.class, ImgLabeling.class,
						weka.classifiers.Classifier.class, Double.class);
	}

	@Override
	public <T extends RealType<T>> BinaryHybridCF<RandomAccessibleInterval<T>, AbstractClassifier, RandomAccessibleInterval<?>> predictOp() {
		// TODO Auto-generated method stub
		return null;
	}

}
