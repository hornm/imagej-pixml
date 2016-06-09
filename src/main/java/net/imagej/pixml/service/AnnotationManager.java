package net.imagej.pixml.service;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.scijava.command.Command;
import org.scijava.display.Display;
import org.scijava.display.DisplayService;
import org.scijava.event.EventHandler;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import net.imagej.display.ImageDisplay;
import net.imagej.display.OverlayService;
import net.imagej.event.OverlayCreatedEvent;
import net.imagej.overlay.Overlay;

@Plugin(type = Command.class, label = "Annotation Manager")
public class AnnotationManager implements Command {

	@Parameter
	private OverlayService overlayService;

	@Parameter
	private DisplayService displayService;

	@Override
	public void run() {
		JFrame f = new JFrame("Annotation Manager");
		Display<?> display = displayService.getActiveDisplay();
		List<Overlay> overlays;
		if (display instanceof ImageDisplay) {
			overlays = overlayService.getOverlays((ImageDisplay) display);
		} else {
			overlays = Collections.emptyList();
		}
		f.setLayout(new GridLayout(overlays.size() + 1, 1));
		f.add(new JLabel("Overlay -> Class Label"));
		for (int i = 0; i < overlays.size(); i++) {
			JPanel p = new JPanel(new FlowLayout());
			p.add(new JLabel("overlay " + i));
			p.add(new JTextField("class1"));
			f.add(p);
		}
		f.pack();
		f.setVisible(true);
	}

	@EventHandler
	private void onEvent(OverlayCreatedEvent e) {
		//
	}

}
