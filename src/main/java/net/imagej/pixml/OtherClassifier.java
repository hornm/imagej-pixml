package net.imagej.pixml;

import java.io.Serializable;

import org.scijava.plugin.Plugin;

import net.imagej.ops.OpRef;
import net.imagej.ops.special.function.BinaryFunctionOp;
import net.imagej.ops.special.hybrid.BinaryHybridCF;
import net.imagej.pixml.OtherClassifier.MyModel;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.composite.RealComposite;

@Plugin(type = Classifier.class)
public class OtherClassifier implements Classifier<MyModel> {

	public static class MyModel implements Serializable {

	}

	@Override
	public String toString() {
		return "Other Classifier";
	}

	@Override
	public <T extends RealType<T>, L> BinaryFunctionOp<RandomAccessibleInterval<RealComposite<T>>, ImgLabeling<L, ? extends RealType<?>>, MyModel> trainOp() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends RealType<T>> BinaryHybridCF<RandomAccessibleInterval<T>, MyModel, RandomAccessibleInterval<?>> predictOp() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean canHandle(Object model) {
		return model instanceof MyModel;
	}


}
