# imagej-pixml
Playground for Pixel-based Machine Learning for ImageJ and beyond


Blockers:
- overlay issue - overlays in BDV, overlays in ImageJ2, unification of overlays (ROIs), Overlays need labels (can be potentially turned into RandomAccressibleInterval<LabelingType<..>>), OverlayManager
- BDV as ImageJ2 DisplayPlugin (and related stuff), that supports the rendering of RandomAccessibleIntervals of different types (e.g. RealType, LabelingType etc. - ColorTable), Overlays, ...

TODOs:
- Cluster-interface
- Model-Wrapper
- Model/Classifier Display/Manager (to load/save/display models)


Commands:
- Classify & Predict
- Classify (returns a Model, e.g. to be saved somewhere)
- Predict (requires a Model)
- Cluster
- Model Manager
