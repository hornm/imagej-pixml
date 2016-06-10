package net.imagej.pixml.command;

import javax.swing.JFrame;

import org.scijava.command.Command;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import bdv.util.Bdv;
import bdv.util.BdvFunctions;
import bdv.util.BdvHandlePanel;
import bdv.util.BdvOptions;
import net.imglib2.img.Img;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.type.numeric.IntegerType;

@Plugin(type = Command.class, menuPath = "Plugins > Img2Labeling")
public class Img2Labeling<L, T extends IntegerType<T>> implements Command {

	@Parameter
	private Img<T> img;

//	@Parameter(type = ItemIO.OUTPUT)
	private ImgLabeling<L, T> labeling;

	@Override
	public void run() {
		labeling = new ImgLabeling<>(img);
		JFrame f = new JFrame("Test");
		BdvOptions o = BdvOptions.options();
		BdvHandlePanel p = new BdvHandlePanel(f, o);
		o.addTo(p);
		f.add(p);
		Bdv show = BdvFunctions.show(img, "Test", o);
		f.pack();
		f.setVisible(true);
		
	}

}
