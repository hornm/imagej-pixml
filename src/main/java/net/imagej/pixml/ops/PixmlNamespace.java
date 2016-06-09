package net.imagej.pixml.ops;

import org.scijava.plugin.Plugin;

import net.imagej.ops.AbstractNamespace;
import net.imagej.ops.Namespace;
import net.imagej.ops.Op;
import net.imagej.ops.OpMethod;

@Plugin(type = Namespace.class)
public class PixmlNamespace extends AbstractNamespace {

	@Override
	public String getName() {
		return "pixml";
	}

	// -- PixML Namespace Op interfaces --

	public interface Train extends Op {
		String NAME = "pixml.train";
		String ALIASES = "pixml.learn";
	}
	
	public interface Predict extends Op {
		String NAME = "pixml.predict";
	}

	// -- PixML Namespace built-in methods --

	@OpMethod(op = net.imagej.pixml.ops.PixmlNamespace.Train.class)
	public Object train(final Object... args) {
		return ops().run(net.imagej.pixml.ops.PixmlNamespace.Train.class, args);
	}
	
	@OpMethod(op = net.imagej.pixml.ops.PixmlNamespace.Predict.class)
	public Object predict(final Object... args) {
		return ops().run(net.imagej.pixml.ops.PixmlNamespace.Predict.class, args);
	}
}
