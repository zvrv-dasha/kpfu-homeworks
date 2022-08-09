from imageai.Detection import ObjectDetection
import os

exec_path = os.getcwd()
print(exec_path)
detector = ObjectDetection()
detector.setModelTypeAsRetinaNet()
detector.setModelPath(os.path.join(
    exec_path, "resnet50_coco_best_v2.0.1.h5")
)
detector.loadModel()
list = detector.detectObjectsFromImage(
    input_image = os.path.join(exec_path, "people.jpg"),
    output_image_path = os.path.join(exec_path, "new_people.jpg"),
    minimum_percentage_probability = 30,
    display_percentage_probability = False,
    display_object_name = True
)