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


Use Cases:

Feature Calculation + Build Model + (Optional) Predict
- input: image and labeling
- calculate features of the input image
- build model based on the given feature image and labels
- prediction of the same input images

Feature Calculation + Prediction
- input: (multiple) images + model (classifier, e.g. selected from a list of available models)
- calculate features
- prediction using the given model (needs to comply with the given features -> model must contain a specification of the features!!!)
- advantage: model can be reused each for batch-processing

Prediction only
- input: feature image + model
- prediction using the given model and features
