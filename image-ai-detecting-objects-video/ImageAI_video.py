from imageai.Detection import VideoObjectDetection
import os

exec_path = os.getcwd()
detector = VideoObjectDetection()
detector.setModelTypeAsYOLOv3()
detector.setModelPath(os.path.join(exec_path, "yolo.h5"))
detector.loadModel()

video_path = detector.detectObjectsFromVideo(
    input_file_path = os.path.join(exec_path, "traffic.mp4"),
    output_file_path = os.path.join(exec_path, "new_traffic.mp4"),
    frames_per_second = 20,
    log_progress = True
)

print(video_path)