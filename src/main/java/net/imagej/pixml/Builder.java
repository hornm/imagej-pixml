package net.imagej.pixml;

import java.io.Serializable;

import org.scijava.command.Command;

import net.imagej.ops.special.function.BinaryFunctionOp;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.roi.labeling.LabelingType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.composite.RealComposite;

public interface Builder<M extends Serializable> extends Command {
	
	Class<M> getModelClass();
	
	/**
	 * TODO
	 * 
	 * @return
	 */
	<T extends RealType<T>, L> BinaryFunctionOp<RandomAccessibleInterval<RealComposite<T>>, RandomAccessibleInterval<LabelingType<L>>, M> buildOp();
	
	@Override
	default void run() {
		// nothing to here, usually
		
	}
	
}
