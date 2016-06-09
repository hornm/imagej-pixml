package net.imagej.pixml.service;

import org.scijava.service.SciJavaService;
import org.scijava.tool.Tool;

import net.imagej.display.ImageDisplay;
import net.imagej.ui.swing.commands.OverlayManager;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.roi.labeling.LabelRegions;

/**
 * Possible entry point to get image annotations.
 * 
 * Thoughts:
 * - should give either {@link ImgLabeling} or {@link LabelRegions} (and some how access to their source images -> RichImg?) -> in that way a label basically associated with a pixel (or rather a pixel position)
 * - maybe should be able to retrieve the annotations from the {@link OverlayManager}? Question here: where come the labels/classes from?
 * - need of another source of annotations (overlays are not sufficient because of missing label information): kind of a annotation manager using {@link Tool}s to draw, add a label to them and generate, e.g., an {@link ImgLabeling} out of it
 * - drawing on a big data viewer display should be possible
 * - how can we make use of that stuff in KNIME? i.e. Image Annotator-node ...
 * - could maybe provide a method to retrieve an {@link ImgLabeling} from an {@link ImageDisplay}
 * 
 * @author Martin Horn
 *
 */
public interface AnnotationService extends SciJavaService {

	//pixel annotation service -> e.g. gives ImgLabelings or LabelRegions to represent image annotations
	
	
	
}
