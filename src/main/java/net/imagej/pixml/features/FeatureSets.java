package net.imagej.pixml.features;

import java.util.List;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;

import net.imagej.pixml.ui.swing.SwingFeatureSetsWidget;

/**
 * Summarizes multiple {@link FeatureSet}'s to a list of feature sets. This can
 * then be, e.g., annotated as a {@link Parameter} within a {@link Command}
 * -plugin. It will be injected automatically (see
 * {@link SwingFeatureSetsWidget}.
 * 
 * @author Martin Hron
 *
 */
public interface FeatureSets extends List<FeatureSet> {

}
