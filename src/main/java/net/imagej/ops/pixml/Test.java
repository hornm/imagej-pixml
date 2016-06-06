package net.imagej.ops.pixml;

import java.io.IOException;

import bdv.util.Bdv;
import bdv.util.BdvFunctions;
import bdv.util.BdvOptions;
import net.imagej.ImageJ;
import net.imagej.ImgPlus;
import net.imagej.ops.Ops;
import net.imglib2.RandomAccessibleInterval;
import net.imglib2.algorithm.labeling.ConnectedComponents;
import net.imglib2.img.Img;
import net.imglib2.roi.labeling.ImgLabeling;
import net.imglib2.type.numeric.RealType;
import net.imglib2.view.Views;
import net.imglib2.view.composite.CompositeIntervalView;
import net.imglib2.view.composite.RealComposite;
import weka.classifiers.trees.J48;

public class Test {
	public static <T extends RealType<T>> void main(String[] args) throws IOException {
		ImageJ ij = new ImageJ();

		RandomAccessibleInterval<T> img = (ImgPlus<T>) ij.scifio().datasetIO().open("/home/hornm/tmp/train-volume.tif")
				.getImgPlus();
		CompositeIntervalView<T, RealComposite<T>> collapsed = Views.collapseReal(img);

		RandomAccessibleInterval labels = ij.scifio().datasetIO().open("/home/hornm/tmp/train-labels.tif").getImgPlus();
		labels = Views.hyperSlice(labels, labels.numDimensions() - 1, 0);

		ImgLabeling cca = (ImgLabeling) ij.op().labeling().cca(labels,
				ConnectedComponents.StructuringElement.EIGHT_CONNECTED);

		// TODO provide an own OpEnvironment??
		PixmlNamespace pixml = new PixmlNamespace();
		pixml.setEnvironment(ij.op());

		J48 j48 = new J48();
		pixml.train(collapsed, cca, j48, .01);

		Bdv bdv = BdvFunctions.show(img, "source");

		BdvFunctions.show(cca, "labels", BdvOptions.options().addTo(bdv));

	}

}
