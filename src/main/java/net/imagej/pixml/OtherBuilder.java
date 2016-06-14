package net.imagej.pixml;

import java.io.Serializable;

import org.scijava.plugin.Plugin;

import net.imagej.ops.special.function.BinaryFunctionOp;
import net.imagej.pixml.OtherBuilder.MyModel;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.composite.RealComposite;

@Plugin(type = Builder.class)
public class OtherBuilder implements Builder<MyModel> {

	public class MyModel implements Serializable {
		
	}
	
	@Override
	public Class<MyModel> getModelClass() {
		return MyModel.class;
	}

	@Override
	public <T extends RealType<T>, L> BinaryFunctionOp<RandomAccessibleInterval<RealComposite<T>>, RandomAccessibleInterval<LabelingType<L>>, MyModel> buildOp() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return "Other Builder";
	}

}
