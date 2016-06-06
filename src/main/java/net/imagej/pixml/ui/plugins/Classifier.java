package net.imagej.pixml.ui.plugins;

import org.scijava.plugin.SciJavaPlugin;

/**
 * 
 * TODO:
 * requirements: 
 * - JPanel for configuration
 * - serialization (e.g. of all parameters, i.e. model itself and beyond)
 * - model (e.g. Weka Classifier)
 * - additional model parameters that are not stored with the model itself
 * - make use of ops (e.g. train and predict), but somehow systematically such that it can be extended (e.g. always Img<RealComposite> and ImgLabeling)
 * 
 * @author Martin Horn
 */
public interface Classifier extends SciJavaPlugin{

	
	
}
